'''
Created on 06-Feb-2020

@author: soman
'''

#Import Libraries
from project import SystemperformanceAlert
from project import CoapClientConnector
from project import GeolocationAdaptor
from project import DataUtil
from time import sleep
import logging

#Format logging and print message
logging.basicConfig(format='%(asctime)s:%(levelname)s:%(message)s', level=logging.INFO)

# Instantiate CoapClientConnector
connector = CoapClientConnector.CoapClientConnector()
geoloc = GeolocationAdaptor.GeolocationAdaptor()
util = DataUtil.DataUtil()
logging.info("Starting Sensor and Actuation processing application daemon thread...")
connector.daemon = True

# Starrt the App
#while True:
payload = geoloc.getGeolocationDatafromAPI()
print("GeolocationData object received using API : " + str(payload))
jsonloc = util.toJsonFromGeolocationData(payload)
print("Current Location Value POST using COAP: " + jsonloc)
connector.sendPostRequest("location", jsonloc);
#sleep(58)  

# Main function. Retrieve CPU utilization and Memory utilization. Send email notification using SMTP when
# CPU utilization or Memory utilization is less than 0 or more than 100    
if __name__ == '__main__':
    enableAdaptor = True
    obj = SystemperformanceAlert.SystemperformanceAlert()
    while True:
        if enableAdaptor:
            cpuUtil = obj.getCPUPercentage()
            memUtil = obj.getMemoryPercentage()
            perfcpuData = " CPU Utilization=" + str(cpuUtil)
            perfmemData = " Memory Utilization=" + str(memUtil)
            logging.info(perfcpuData)
            logging.info(perfmemData)
            if ((cpuUtil >= 100) or (memUtil >= 100)):
                publishData =  perfcpuData + perfmemData
                obj.publishMessage("Poor System Performance", publishData)
            elif ((cpuUtil <= 0) or (memUtil <= 0)):
                publishData =  perfcpuData + perfmemData
                obj.publishMessage("Poor System Performance", publishData)                
#             elif ((cpuUtil >= 20) or (memUtil >= 70)):
#                 publishData =  perfcpuData + perfmemData
#                 obj.publishMessage("Poor System Performance", publishData)
                
            sleep(30)

