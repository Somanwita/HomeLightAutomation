'''
Created on 08-Apr-2020

@author: soman
'''

#Import Libraries
from project import MqttClientConnector
import time
import logging

#Format logging and print message
logging.basicConfig(format='%(asctime)s:%(levelname)s:%(message)s', level=logging.INFO)

# Set topic "lightstate/test"
topic = "lightstate/test"

# Set host 
host = "Enter Host"

# Set port number 1883
port = 1883

# Set keep alive interval 5 sec
keepAlive = 6000

# Set quality of service 1
qos = 2

# Instantiate MqttClientConnector
connector = MqttClientConnector.MqttClientConnector(topic)


logging.info("Starting Sensor and Actuation processing application daemon thread...")

while True:
    # Call MqttClientConnector methods to connect to broker, subscribe to topic, retrieve message 
    connector.subscription(host, port, keepAlive, qos)

    # After connection is established, wait for 15 seconds to receive Published message
    time.sleep(15)