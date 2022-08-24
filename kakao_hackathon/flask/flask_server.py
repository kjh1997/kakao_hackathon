from flask import Flask, request 
from model import ReviewAnalysis
app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello World!'
@app.route('/api/reviewanalysis')
def reviewAnalysis():
    try:
        params = request.get_json()
        print(params)
    except:
        params=0
        pass
    if len(params) == 0:
        return 'No parameter'
    print("fdsafasd")
    string = params['context']
    result = reviewAnalysis.sentiment_predict1(string)
    print(result)
    return result
    

if __name__ == '__main__':
    reviewAnalysis = ReviewAnalysis()
    reviewAnalysis.createTokenizer() 
    app.run()