'''
Created on 20-Feb-2020

@author: soman
'''

import json
import logging
from project.GeolocationData import GeolocationData

class DataUtil(object):

    def __init__(self):
        #Format logging and print message
        logging.basicConfig(format='%(asctime)s:%(levelname)s:%(message)s', level=logging.INFO)

    def toJsonFromGeolocationData(self, geolocdata):
        #method to convert GeolocationData to JSON
        geoloc_data={};
        geoloc_data['lat'] = geolocdata.getlat();
        geoloc_data['lng'] = geolocdata.getlng();
        geoloc_data['timezone'] = geolocdata.gettimezone();
        self.jsonSd = json.dumps(geoloc_data)            
        return self.jsonSd
     
    def toGeolocationDataFromJson(self,geolocjsonData, timezonepayload):
        #method to convert JSON to GeolocationData object
        geolocjsonData = str(geolocjsonData).replace("\'", "\"")
        gdDict = json.loads(geolocjsonData)     
        gd = GeolocationData()
        jsonlocation = gdDict['location']
        gd.lat = jsonlocation['lat'] 
        gd.lng = jsonlocation['lng'] 
        print(timezonepayload)
        gd.timezone = timezonepayload["timeZoneId"] 
        return gd