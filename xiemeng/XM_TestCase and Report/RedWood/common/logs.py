"""
功能描述：
编写人：AriaXie
编写日期：2021.9.18
实现逻辑：打印日志
"""
import logging
def log():
    logging.basicConfig(level=logging.DEBUG, format="%(filename)s=%(name)s-%(asctime)s-[line:%(lineno)d]-日志信息：%(message)s")
    logger = logging.getLogger("API_Auto_test")
    return logger
logger = log()
if __name__ == '__main__':
    logger.info("测试日志")
