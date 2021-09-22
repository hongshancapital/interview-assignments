"""
功能描述：读取excel数据，对外提供读取数据的方法
编写人：AriaXie
编写日期：2021.9.18
实现逻辑：
1-导包 xlrd
2-确定excel数据位置
3-确定sheet页
4-逐行遍历， 重新组装数据[{},{},{}]
    4-1-第一行为Key
    4-2-第二行为Value
5-return data
"""
from common.logs import logger
import xlrd, os
import requests
class ReadExcel():
    def __init__(self, method):
        logger.debug("正在初始化数据")
        self.method = method
        # 定位excel数据
        if self.method == 'post':
            self.ex = os.path.dirname(os.path.dirname(__file__)) + '/testData/data.xls'
        elif self.method == 'get':
            self.ex = os.path.dirname(os.path.dirname(__file__)) + '/testData/data_get.xls'
        #print(self.ex)
        # 打开excel
        self.op = xlrd.open_workbook(self.ex)
        #print(self.op)
        # 定位sheet
        self.sh = self.op.sheet_by_index(0)
        print(self.sh)
        # 确定行数，即循环遍历次数
        self.rownum = self.sh.nrows
        print(self.rownum)
    def readExcel(self):
        data = []
        #获取第一行的值，作为key
        key = self.sh.row_values(0)
        #循环遍历第二行开始的数据，作为Value
        for i in range(1, self.rownum):
            value = self.sh.row_values(i)
            dict_kv = {key[j]:value[j] for j in range(len(key))}
            data.append(dict_kv)
        return data


if __name__ == '__main__':
    data = ReadExcel('post')
    r = data.readExcel()
    print(r)




