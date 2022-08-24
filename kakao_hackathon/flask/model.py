from tensorflow.keras.layers import Embedding, Dense, GRU
from tensorflow.keras.models import Sequential
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint
import re
import re
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import urllib.request
from collections import Counter
from konlpy.tag import Mecab
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
import os
from tensorflow.keras.models import model_from_json 
from keras.models import load_model
from konlpy.tag import Mecab
from sklearn.model_selection import train_test_split

class ReviewAnalysis:
  def __init__(self):
    self.max_len = 80
    self.mecab = Mecab()
    self.total_data = pd.read_table('ratings_total.txt', names=['ratings', 'reviews'])
    self.loaded_model = load_model('best_model.h5')
    self.tokenizer = Tokenizer()
    self.stopwords = ['도', '는', '다', '의', '가', '이', '은', '한', '에', '하', '고', '을', '를', '인', '듯', '과', '와', '네', '들', '듯', '지', '임', '게']

  def createTokenizer(self):
    self.total_data['label'] = np.select([self.total_data.ratings > 3], [1], default=0)
    self.total_data['reviews'].nunique()
    self.total_data.drop_duplicates(subset=['reviews'], inplace=True)
    self.total_data['tokenized'] = self.total_data['reviews'].apply(self.mecab.morphs)
    self.total_data['tokenized'] = self.total_data['tokenized'].apply(lambda x: [item for item in x if item not in self.stopwords])
    X_train = self.total_data['tokenized'].values
    self.tokenizer = Tokenizer()
    self.tokenizer.fit_on_texts(X_train)

  def sentiment_predict1(self, new_sentence):
    new_sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣 ]','', new_sentence)
    new_sentence = self.mecab.morphs(new_sentence)
    new_sentence = [word for word in new_sentence if not word in self.stopwords]
    encoded = self.tokenizer.texts_to_sequences([new_sentence])
    pad_new = pad_sequences(encoded, maxlen = self.max_len)
    score = float(self.loaded_model.predict(pad_new))
    if(score > 0.5):
      print("{:.2f}% 확률로 긍정 리뷰입니다.".format(score * 100))
      return f"긍정 : {score * 100}"
    else:
      print("{:.2f}% 확률로 부정 리뷰입니다.".format((1 - score) * 100))
      return f"부정 : {(1 - score) * 100}" 








