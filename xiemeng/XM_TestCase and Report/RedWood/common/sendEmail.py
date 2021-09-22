"""
功能描述：
编写人：AriaXie
编写日期：
实现逻辑：
1-配置邮箱属性
2-组装邮件内容和标题
3-登录并发送邮件
"""
from common.logs import logger
import smtplib,time,os
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.header import Header
class SendEmail():
    def __init__(self):
        self.sender = 'm13910404362@163.com'
        self.receiver = 'm13910404362@163.com, 506131922@qq.com'
        t = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
        self.subject = "RedWood_HomeWork" + t
        self.connect = "127.0.0.1:5000/toolbox/create接口自动化测试报告，内容详见附件"
        self.smtpserver = "smtp.163.com"
        self.username = 'm13910404362@163.com'
        self.password = 'YYUAIKAQMOPGXQTP'

    def __config(self):
        cur_dir = os.path.dirname(os.path.dirname(__file__)) + '/testReport'
        # print(cur_dir)
        file = cur_dir + '/' + os.listdir(cur_dir)[-1]
        # print(file)
        with open(file) as f:
            self.mail_body = f.read()
            self.msg = MIMEMultipart()
            self.att = MIMEText(self.mail_body, 'plain', 'utf-8')
            self.att['Content-Type'] = 'application/octet-stream'
            self.att['Content-Disposition'] = 'attachment; filename = report.html'
            self.msg.attach(self.att)
            self.msg.attach(MIMEText(self.connect, 'html', 'utf-8'))
            self.msg['Subject'] = Header(self.subject, 'utf-8')
            self.msg['From'] = self.sender
            self.msg['To'] = self.receiver
    def sendEmail(self):
        self.__config()
        try:
            s = smtplib.SMTP()
            s.connect(self.smtpserver)
            s.login(self.username, self.password)
            s.sendmail(self.sender, self.receiver.split(','), self.msg.as_string())
        except Exception as msg:
            logger.error(f"邮件发送失败:{msg}")
        finally:
            logger.info("邮件发送成功")
if __name__ == '__main__':
    s = SendEmail()
    s.sendEmail()
