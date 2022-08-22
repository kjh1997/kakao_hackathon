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

sentence = "맞춤법 틀리면 왜 안 돼? 쓰고 싶은 대로 쓰면 되지"

def check_spell_n_space(sentence):
    spelled = spell_checker.check(sentence)
    spell_result = spelled.checked

    spacing = Spacing()
    spacing_result = spacing(spell_result)
    return spacing_result

print(check_spell_n_space(sentence))