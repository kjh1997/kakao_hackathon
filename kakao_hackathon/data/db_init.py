# -*- coding: UTF-8 -*-
'''
@Project ：kakao_hackathon 
@File ：db_init.py
@IDE  ：PyCharm 
@Author ： Hwang
@Date ：2022-08-03 오전 10:52 
'''
import json

import pymysql

pw = "whdgns1002@"
db = pymysql.connect(
            host="localhost",
            port=3306,
            member='root',
            password=pw,
            db='kurly', charset='utf8', autocommit=True  # 실행결과확정
        )

with open("review.json",'r',encoding="UTF-8") as file:
    reviews = json.load(file)




cursor = db.cursor()
sql = """INSERT INTO review (`star`, `comment`, `date`) VALUES (%s, %s, %s)"""

for review in reviews:
    star = review['star']
    comment = review['comment'].replace("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]","")
    date = review['date']

    # kor lang pre-process



    data_tuple = (star, comment, date)

    print(data_tuple)
    cursor.execute(sql, data_tuple)

