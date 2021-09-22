"""
功能描述：查找测试用例， 执行用例， 生成报告， 发送邮件，自动清理报告
编写人：AriaXie
编写日期：2021.9.18
实现逻辑：
1-查找测试用例， 生成suite
    unittest.testLoader
2-执行用例并生成报告
    HTMLTestRunner
    1-导包
    2-定义报告文件名
    3-写入的方式打开
    4-HTMLTestRunner
    5-运行
    6-关闭
3-自动清理报告
    1-获取目标目录下的所有文件列表
    2-判断当前文件的个数，是否超过3个
        1-超过3个：
            1-遍历获取每个文件的创建时间，组成一个列表
            2-使用字典推导式，将文件列表和时间列表组成字典（键值对）
            3-将时间列表进行排序
            4-获取旧的文件列表
            5-遍历删除旧的文件
                根据时间获取对应的文件名称
                删除
"""
import time
from common.logs import logger
from HTMLTestRunner import HTMLTestRunner
import unittest, os
from common.sendEmail import SendEmail
pro_dir = os.path.dirname(__file__)
# print(f"pro_dir路径是：{pro_dir}")
report_dir = pro_dir + '/testReport'
# print(f"report_dir路径是：{report_dir}")
def creat_suite():
    # 指定目录：
    start_dir = os.path.dirname(__file__) + '/testCase'
    # print(f"start_dir路径是：{start_dir}")
    # 通过testloader来加载指定目录下的test开头的用例，运行添加好的discover
    suite = unittest.TestLoader().discover(start_dir = start_dir, pattern='test*.py', top_level_dir=None)
    return suite

def auto_clear():
    file_list = os.listdir(report_dir)
    list1 = []
    if len(file_list) >3:
        for i in file_list:
            list1.append(i.split('_')[0])
        dict1 = {list1[j]:file_list[j] for j in range(len(list1))}
        list2 = sorted(list1)
        for i in list2[:-3]:
            os.remove(report_dir + '/' + dict1[i])

if __name__ == '__main__':
    suite = creat_suite()
    cur_time = time.strftime("%Y-%m-%d %H-%M-%S", time.localtime())
    file = report_dir + '/' + cur_time + '_result.html'
    with open(file, 'wb') as fp:
        runner = HTMLTestRunner(stream=fp, verbosity=1, title='XM的测试报告', description='RedWood')
        runner.run(suite)
    se = SendEmail()
    se.sendEmail()
    logger.info('file----%s' % file)
    auto_clear()


