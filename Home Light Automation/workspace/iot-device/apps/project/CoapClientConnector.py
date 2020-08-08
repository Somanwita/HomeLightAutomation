'''
Created on 05-Mar-2020

@author: soman
'''

#Import Libraries
import logging
from coapthon.client.helperclient import HelperClient

#Define CoapClientConnector class
class CoapClientConnector():
    #Define Host, Port and Protocol
    #defaultHost = "californium.eclipse.org"
    defaultHost =  "Enter Host"
    defaultPort = 5683
    defaultProtocol = "coap"
    securePort = 5684
    secureProtocol = "DTLS" 
    
    #Define Constructor
    def __init__(self, host=defaultHost, protocol=defaultProtocol, port=defaultPort, isSecure=False):  
        self.host = host 
        self.protocol = protocol;
        self.port = port      
        self.serverAddr = self.protocol + "://" + self.host + ":" + str(self.port)     
        logging.info("Using URL for server conn: " + self.serverAddr); 
        self.clientConn = None
   
    # Define method to create connection between client and server
    def initClient(self, serverAddr, resourceName=None, clientConn = None):
        if (self.clientConn != None):
            self.clientConn.close()
            self.clientConn = None
        try:
            if (resourceName != None):
                self.serverAddr += "/" + resourceName
            else:
                self.serverAddr = serverAddr
            self.clientConn = HelperClient(server=(self.host, self.port))           
            logging.info("Created client connection to server / resource: " + self.serverAddr)
        except Exception:
            logging.exception("Failed to connect to broker: " + self.serverAddr)

    # Define method for Issuing discover
    def discoverResources(self):
        self.initClient(self.serverAddr, "location")
        response = self.clientConn.discover()
        print((response.pretty_print()))
        self.clientConn.stop()            
    
    # Define method for sending POST request using resource name and current Geolocation object as input.
    def sendPostRequest(self, resource, payload):
        self.initClient(self.serverAddr)
        response = self.clientConn.post(resource, payload);      
        if response:
            logging.info("Response Received From Server Upon POST Request")
            print(response.pretty_print())           
        else:
            print("Failed to send Post Request");            
        self.clientConn.stop()