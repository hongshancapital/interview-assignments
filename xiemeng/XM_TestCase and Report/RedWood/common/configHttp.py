"""
功能描述：
接收testCase传入的接口请求测试数据，根据具体的请求逻辑，完成接口测试，
将接口返回的关键结果，返回给testCase
编写人：AriaXie
编写日期：2021.9.18
实现逻辑：
1-接收testCase传入的测试数据
2-提取测试数据中的请求方式：method
3-根据method进行判断：
    3-1-if get, request.get
    3-2-if post, request.post
4-从接口请求结果中，提取需要断言的字段：expect VS real
5-将预期结果与实际结果，进行比对:
    5-1-相同即通过，pass
    5-2-不同即失败，fail
"""
from common.logs import logger
import requests


class ConfigHttp():
    # def __init__(self, id, value, method, url, expect, real, result):
    def __init__(self, value, method, url):
        #self.id = id
        self.value = value
        self.method = method
        self.url = url
        # self.expect = expect
        # self.real = real
        # self.result = result
        logger.debug("ConfigHttp模块的初始化方法被调用了：%s" % self.method)
        logger.debug(f"value:{self.value}")
        logger.debug(f"url:{self.url}")

    def run(self):
        # logger.debug(f"传入的请求方式为：{self.method}")
        # if self.method.lower() == 'get':
        #     return self.__get()
        # elif self.method.lower() == 'post':
        #     return self.__post()
        try:
            """
            根据method进行判断，将结果返回给testCase
            """
            logger.debug(f"传入的请求方式为：{self.method}")
            if self.method.lower() == 'get':
                return self.__get()
            elif self.method.lower() == 'post':
                return self.__post()
        except Exception as msg:
            logger.error(f'-------系统错误：{msg}')

    def __get(self):
        logger.debug(f"__get方法的请求参数为：url={self.url}, parmas={self.value}, method={self.method}")
        result = requests.get(url=self.url, params=self.value)
        if self.url == 'http://127.0.0.1:5000/toolbox/options':
            get_result = result.json()
            statusCode = result.status_code
            logger.info(f"返回的status_code:{statusCode}, message:{get_result}")
            return statusCode, get_result
        elif self.url == 'http://127.0.0.1:5000/toolbox':
            get_result = result.json()['message']
            statusCode = result.status_code
            logger.info(f"返回的status_code:{statusCode}, message:{get_result}")
            return statusCode, get_result


    def __post(self):
        """
        错误提示语：
        value为空："No name specified,No tools specified"
        缺少name: "No name specified"
        缺少tools: "No tools specified"
        """
        #先判断value是否为空
        if self.value != '':
            logger.debug(f"__post方法的请求参数为：url={self.url}, data={self.value}, method={self.method}")
            result = requests.post(url=self.url, data=eval(self.value))
            statusCode = result.status_code
            if statusCode == 200:
                msg = result.json()['message']
                logger.info(f"返回的status_code:{statusCode}, message:{msg}")
                return statusCode, msg
            elif statusCode == 400:
                msg = result.json()['error']
                logger.info(f"返回的status_code:{statusCode}, message:{msg}")
                return statusCode, msg
        else:
            logger.debug(f"__post方法的请求参数为：url={self.url}, data={self.value}, method={self.method}")
            result = requests.post(url=self.url, data=self.value)
            statusCode = result.status_code
            msg = result.json()['error']
            if msg == 'No name specified':
                logger.info(f"返回的status_code:{statusCode}, message:{msg}")
                return statusCode, msg
            elif msg == 'No tools specified':
                logger.info(f"返回的status_code:{statusCode}, message:{msg}")
                return statusCode, msg
            elif msg == 'No name specified,No tools specified':
                logger.info(f"返回的status_code:{statusCode}, message:{msg}")
                return statusCode, msg
            elif msg == 'SQLITE_CONSTRAINT: UNIQUE constraint failed: toolbox_prefs.name':
                logger.info(f"返回的status_code:{statusCode}, message:{msg}")
                return statusCode, msg

if __name__ == '__main__':
    #url = 'http://127.0.0.1:5000/toolbox/options'
    url = 'http://127.0.0.1:5000/toolbox'
    value = None
    method = 'get'
    ch = ConfigHttp(value, method, url)
    #print(type(ch.run()))
    print(ch.run())
    print(f"ch是：{ch}")
    # dict1 = {"id":100,
    #          'value': '{"name": "test4", "tools": "test2"}',
    #          'method': 'post',
    #          'url': 'http://127.0.0.1:5000/toolbox/create',
    #          "expect":"success",
    #          "real":"F",
    #          "status":"success"}
    # dict1 = {'value': '{"name": "a1", "tools": "t1"}',
    #          'method': 'post',
    #          'url': 'http://127.0.0.1:5000/toolbox/create'}
    # dict1 = {'value': '{"tools":"ttt1"}',
    #          'method': 'post',
    #          'url': 'http://127.0.0.1:5000/toolbox/create'}
    # dict1 = {'value': '',
    #          'method': 'post',
    #          'url': 'http://127.0.0.1:5000/toolbox/create'}
    # dict1 = {'value': '{"name":"ttt1"}',
    #          'method': 'post',
    #          'url': 'http://127.0.0.1:5000/toolbox/create'}
    # url = dict1['url']
    # print(f"url是：{url}")
    # method = dict1['method']
    # print(f"method是：{method}")
    # value = dict1['value']
    # print(f"value是：{value}")
    # # id = dict1["id"]
    # # print(f"id是：{id}")
    # # expect = dict1["expect"]
    # # print(f"expect是：{expect}")
    # # real = dict1["real"]
    # # print(f"real是：{real}")
    # # status = dict1["status"]
    # # print(f"status是:{status}")
    # #ch = ConfigHttp(id, value, method, url, expect, real, status)
    # ch = ConfigHttp(value, method, url)
    # print(type(ch.run()))
    # print(ch.run())
    # print(f"ch是：{ch}")

