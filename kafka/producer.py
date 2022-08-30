# -*- coding: UTF-8 -*-
'''
@Project ：kakao_hackathon 
@File ：producer.py
@IDE  ：PyCharm 
@Author ： Hwang
@Date ：2022-08-19 오후 1:35 
'''


from kafka import KafkaProducer
from json import dumps
import time


# producer = KafkaProducer(
#     acks=0,
#     compression_type='gzip',
#     bootstrap_servers=['44.209.59.22:9092'],
#     value_serializer=lambda x: dumps(x).encode('utf-8')
# )
#
# start = time.time()
# for i in range(10):
#     data = {'str' : 'result'+str(i),
#             "한글" : "테스트"}
#     producer.send('elt', value=data)
#     producer.flush()
# print("elapsed :", time.time() - start)


producer = KafkaProducer(
    bootstrap_servers= '52.44.127.67',
    value_serializer=lambda x: dumps(x).encode('utf-8')
)
start = time.time()
for i in range(3):
    data = {
        'str' : 'result'+str(i),
        "한글" : "테스트"
        }
    producer.send('etl', value=data)
    producer.flush()
print("elapsed :", time.time() - start)