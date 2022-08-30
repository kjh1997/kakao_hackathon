import re
import pandas as pd
import numpy as np
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from keras.models import load_model
from konlpy.tag import Mecab
import copy

from department import setDepartment
from language import check_spell_n_space


class ReviewAnalysis:
    def __init__(self):
        self.max_len = 80
        self.mecab = Mecab()
        self.total_data = pd.read_table('ratings_total.txt', names=['ratings', 'reviews'])
        self.loaded_model = load_model('best_model.h5')
        self.tokenizer = Tokenizer()
        self.stopwords = ['도', '는', '다', '의', '가', '이', '은', '한', '에', '하', '고', '을', '를', '인', '듯', '과', '와', '네', '들',
                          '듯', '지', '임', '게']

    def createTokenizer(self):
        self.total_data['label'] = np.select([self.total_data.ratings > 3], [1], default=0)
        self.total_data['reviews'].nunique()
        self.total_data.drop_duplicates(subset=['reviews'], inplace=True)
        self.total_data['tokenized'] = self.total_data['reviews'].apply(self.mecab.morphs)
        self.total_data['tokenized'] = self.total_data['tokenized'].apply(
            lambda x: [item for item in x if item not in self.stopwords])
        X_train = self.total_data['tokenized'].values
        self.tokenizer = Tokenizer()
        self.tokenizer.fit_on_texts(X_train)

    def sentiment_predict1(self, new_sentence):

        # 정규화
        new_sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣0-9]', '', new_sentence)

        # 맞춤법, 띄어쓰기
        new_sentence = check_spell_n_space(new_sentence)
        correct_sentence = copy.deepcopy(new_sentence)

        # 토큰화
        new_sentence = self.mecab.morphs(new_sentence)
        new_sentence = [word for word in new_sentence if not word in self.stopwords]

        # 부서
        department = setDepartment(new_sentence)

        # 감성 분류
        encoded = self.tokenizer.texts_to_sequences([new_sentence])
        pad_new = pad_sequences(encoded, maxlen=self.max_len)
        try:
            score = float(self.loaded_model.predict(pad_new))
        except:
            return {
                "department": "UPD",
                "feedback": -1,
                "score": -1,
                "review_word": "UPD",
                "correct": correct_sentence
            }

        if (score > 0.5):
            # print("{:.2f}% 확률로 긍정 리뷰입니다.".format(score * 100))
            return {
                "department": department[0],
                "feedback": 1,
                "score": score * 100,
                "review_word": department[1],
                "correct": correct_sentence
            }
        else:
            # print("{:.2f}% 확률로 부정 리뷰입니다.".format((1 - score) * 100))
            return {
                "department": department[0],
                "feedback": 0,
                "score": (1 - score) * 100,
                "review_word": department[1],
                "correct": correct_sentence
            }
