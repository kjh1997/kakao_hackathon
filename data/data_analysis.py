import pymysql

db = pymysql.connect(
            host="database-1.czlecdolrcj0.us-east-1.rds.amazonaws.com",
            port=3306,
            user='root',
            password='12341234',
            db='kurly', charset='utf8', autocommit=True  # 실행결과확정
        )

cursor = db.cursor()
sql = """SELECT * FROM kurly.review WHERE feedback IS NULL"""
cursor.execute(sql)

result = [list(i) for i in cursor.fetchall()]

from model import ReviewAnalysis
m = ReviewAnalysis()
m.createTokenizer()

sql = """UPDATE review SET `department` = %s, `feedback` = %s, `score` = %s WHERE (`id` = %s)"""
update_query = []
current_cnt=0
for i in result:
    if len(update_query) == 100:
        cursor.executemany(sql, update_query)
        print("db update " + str(current_cnt) + " completed")
        update_query = []
    string = str(i[1])
    analysis = m.sentiment_predict1(string)
    update_query.append([analysis['department'], int(analysis['feedback']), int(analysis['score']), int(i[0])])
    current_cnt+=1

if update_query:
    cursor.executemany(sql, update_query)
    print("db update " + str(current_cnt) + " completed")

print("done!")

db.close()    
