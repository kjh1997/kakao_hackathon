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

def check_spell_n_space(sentence):
    spacing = Spacing()
    spacing_result = spacing(sentence)

    spelled = spell_checker.check(spacing_result)
    spell_result = spelled.checked

    return spell_result
