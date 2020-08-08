package neu.s.dey.connecteddevices.project;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

public class GeolocationData implements Serializable	{

//	Define variable for Latitude value of current location
	public double lat = 0.0;

//	Define variable for Longitude value of current location 	
	public double lng = 0.0;

//	Define variable for Timezone value of current location	
	public String timezone = null;
	
//	Define GeolocationData Constructor
	public GeolocationData()	{
		super();
	}

// Getter and Setter methods for lat, lng and timezone
	public double getlat() {
		return lat;
	}

	public void setlat(double lat) {
		this.lat = lat;
	}

	public double getlng() {
		return lng;
	}

	public void setlng(double lng) {
		this.lng = lng;
	}

	public String gettimezone() {
		return timezone;
	}

	public void settimezone(String timezone) {
		this.timezone = timezone;
	}
	
// String representation of GeolocationData object	
	public String toString() {
	       String String_obj = 
	          "lat=" + String.valueOf(this.getlat()) + "," + 
	          "lng=" + String.valueOf(this.getlng()) + "," +        
	       	  "timezone=" + String.valueOf(this.gettimezone());	        
	           return String_obj; 
	}

}