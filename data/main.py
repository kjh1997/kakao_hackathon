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
from kafka import KafkaProducer
from json import dumps
from model import ReviewAnalysis
import time

# kafka
## consumer
site = 'etl'
bootstrap_servers = ['52.44.127.67']
consumer = KafkaConsumer(
            site,
            bootstrap_servers=bootstrap_servers,
            group_id='consumerGroupId',
            enable_auto_commit=False,
            auto_offset_reset='latest',
            value_deserializer=lambda x: json.loads(x),
            max_poll_records=1,
            )
## producer
producer = KafkaProducer(
    bootstrap_servers= bootstrap_servers,
    value_serializer=lambda x: dumps(x).encode('utf-8')
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
        current_date = str(time.strftime('%Y-%m-%d %H:%M:%S', time.localtime()))

        # predict
        result = m.sentiment_predict1(review['comment'])
        data = (review['star'], review['comment'], current_date, result['department'], int(result['feedback']), int(result['score']), result['review_word'], result['correct'])
        data_json ={
            "star" : review['star'],
            "comment": review['comment'],
            "date": current_date,
            "department": result['department'],
            "feedback": int(result['feedback']),
            "score": int(result['score']),
            "review_word": result['review_word'],
            "correct": result['correct']
        }

        # send msg
        producer.send('complete', value=data_json)
        producer.flush()

        # insert into db
        cursor.execute(sql, data)

        print("star = %s, comment = %s, date = %s, department = %s, feedback = %s, score = %s, review_word = %s, correct = %s," % data)
        print()



