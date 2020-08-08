'''
Created on 15-Apr-2020

@author: soman
'''

# Import Libraries
import logging
import psutil
import smtplib
from email.mime.text import MIMEText as mimeText
from email.mime.multipart import MIMEMultipart as mimeMultipart
import re
from time import sleep
 
class SystemperformanceAlert:    
    enableAdaptor = False
    logging.basicConfig(format='%(asctime)s:%(levelname)s:%(message)s', level=logging.DEBUG)
    logging.info("Starting system performance app daemon thread...")

    #   Define Constructor 
    def __init__(self):
        super(SystemperformanceAlert, self).__init__()

    #  Retrieve CPU percentage from psutil library       
    def getCPUPercentage(self):
        return psutil.cpu_percent(interval=1)

    #  Retrieve Memory percentage from psutil library       
    def getMemoryPercentage(self):
        mem = psutil.virtual_memory()
        return mem.percent
     
    #   Check if email id is valid or not
    def isvalid(self,email):  
        isvalidemail = False
        regex = '^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$'
        if(re.search(regex,email)): 
            isvalidemail = True 
        return isvalidemail

    #   SMTP configurations for sending e-mail notification 
    def publishMessage(self, topic, data):
        isconnected = False
        host = 'smtp.gmail.com'
        port = 465
        fromAddr = 'Enter From email address'
        toAddr = 'Enter To email address'
        
        if (self.isvalid(fromAddr) and self.isvalid(toAddr)):
            authToken = 'Enter Password'
            msg = mimeMultipart()
            msg['From'] = fromAddr        
            msg['To'] = toAddr
            msg['Subject'] = topic
            msgBody = str(data)
            msg.attach(mimeText(msgBody))
            msgText = msg.as_string()

            smtpServer = smtplib.SMTP_SSL(host, port)
            smtpServer.ehlo()
            smtpServer.login(fromAddr, authToken)
            smtpServer.sendmail(fromAddr, toAddr, msgText)
            smtpServer.close()
            isconnected = True
        return isconnected

                
                    
