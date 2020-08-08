package neu.s.dey.connecteddevices.project;

import com.google.gson.Gson;
import neu.s.dey.connecteddevices.project.GeolocationData;;

public class DataUtil {
	
	//method to convert GeolocationData object to JSON
	public String toJsonFromGeolocationData(GeolocationData geolocdata)	{
		String jsonData = null;
		if (geolocdata != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(geolocdata);
		}
		return jsonData;
	}
	
	//method to convert JSON to GeolocationData object
	public GeolocationData toGeolocationDataFromJson(String jsonData)	{
		GeolocationData geolocdata = null;
		if (jsonData != null && jsonData.trim().length() > 0) {
			Gson gson = new Gson();
			geolocdata = gson.fromJson(jsonData, GeolocationData.class);
		}
		return geolocdata;
	}
	
	
	//method to convert SunriseSunsetData object to JSON
	public String toJsonFromSunriseSunsetData(SunriseSunsetData sunrisesunsetdata)	{
		String jsonData = null;
		if (sunrisesunsetdata != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(sunrisesunsetdata);
		}
		return jsonData;
	}
	
	//method to convert JSON to SunriseSunsetData object
	public SunriseSunsetData toSunriseSunsetDataFromJson(String jsonData)	{
		SunriseSunsetData sunrisesunsetdata = null;
		if (jsonData != null && jsonData.trim().length() > 0) {
			Gson gson = new Gson();
			sunrisesunsetdata = gson.fromJson(jsonData, SunriseSunsetData.class);
		}
		return sunrisesunsetdata;
	}
}
