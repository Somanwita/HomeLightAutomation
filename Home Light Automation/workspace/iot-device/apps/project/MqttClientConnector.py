'''
Created on 26-Feb-2020

@author: soman
'''

#Import Libraries
from threading import Thread
import time
import paho.mqtt.client as mqtt
from project import PhilipsHueActivator

# Initialize jsondata and message
message = 'My message'  
global json_data

# Define MqttClientConnector class
class MqttClientConnector(Thread):   
    # Set host = "mqtt.eclipse.org", port = 1883, keepAlive interval 5, quality of service 2
    #host = "mqtt.eclipse.org"
    host = "Enter host"
    port = 1883
    keepAlive = 5000
    qos = 2

    # Define constructor
    def __init__(self,topic=None):
        self.topic = topic;
        self.client = mqtt.Client()
    
    # Define Callback function on_connect, subscribe to the topic
    def on_connect(self,client,userdata,flags,rc):
        #print("Connected with Client: " + str(rc))
        self.client.subscribe("lightstate/test")   
     
    # Define Callback function on_message to retrieve message    
    def on_message(self,client,userdata,msg):
        global json_data        
        json_data = str(msg.payload.decode("utf-8","ignore"))
        print('Display the Actuated lightstate value received from GatewayDevice :' + json_data)
        lightstate = int(json_data)
        print(lightstate)
        hueactivator = PhilipsHueActivator.PhilipsHueActivator()
        hueactivator.setLight(lightstate)
        return json_data
    
    # Define method to publish message. This method is not used as of now   
    def publish(self,topic,message,host,port):
        self.client.connect(host,port)
        self.client.publish(topic,message)
    
    #  Establish Connection, Subscribe to topic referring callback functions    
    def subscription(self,host,port,keepAlive,qos):
        self.client.on_connect = self.on_connect
        self.client.on_message = self.on_message
        self.client.connect(host,port,keepAlive)
        self.client.loop_start()
    
    #  Unsubscribe the topic and Disconnet   
    def Disconnect(self):
        time.sleep(10)
        self.client.unsubscribe(self.topic)
        self.client.loop_stop()
        self.client.disconnect()
        print("Client Disconnected!!")
    
    # Define message    
    def message(self):
        global json_data
        return json_data