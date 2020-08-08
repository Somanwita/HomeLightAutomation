package neu.s.dey.connecteddevices.project;

// Import Libraries
import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;

public class CoapServerManager {

	// Define static variable
	private static final Logger _Logger = Logger.getLogger(CoapServerManager.class.getName());

	public static GeolocationResourceHandler geolocHandler = null;
	
	// Define private variable
	private CoapServer _coapServer;
	
	// Define constructors
	public CoapServerManager()	{
		super();
	}
	
	// Define public method to add CoapResource resoucrce
	public void addResource(CoapResource resource)	{
		if (resource != null) {
			_coapServer.add(resource);
		}
	}
	
	// Define method for Creating CoAP server instance and GeolocationResourceHandler instance. Adds GeolocationResourceHandler resource to CoapServer. 
	// Starts the Coap server
	public void start()	{
		if (_coapServer == null) {
			_Logger.info("Creating CoAP server instance and Geo location Resource Handler...");
			_coapServer = new CoapServer();
			geolocHandler = new GeolocationResourceHandler();
			_coapServer.add(geolocHandler);
		}
		_Logger.info("Starting CoAP server...");
		_coapServer.start();

	}
	
	// Define method to Stop Coap server
	public void stop()	{
	_Logger.info("Stopping CoAP server...");
	_coapServer.stop();
	}
}
