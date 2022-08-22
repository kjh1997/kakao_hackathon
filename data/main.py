# -*- coding: UTF-8 -*-
'''
@Project ：kakao_hackathon 
@File ：main.py
@IDE  ：PyCharm
@Author ： Hwang
@Date ：2022-08-22 오후 12:09
'''
import json
import pymysql
from kafka import KafkaConsumer
from model import ReviewAnalysis

# kafka
site = 'etl'
bootstrap_servers = ['44.209.59.22']
consumer = KafkaConsumer(
            site,
            bootstrap_servers=bootstrap_servers,
            group_id='consumerGroupId',
            enable_auto_commit=False,
            auto_offset_reset='earliest',
            value_deserializer=lambda x: json.loads(x),
            max_poll_records=1,
            )

# mysql
db = pymysql.connect(
            host="database-1.czlecdolrcj0.us-east-1.rds.amazonaws.com",
            port=3306,
            user='root',
            password='12341234',
            db='kurly', charset='utf8', autocommit=True  # 실행결과확정
        )
cursor = db.cursor()
sql = """INSERT INTO review (`star`, `comment`, `date`, `department`,`feedback`, `score`, `mu_keyword`, `correct`) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"""


# analysis
print("!!!!!!!!!!!!!!load model!!!!!!!!!!!!!!")
m = ReviewAnalysis()
m.createTokenizer()

print("!!!!!!!!!!!!!!start consumer!!!!!!!!!!!!!!")

for msg in consumer:
    if "comment" in msg.value:
        review = json.loads(msg.value)
        result = m.sentiment_predict1(review['comment'])
        print("star = %s, comment = %s, date = %s, department = %s, feedback = %s, score = %s, review_word = %s, correct = %s," % (review['star'], review['comment'], review['date'], result['department'], int(result['feedback']), int(result['score']), result['review_word'], result['correct']))

        print()



