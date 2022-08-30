# -*- coding: UTF-8 -*-
'''
@Project ：kakao_hackathon 
@File ：review_date_update.py
@IDE  ：PyCharm 
@Author ： Hwang
@Date ：2022-08-23 오후 8:52 
'''

import pymysql
import random
import time

def str_time_prop(start, end, format, prop):
    stime = time.mktime(time.strptime(start, format))
    etime = time.mktime(time.strptime(end, format))
    ptime = stime + prop * (etime - stime)
    return time.strftime(format, time.localtime(ptime))

def random_date(start, end, prop):
    return str_time_prop(start, end, '%Y-%m-%d %H:%M:%S', prop)

db = pymysql.connect(
            host="database-1.czlecdolrcj0.us-east-1.rds.amazonaws.com",
            port=3306,
            user='root',
            password='12341234',
            db='kurly', charset='utf8', autocommit=True  # 실행결과확정
        )

cursor = db.cursor()
sql = """UPDATE review SET `date` = %s WHERE (`id` = %s)"""
update_query = []

print("query start!!")
for i in range(200000):
    if i % 100 == 0:
        cursor.executemany(sql, update_query)
        print("query done : ", i)
        update_query = []

    date = random_date("2022-1-1 23:59:59", "2022-8-22 23:59:59", random.random())
    update_query.append([date, i+214031])

if update_query:
    cursor.executemany(sql, update_query)
print("done")