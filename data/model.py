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

  def setDepartment(self, word_list):

    department=["customerService","delivery","quality"]

    customerService = ['응대', '상담', '전화', '친절', '목소리']
    delivery = ['배송', '배달', '택배', '포장']
    quality = ['품질', '상품']

    # count
    # 0 : customerService
    # 1 : delivery
    # 2 : quality
    cnt = {"customerService" :0,
          "delivery":0,
          "quality":0}
    for word in word_list:
      if word in customerService:
        cnt["customerService"]=1
      if word in delivery:
        cnt["delivery"]+=1
      if word in quality:
        cnt["quality"]+=1
    
    if sum(cnt.values())== 0:
      return "UCF"
    else:
      for dep in department:
        if cnt[dep] == max(cnt.values()):
          return dep

  def sentiment_predict1(self, new_sentence):

    new_sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣 ]','', new_sentence)
    new_sentence = self.mecab.morphs(new_sentence)
    new_sentence = [word for word in new_sentence if not word in self.stopwords]
    # department
    department = self.setDepartment(new_sentence)
    encoded = self.tokenizer.texts_to_sequences([new_sentence])
    pad_new = pad_sequences(encoded, maxlen = self.max_len)
    try :
        score = float(self.loaded_model.predict(pad_new))
    except:
        return{
        "department":"",
        "feedback":-1,
        "score":-1
                }
    if(score > 0.5):
      #print("{:.2f}% 확률로 긍정 리뷰입니다.".format(score * 100))
      return {
        "department" : department,
        "feedback" : 1,
        "score" : score * 100
        }
    else:
      #print("{:.2f}% 확률로 부정 리뷰입니다.".format((1 - score) * 100))
      return {
        "department" : department,
        "feedback" : 0,
        "score" : (1 - score) * 100
        }
