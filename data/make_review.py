# -*- coding: UTF-8 -*-
'''
@Project ：kakao_hackathon 
@File ：make_review.py
@IDE  ：PyCharm 
@Author ： Hwang
@Date ：2022-08-03 오후 12:56 
'''
import json
import random
import re
import time

test_code = ""

def str_time_prop(start, end, format, prop):
    stime = time.mktime(time.strptime(start, format))
    etime = time.mktime(time.strptime(end, format))
    ptime = stime + prop * (etime - stime)
    return time.strftime(format, time.localtime(ptime))

def random_date(start, end, prop):
    return str_time_prop(start, end, '%Y-%m-%d %H:%M:%S', prop)

def romoveEmoji(string):
    only_BMP_pattern = re.compile("["
                                  u"\U00010000-\U0010FFFF"  # BMP characters 이외
                                  "]+", flags=re.UNICODE)
    return (only_BMP_pattern.sub(r'', string))  # BMP characters만

f = open("ratings_total.txt",'r', encoding="UTF-8")
review = []

lines = f.readlines()
for l in lines:
    l = l.strip()
    l = l.split("\t")

    review.append(
        {"star" : l[0],
         "comment" : romoveEmoji(l[1]),
         "date" : random_date("2021-1-1 23:59:59", "2022-1-1 23:59:59", random.random())})

with open('review.json','w',encoding='utf-8') as f:
    json.dump(review, f, indent=4, ensure_ascii=False)
