package neu.s.dey.connecteddevices.project;

import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;;

// Define GeolocationResourceHandler class to handle different requests (GET, PUT, POST, DELETE) from Client
public class GeolocationResourceHandler extends CoapResource	{
	public String content      = null;
	private static final Logger _Logger = Logger. getLogger ( GeolocationResourceHandler.class. getName ());
	public GeolocationData geolocdata = new GeolocationData();
    DataUtil util = new DataUtil();
    
	// Define constructor
	public GeolocationResourceHandler ()	{
		super ( "location" , true );
		getAttributes().setTitle("Location Resource");
	}
	
	// Define method to handle POST request. Update GeolocationData sent from Constrained Device.
	// Then, converts it to JSON object using DataUtil and Sends Response message.
	@Override
	public void handlePOST(CoapExchange ce) {
		try {
			ce.accept();
			String content = ce.getRequestText();
			ce.respond(ResponseCode.CHANGED, content);	
			_Logger.info ("Received POST request from client.");
			geolocdata = util.toGeolocationDataFromJson(content);
			System.out.println(geolocdata.toString());
		} catch (Exception e) {
			ce.respond( ResponseCode.BAD_REQUEST, content);
		}
	}

}