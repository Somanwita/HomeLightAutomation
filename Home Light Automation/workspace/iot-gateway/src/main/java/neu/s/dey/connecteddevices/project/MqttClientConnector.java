package neu.s.dey.connecteddevices.project;
import neu.s.dey.connecteddevices.common.SensorData;
import neu.s.dey.connecteddevices.common.DataUtil;
import java.util.Random;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttClientConnector implements MqttCallback	{
	private static final Logger _Logger = Logger.getLogger(MqttClientConnector.class.getSimpleName());
	
	//	Set Keep Alive value 5 
	private static final int KEEP_ALIVE_INTERVAL_DEFAULT = 5;
	
	//	Set topic "lightstate/test"
    String topic = "lightstate/test";
    
    //	Initialize content to be published
    public String content      = null;
    
    // Set Quality of Service to 2
    int qos             = 2;
    
    // Set Mqtt broker 
    String broker       = "tcp://localhost:1883";
    
    String clientId     = "GatewayHandlerApp";
    MqttClient client;
    MemoryPersistence persistence = new MemoryPersistence();
    public SensorData sensor = new SensorData();
    DataUtil util = new DataUtil();
    
    public MqttClientConnector() {
    	
    }
    
    // Connect to the broker and subscribe to the topic 
    public void connect() {
        try {
            client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(KEEP_ALIVE_INTERVAL_DEFAULT);
            client.connect(connOpts);
            client.setCallback(this);
            client.subscribe(topic); 
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        } 
    }
    
    // Publish the generated message
    public void publish(int latestvalue) {
    	content = String.valueOf(latestvalue); 
          try {
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos = 2);
            client.publish(topic, message);

        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }   	
    }
    
    // Disconnect after publish
    public void disconnect() {
        try {
            client.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        } 
    }

	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
