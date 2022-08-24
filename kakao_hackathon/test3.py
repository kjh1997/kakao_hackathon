
a = "👍"
def rmEmoji_ascii(inputString):
    return inputString.encode('ascii', 'ignore').decode('ascii')
print(rmEmoji_ascii(a))