'''
Created on 09-Apr-2020

@author: soman
'''

#Import Libraries
import requests
import threading
from time import sleep

#Define SenseHatLedActivator class as threaded class
class PhilipsHueActivator(threading.Thread):
    
    #Define SenseHatLedActivator constructor and define orientation of display
    def __init__(self):
        super(PhilipsHueActivator, self).__init__()
        self.username = "Enter User name"
        self.bridge_ip = "Enter Bridge ip"
        self.HueURL = "http://" + self.bridge_ip + "/api/" + self.username + "/lights/1/state"
            
    #Define actuation for different lightstate value   
    def setLight(self, lightstate):
        if (lightstate == 0) :            
            PARAMS_LIGHT_OFF = '{"on":false}'
            r = requests.put(url = self.HueURL, data = PARAMS_LIGHT_OFF) 
            print(r.content)
            
        elif (lightstate == 1) :                 
            PARAMS_LIGHT_ON = '{"on": true, "bri":254}'
            r = requests.put(url = self.HueURL,data = PARAMS_LIGHT_ON) 
            print(r.content)
            
        elif (lightstate == 2) :   
            brightness = 1
            PARAMS_LIGHT_ON = '{"on": true,"bri": 1}'
            r = requests.put(url = self.HueURL,data = PARAMS_LIGHT_ON) 
            print(r.content)
            sleep(5)
            while (brightness < 255) :
                PARAMS_LIGHT_GRAD_INC = '{"on":true, "bri_inc":20}'
                r = requests.put(url = self.HueURL,data = PARAMS_LIGHT_GRAD_INC) 
                brightness += 20
                print(r.content)
                sleep(5)
                
        elif (lightstate == 3) :   
            brightness = 254
            while (brightness > 1) :
                PARAMS_LIGHT_GRAD_DEC = '{"on":true, "bri_inc":-20}'
                r = requests.put(url = self.HueURL,data = PARAMS_LIGHT_GRAD_DEC) 
                brightness -= 20
                print(r.content)
                sleep(5)
        
        else:   return
