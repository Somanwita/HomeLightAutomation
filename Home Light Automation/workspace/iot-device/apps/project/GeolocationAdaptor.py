'''
Created on 29-Mar-2020

@author: soman
'''

# Import Libraries
import requests
import uuid, re 
import json
import datetime 
from project import DataUtil

class GeolocationAdaptor():
    
    # Define constructor    
    def __init__(self):
        self.geolocURL = "https://www.googleapis.com/geolocation/v1/geolocate?key=ENTER GOOGLEAPIKEY"
        self.MAC_address = self.getMACaddr()
        self.PARAMS = { "macAddress": self.MAC_address }
 
    # Retrieve MAC address of the system
    def getMACaddr(self):
        MACADDR = ':'.join(re.findall('..', '%012x' % uuid.getnode()))
        return str(MACADDR)

    # Send HTTP POST request using URL and MAC address
    def sendGeoloccationReq(self):
        r = requests.post(url = self.geolocURL, params = self.PARAMS) 
        geolocpayload = r.json()
        return geolocpayload

    # Send HTTP GET request using URL and latitude, longitude and current timestamp and API key  
    def sendTimezoneReq(self):
        geolocpayload = self.sendGeoloccationReq()
        geolocjsonData = str(geolocpayload).replace("\'", "\"")
        gdDict = json.loads(geolocjsonData)     
        jsonlocation = gdDict['location']
        lat = jsonlocation['lat'] 
        lng = jsonlocation['lng'] 
        now = datetime.datetime.now()
        midnight = datetime.datetime.combine(now.date(), datetime.time())
        timeStamp = (now - midnight).seconds
        timezoneURL = "https://maps.googleapis.com/maps/api/timezone/json?location=" + str(lat) + "," + str(lng) + "&timestamp=" + str(timeStamp) + "&key=AIzaSyB0mgVbal5dKDOtAhrFLMRc8hllnHeY6P8"
        r = requests.get(url = timezoneURL) 
        timezonepayload = r.json()
        return timezonepayload

    # Main function to retrieve latitude, longitude and time zone of the device and create GeolocationData object
    def getGeolocationDatafromAPI(self):
        geolocpayload = self.sendGeoloccationReq()
        timezonepayload = self.sendTimezoneReq()
        util = DataUtil.DataUtil()
        locpayload = util.toGeolocationDataFromJson(geolocpayload, timezonepayload)
        return locpayload