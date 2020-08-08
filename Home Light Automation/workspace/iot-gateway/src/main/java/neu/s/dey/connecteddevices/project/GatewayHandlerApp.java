package neu.s.dey.connecteddevices.project;

//Import Libraries
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import com.labbenchstudios.iot.common.BaseDeviceApp;
import com.labbenchstudios.iot.common.DeviceApplicationException;
import neu.s.dey.connecteddevices.project.GeolocationData;
import neu.s.dey.connecteddevices.project.CoapServerManager;

public class GatewayHandlerApp extends BaseDeviceApp
{
	// static	
	private static final Logger _Logger = Logger.getLogger(GatewayHandlerApp.class.getSimpleName());
	
	/**
	 * Main method - application entrance.
	 * Instantiate GatewayHandlerApp class to retrieve Geolocation data collected by DeviceHandler app
	 * @param args Command line args.
	 * @throws MqttException 
	 * @throws UnsupportedEncodingException 
	 */
	
	public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException, MqttException
	{
		GatewayHandlerApp app = new GatewayHandlerApp(GatewayHandlerApp.class.getSimpleName());
		app.startApp();
		CoapServerManager server = new CoapServerManager();
		SunriseSunsetAdaptor sunadaptor = new SunriseSunsetAdaptor();

		server.start();	

//		Create timer to schedule the task (named timertask) run continuously after a regular interval
        Timer timer = new Timer();

//		Create the target task (named timertask) which should run continuously	                 
        TimerTask timertask = new TimerTask() { 

        @Override
        public void run() {     
    		try {
    			Thread.sleep(30000);
        		//System.out.println(server.geolocHandler.geolocdata.toString());
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
			server.stop();
    		
			//Retrieve Geolocation data from 
			try {
				double lat = server.geolocHandler.geolocdata.getlat();
				double lng = server.geolocHandler.geolocdata.getlng();
				String timezone = server.geolocHandler.geolocdata.gettimezone();
				sunadaptor.sendGET(lat, lng, timezone);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Instantiates UbidotsClientConnection to update Variables in Ubidots
    		UbidotsClientConnection httpobj = new UbidotsClientConnection();
    		try {
    			System.out.println("Update two Variables in Ubidots through HTTPS API with the time difference value");
    			httpobj.pubsubthruAPI(sunadaptor.sunrisediff, sunadaptor.sunsetdiff);
    		} catch (MalformedURLException e1) {
    			e1.printStackTrace();
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		} 
  
        }};
      
        //	Schedules the execution after every 60 seconds	          	          
        timer.schedule(timertask,0,60000);
        
//		Thread Sleep for 10 seconds 
        Thread.sleep(100000);  
	}
	
	// private var's
	
	
	// constructors
	
	public GatewayHandlerApp()
	{
		super();
	}
	
	public GatewayHandlerApp(String appName)
	{
		super(appName);
	}
	
	// protected methods
	
	@Override
	protected void start() throws DeviceApplicationException
	{
		_Logger.info("Hello - GatewayHandlerApp here!");
		
	}
	
	@Override
	protected void stop() throws DeviceApplicationException
	{
		_Logger.info("Stopping GatewayHandlerApp app...");
	}
	
}

