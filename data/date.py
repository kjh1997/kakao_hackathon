# -*- coding: UTF-8 -*-
'''
@Project ：kakao_hackathon 
@File ：date.py
@IDE  ：PyCharm 
@Author ： Hwang
@Date ：2022-08-24 오전 10:18 
'''

import time
current_date = str(time.strftime('%Y-%m-%d %H:%M:%S', time.localtime()))
print(current_date)