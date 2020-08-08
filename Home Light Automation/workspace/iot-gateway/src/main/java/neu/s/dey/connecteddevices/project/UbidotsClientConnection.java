package neu.s.dey.connecteddevices.project;

import com.ubidots.*;

import java.io.IOException;
import java.net.MalformedURLException;

public class UbidotsClientConnection {
	// Define all variables : Ubidots API key, Ubidots Data source Id, Ubidots Variable Id
	private static String apikey = "Enter Ubidots API Key";  
	private static String datasourceid = "Enter Ubidots Datasource Id";  
	private static String sunrisediffid = "Enter Ubidots Variable Id";
	private static String sunsetdiffid = "Enter Ubidots Variable Id";
	public Variable SunriseDiff;  
	public Variable SunsetDiff;  
	
    public UbidotsClientConnection() {
    	
    }
	
    // Update Ubidots variables using Ubidots SDK 
    public void pubsubthruAPI(int risediff, int setdiff) throws MalformedURLException, IOException {   
    	ApiClient api = new ApiClient(apikey);
    	DataSource iotdevice = api.getDataSource(datasourceid);
    	SunriseDiff = api.getVariable(sunrisediffid);
    	SunriseDiff.saveValue(risediff);
    	SunsetDiff = api.getVariable(sunsetdiffid);
    	SunsetDiff.saveValue(setdiff);
	        
    }
}
	   

