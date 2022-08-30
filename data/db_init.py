# -*- coding: UTF-8 -*-
'''
@Project ：kakao_hackathon 
@File ：db_init.py
@IDE  ：PyCharm 
@Author ： Hwang
@Date ：2022-08-03 오전 10:52 
'''
import json
import time

import pymysql
from model import ReviewAnalysis

m = ReviewAnalysis()
m.createTokenizer()

with open("review.json",'r',encoding="UTF-8") as file:
    reviews = json.load(file)


db = pymysql.connect(
            host="database-1.czlecdolrcj0.us-east-1.rds.amazonaws.com",
            port=3306,
            user='root',
            password='12341234',
            db='kurly', charset='utf8', autocommit=True  # 실행결과확정
        )

cursor = db.cursor()
sql = """INSERT INTO review (`star`, `comment`, `date`, `department`,`feedback`, `score`, `mu_keyword`, `correct`) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"""

start = time.time()
print("start : ", start)

reviews_query = []
for i in range(len(reviews)):
    if i % 100 == 0:
        print("done : " , i)
    if i % 10000 == 0:
        cursor.executemany(sql, reviews_query)
        print("query done : ", i)
        reviews_query= []
    analysis = m.sentiment_predict1(reviews[i]['comment'])
    reviews_query.append( (reviews[i]['star'], reviews[i]['comment'], reviews[i]['date'],analysis['department'], int(analysis['feedback']), int(analysis['score']), analysis['review_word'], analysis['correct']) )

if reviews_query:
    cursor.executemany(sql, reviews_query)
    print("query done")


print("time : ", time.time() -start)

db.close()