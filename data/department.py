from collections import defaultdict
from konlpy.tag import Mecab
import re

mecab =  Mecab()
stopwords = ['도', '는', '다', '의', '가', '이', '은', '한', '에', '하', '고', '을', '를', '인', '듯', '과', '와', '네', '들', '듯', '지', '임', '게']

def setDepartment(word_list):

    new_sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣 ]','', word_list)
    new_sentence = mecab.morphs(new_sentence)
    new_sentence = [word for word in new_sentence if not word in stopwords]

    priority={"customerService","delivery","quality"}
    department={
        "customerService" : ['전화', '주문', '교환', '고객', '품절', '사람', '리뷰','연락', '친절',' 서비스', '판매자'],
        "delivery" : ['배송', '포장', '택배', '발송', '도착', '배달', '주일', '평일', '주말'],
        "quality" : ['사용', '제품', '가격', '저렴', '깔끔', '디자인', '상태', '추천', '색상', '재질','고급','퀄리티', '성능']
        }
        
    # count
    # 0 : customerService
    # 1 : delivery
    # 2 : quality
    cnt = {"customerService" :0,
          "delivery":0,
          "quality":0}
    best_word = defaultdict(int)

    for word in new_sentence:
      if word in department['customerService']:
        best_word[word]+=1
        cnt["customerService"]=1
      if word in department['delivery']:
        best_word[word]+=1
        cnt["delivery"]+=1
      if word in department['quality']:
        best_word[word]+=1
        cnt["quality"]+=1
    
    review_department = "UCF"
    review_word = None

    if sum(cnt.values()) != 0:
        for dep in department:
            if cnt[dep] == max(cnt.values()):
                review_department = dep
                break
        
        for word in department[review_department]:
            if word in best_word.keys():
                if best_word[word] == max(best_word.values()):
                    review_word = word
                    break

    return [review_department, review_word]

    

import pymysql

db = pymysql.connect(
            host="database-1.czlecdolrcj0.us-east-1.rds.amazonaws.com",
            port=3306,
            user='root',
            password='12341234',
            db='kurly', charset='utf8', autocommit=True  # 실행결과확정
        )

cursor = db.cursor()
sql = """SELECT * FROM kurly.review"""
cursor.execute(sql)

result = [list(i) for i in cursor.fetchall()]
update_query = []
for i in result:
    review = str(i[1])
    update_query.append(setDepartment(review)+[i[0]])

sql = """UPDATE review SET `department` = %s, `mu_keyword` = %s WHERE (`id` = %s)"""

current_cnt=0
tem_query = []
for q in update_query:
    if current_cnt % 100 == 0:
        cursor.executemany(sql, tem_query)
        print("db update " + str(current_cnt) + " completed")
        tem_query = []
    tem_query.append(q)
    current_cnt+=1

if tem_query:
    cursor.executemany(sql, tem_query)
    print("db update " + str(current_cnt) + " completed")

print("done!")

db.close()