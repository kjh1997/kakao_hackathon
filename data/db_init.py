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

f=open("db")
pw = f.readline()
f.close()

with open("review.json",'r',encoding="UTF-8") as file:
    reviews = json.load(file)


db = pymysql.connect(
            host="localhost",
            port=3306,
            user='root',
            password=pw,
            db='kurly', charset='utf8', autocommit=True  # 실행결과확정
        )

cursor = db.cursor()
sql = """INSERT INTO review (`star`, `comment`, `date`) VALUES (%s, %s, %s)"""

def romoveEmoji_ascii(string):
    only_BMP_pattern = re.compile("["
                                  u"\U00010000-\U0010FFFF"  # BMP characters 이외
                                  "]+", flags=re.UNICODE)
    return (only_BMP_pattern.sub(r'', string))  # BMP characters만



reviews_query = [ (reviews[i]['star'], reviews[i]['comment'], reviews[i]['date']) for i in range(len(reviews))]

cursor.executemany(sql, reviews_query)