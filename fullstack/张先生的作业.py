使用语言：python
源代码 ：

import string
import random

class Codec:
    def __init__(self):
        self.tiny = {}
        self.origin = {}
        self.k = 8
        self.prefix = 'http://xxx.com/xx/'
        self.ss = string.ascii_letters + string.digits

    def encode(self, longUrl):
        while longUrl not in self.origin.keys():
            cur = ''.join([self.ss[random.randint(0,len(self.ss) - 1)] for _ in range(self.k)])
            if cur in self.tiny.keys():
                continue
            self.tiny[cur] = longUrl
            self.origin[longUrl] = cur
        return self.origin[longUrl]
        
    def decode(self, shortUrl):
        return self.tiny[shortUrl]
        
测试用例：

import string
import random

class Codec:
    def __init__(self):
        self.tiny = {}
        self.origin = {}
        self.k = 8
        self.prefix = 'http://xxx.com/xx/'
        self.ss = string.ascii_letters + string.digits

    def encode(self, longUrl):
        while longUrl not in self.origin.keys():
            cur = ''.join([self.ss[random.randint(0,len(self.ss) - 1)] for _ in range(self.k)])
            if cur in self.tiny.keys():
                continue
            self.tiny[cur] = longUrl
            self.origin[longUrl] = cur
        return self.origin[longUrl]
        
    def decode(self, shortUrl):
        return self.tiny[shortUrl]


a = Codec()
while True:
    randomUrl = ''.join([random.choice(string.ascii_letters + string.digits) for i in range(8)])
    print(randomUrl)
    tmp = a.encode(randomUrl)
    print(tmp)
    res = a.decode(tmp)
    print(a.decode(tmp))
    if res != randomUrl:
        print('error')
        break

测试覆盖率 ：url里未包含特殊符号的原地址 ，而且一般url里也不含有数字（不过测试的时候还是加了string.digits增加覆盖率）


目前数据都放在内存里了，有易失性，如果有需要可以持久化到数据库。不过目前以简洁的方式呈现，无过度设计


