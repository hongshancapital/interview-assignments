"""
功能描述：拿到TestCase的断言结果， 写入到测试数据data.xls里面
        提供testCase模块写入excel的方法
编写人：AriaXie
编写日期：2021.7.13
实现逻辑：
1-导包
2-定义写入数据的类
    2-1-定义初始化方法
        2-1-1-准备写入的excel文件
        2-1-2-确定sheet页
    2-2-定义写入的数据的方法
        2-2-1-准备写入的测试结果
        2-2-2-准备写入的目标单元格的行和列
        2-2-3-开始写入
导包
打开excel
赋值当前excel
写入real, result
保存
"""
import os, xlrd
from xlutils.copy import copy


class WriteExcel():
    def __init__(self, method):
        self.method = method
        # 确定待写入excel位置
        if self.method == 'post':
            self.ex_dir = os.path.dirname(os.path.dirname(__file__)) + '/testData/data.xls'
        elif self.method == 'get':
            self.ex_dir = os.path.dirname(os.path.dirname(__file__)) + '/testData/data_get.xls'
        # self.ex_dir = os.path.dirname(os.path.dirname(__file__)) + "/testData/data.xls"
        # 打开excel
        self.wb = xlrd.open_workbook(self.ex_dir)
        # 复制打开的excel
        self.cp = copy(self.wb)
        # 确定sheet
        self.sh = self.cp.get_sheet(0)

    def writeData(self, id, real, result):
        """
        对外提供写入excel的方法
        :param id: 测试数据的行
        :param real: 接口测试的实际结果
        :param result：测试结果
        :return:
        """
        self.sh.write(id, 4, real)
        self.sh.write(id, 5, result)
        self.cp.save(self.ex_dir)


if __name__ == '__main__':
    #we = WriteExcel('get')
    we = WriteExcel('get')
    we.writeData(1, "X", "Y")
