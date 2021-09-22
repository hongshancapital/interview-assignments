"""
功能描述：拿到测试数据，根据接口的请求方式判断,然后进行请求，断言请求的结果
编写人：AriaXie
编写日期：2021.9.18
实现逻辑：
1-导包 unittest, requests, os
2-调用readExcel模块中的read方法， 获取测试数据
3-遍历测试数据，逐一取出，请求：
    3-1-准备接口请求的参数
        header, cookie, parameter
    3-2-根据接口的请求方式进行判断
        if get: requests.get
        if post: requests.post
    3-3-断言接口的返回结果：
        实际结果 real：从接口的响应数据中，提取
        断言
4-回写：
    将实际结果real,写入data
    将预期结果与实际结果，比对的结果result，写入data
    形成完成的测试报告
"""
from common.logs import logger
from common.readExcel import ReadExcel
import unittest, requests
from ddt import ddt, data, unpack
from common.configHttp import ConfigHttp
from common.writeExcel import WriteExcel

re = ReadExcel('post')
test_data = re.readExcel()
#a, b = test_data
# print(f"---------a是：{a}")
# print(f"---------b是：{b}")
logger.debug(f"获取的数据为：{test_data}")

@ddt
class TestCase(unittest.TestCase):
    @data(*test_data)
    @unpack
    def test_run(self, id, value, method, url, expect, real, result):
        self.value = value
        self.method = method
        self.url = url
        self.expect = expect
        # print(f"-----------------self.value是：{self.value}")
        # print(f"-----------------self.method是：{self.method}")
        # print(f"-----------------self.url是：{self.url}")
        #ch = ConfigHttp(id, value, method, url, expect, real, status)
        ch = ConfigHttp(value, method, url)
        # print(f"-----------------ch是:{ch}")
        statusCode, msg = ch.run()
        self.real = msg
        if self.expect == self.real and statusCode == 200:
            result = "pass"
            # print(f"--------if:result是：{self.real}")
        elif self.expect == self.real and statusCode == 400:
            result = "pass"
            # print(f"--------elif:result是：{self.real}")
        else:
            result = 'fail'
        # we = WriteExcel()
        # we.writeData(int(id), self.real, result)

        try:
            #self.assertEqual(int(statusCode), 200)
            self.assertEqual(self.expect, self.real)
        except AssertionError as msg:
            logger.error(f"系统提示:{msg}")
            result = 'error,请排查问题'
            raise
        finally:
            # 将接口断言结果，写入excel
            we = WriteExcel('post')
            we.writeData(int(id), self.real, result)
if __name__ == '__main__':
    unittest.main()
    # re = ReadExcel()
    # test_data = re.readExcel()
    # logger.debug(f"获取的数据为：{test_data}")
