package neu.s.dey.connecteddevices.project;

import com.labbenchstudios.iot.common.CertManagementUtil;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ActuationControllerApp {
	private static final Logger _Logger = Logger.getLogger(ActuationControllerApp.class.getSimpleName());
	
	CertManagementUtil certmgr = new CertManagementUtil();
	//	Set Keep Alive value 
	//private static final int KEEP_ALIVE_INTERVAL_DEFAULT = 86400;
	private static final int KEEP_ALIVE_INTERVAL_DEFAULT = Integer.MAX_VALUE;
	
    private static final String token = "Enter Token"; 
	   
    // Set Mqtt broker 
    public static String broker       = "ssl://industrial.api.ubidots.com:8883";

	//	Set topic 
    public static String topic        = "/v1.6/devices/home_automation-device/light-state-actuator/lv";
       
    //	Initialize content to be published
    public static String content      = null;
    
    // Set Quality of Service to 2
    public static int qos             = 2;
          
    public static String clientId     = "GatewayHandlerApp";
    public static MqttClient client;
    public static MemoryPersistence persistence = new MemoryPersistence();
    public int latestvalue = 0;
    
    public ActuationControllerApp() {
    	
    	
    }
    
    // Subscribe the generated message. On receiving new value for lightstate variable PUBLISH the value to Actuator
    public void connectmqtt(String topic) throws MqttException, UnsupportedEncodingException, InterruptedException {   

        	try {
        		client = new MqttClient(broker, clientId, persistence);      	
        		MqttConnectOptions connOpts = new MqttConnectOptions();        		       		
        		connOpts.setCleanSession(true);
        		connOpts.setUserName(token);
        		connOpts.setKeepAliveInterval(KEEP_ALIVE_INTERVAL_DEFAULT);
        		//System.out.println(KEEP_ALIVE_INTERVAL_DEFAULT);
        		connOpts.setSocketFactory(certmgr.loadCertificate("Enter path for ubidots certificate .pem file"));
        		client.setCallback(new MqttCallback() {

        		    @Override
        		    public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker 
        		    }

        		    @Override
        		    public void messageArrived(String topic, MqttMessage message) throws Exception {
        		    	//System.out.println((String.format("[%s] %s", topic, new String(message.getPayload()))));
        		    	latestvalue = Integer.valueOf(String.format(new String(message.getPayload())));
        				System.out.println("Publish the Actuated Light-State-Actuator value received from Ubidots through MQTT: " + latestvalue);
        		    	neu.s.dey.connecteddevices.project.MqttClientConnector pubobj = new neu.s.dey.connecteddevices.project.MqttClientConnector();
        		    	pubobj.connect();
        		    	pubobj.publish(latestvalue);
        		    	//pubobj.disconnect();
        		    }

        		    @Override
        		    public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete 
        		    }
        		});
        		client.connect(connOpts);
        		client.subscribe(topic, qos);
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
    public void disconnect() throws MqttException, UnsupportedEncodingException {
        try {
        	client.unsubscribe(topic);
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
    
    // Main method
    public static void main (String[] args) {
    	ActuationControllerApp mqtcon = new ActuationControllerApp();
    	
    	try {
			mqtcon.connectmqtt(topic);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
