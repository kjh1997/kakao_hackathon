# -*- coding: UTF-8 -*-
'''
@Project ：kakao_hackathon 
@File ：db_init.py
@IDE  ：PyCharm 
@Author ： Hwang
@Date ：2022-08-03 오전 10:52 
'''
import json
import re
import pymysql


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
sql = """INSERT INTO review (`star`, `comment`, `date`) VALUES (%s, %s, %s)"""

reviews_query = [ (reviews[i]['star'], reviews[i]['comment'], reviews[i]['date']) for i in range(len(reviews))]

cursor.executemany(sql, reviews_query)

db.close()