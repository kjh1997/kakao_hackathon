# -*- coding: UTF-8 -*-
'''
@Project ：kakao_hackathon 
@File ：language.py
@IDE  ：PyCharm 
@Author ： Hwang
@Date ：2022-08-19 오전 9:18 
'''
import time

from pykospacing import Spacing
from hanspell import spell_checker

sentence = "맞춤법 틀리면 외 않되? 쓰고싶은대로쓰면돼지"

start  = time.time()

spelled = spell_checker.check(sentence)
spell_result = spelled.checked
print(spell_result)


end = time.time()
print(end - start)
start  = time.time()

spacing = Spacing()
spacing_result = spacing(spell_result)
print(spacing_result)


end = time.time()
print(end - start)