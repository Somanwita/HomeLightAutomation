'''
Created on 26-Jan-2020

@author: soman
'''

class GeolocationData():

#   Define variable for Latitude value of current location
    lat = 0.0;

#   Define variable for Longitude value of current location     
    lng = 0.0;
    
#   Define variable for Timezone of current location     
    timezone = None;

#   Define GeolocationData constructor
    def __init__(self): 
        super()
        
#   Define getter method for Latitude
    def getlat(self):
        return self.lat

#   Define getter method for Longitude    
    def getlng(self):
        return self.lng
 
#   Define setter method for Latitude    
    def setlat(self, lat):
        self.lat = lat
 
#   Define setter method for Longitude    
    def setlng(self, lng):
        self.lng = lng 
        
#   Define getter method for Longitude    
    def gettimezone(self):
        return self.timezone
 
#   Define setter method for Latitude    
    def settimezone(self, timezone):
        self.timezone = timezone

#    String representation of GeolocationData object
    def __str__(self):
        String_obj = "Latitude=" + str(self.lat) + "," + "Longitude=" + str(self.lng) + "," + "Timezone=" + self.timezone
        return String_obj  
    