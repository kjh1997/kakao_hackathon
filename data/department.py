from collections import defaultdict
import re

stopwords = ['도', '는', '다', '의', '가', '이', '은', '한', '에', '하', '고', '을', '를', '인', '듯', '과', '와', '네', '들', '듯', '지',
             '임', '게']


def setDepartment(word_list):
    priority = {"customerService", "delivery", "quality"}
    department = {
        "customerService": ['전화', '주문', '교환', '고객', '품절', '사람', '리뷰', '연락', '친절', ' 서비스', '판매자'],
        "delivery": ['배송', '포장', '택배', '발송', '도착', '배달', '주일', '평일', '주말'],
        "quality": ['사용', '제품', '가격', '저렴', '깔끔', '디자인', '상태', '추천', '색상', '재질', '고급', '퀄리티', '성능']
    }

    # count
    # 0 : customerService
    # 1 : delivery
    # 2 : quality
    cnt = {"customerService": 0,
           "delivery": 0,
           "quality": 0}
    best_word = defaultdict(int)

    for word in word_list:
        if word in department['customerService']:
            best_word[word] += 1
            cnt["customerService"] = 1
        if word in department['delivery']:
            best_word[word] += 1
            cnt["delivery"] += 1
        if word in department['quality']:
            best_word[word] += 1
            cnt["quality"] += 1

    review_department = "UCF"
    review_word = "UCF"

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