# -*- coding: UTF-8 -*-
'''
@Project ：kakao_hackathon
@File ：consumer.py
@IDE  ：PyCharm
@Author ： Hwang
@Date ：2022-08-20 오후 5:00
'''

from kafka import KafkaConsumer
import json

# consumer = KafkaConsumer(
#     'elt',
#     group_id='my-group',
#     bootstrap_servers=['44.209.59.22:9092'],
#     enable_auto_commit=True,
#     auto_offset_reset='earliest'
# )
#
# for message in consumer:
#     data = json.loads(message.value.decode('utf-8'))
#     print(data)

site = 'etl'
bootstrap_servers = ['52.44.127.67']
consumer = KafkaConsumer(
            site,
            bootstrap_servers=bootstrap_servers,
            group_id='consumerGroupId',
            enable_auto_commit=False,
            auto_offset_reset='earliest',
            value_deserializer=lambda x: json.loads(x),
            max_poll_records=1,
            )
""" #3. msg 처리기 """
for msg in consumer:
    data = msg.value
    key = ["id", "star", "date", "department", "feedback", "score", "comment", "mu_keyword"]
    print(data[key[0]])

