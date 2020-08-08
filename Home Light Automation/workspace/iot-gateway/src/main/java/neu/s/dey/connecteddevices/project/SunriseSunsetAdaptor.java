package neu.s.dey.connecteddevices.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SunriseSunsetAdaptor { 
	
	private static final String USER_AGENT = "Mozilla/5.0";
	public static String sunrise = null;
	public static String sunset = null;
	public static int sunrisediff = 0;
	public static int sunsetdiff = 0;

	// Define constructor
    public SunriseSunsetAdaptor() {
    	
    }
	
    // Get URL using latitude and longitude value of Constrained device 
    public static String getURL(double lat, double lng)	{   
    	String GET_URL = "https://api.sunrise-sunset.org/json?lat=" + lat + "&lng=" + lng + "&date=today";	
    	return GET_URL;
    }
    
    // Perform HTTP GET request with latitude, longitude. This API returns sunset and sunrise time in UTC time. 
    // Convert the time from UTC time to Constrained device timezone time
    public static void sendGET(double lat, double lng, String timezone) throws IOException {
    	String GET_URL = getURL(lat, lng);
    	URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
			DataUtil util = new DataUtil();
			SunriseSunsetData sunobj = util.toSunriseSunsetDataFromJson(response.toString());
			
			final String sunrisein12 = String.valueOf(java.time.LocalDate.now()) + " " + sunobj.results.sunrise;
			final String sunsetin12 = String.valueOf(java.time.LocalDate.now()) + " " + sunobj.results.sunset;
			//Format of the date defined in the input String
		    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
		    //Desired format: 24 hour format: Change the pattern as per the need
		    DateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date d1 = null;
		    Date d2 = null;
		    String sunrisein24 = null;
		    String sunsetin24 = null;
		    try{
		       //Converting the input String to Date
		       d1 = df.parse(sunrisein12);
		       d2 = df.parse(sunsetin12);
		       //Changing the format of date and storing it in String
		       sunrisein24 = outputformat.format(d1);
		       sunsetin24 = outputformat.format(d2);
		    }catch(ParseException pe){
		       pe.printStackTrace();
		    }
		     
			ZonedDateTime zonedDateTime1 = ZonedDateTime.parse(sunrisein24.replace(" ", "T") + "+00:00");
			ZonedDateTime currentTime1 = zonedDateTime1.withZoneSameInstant(ZoneId.of(timezone));
			//DateTimeFormatter zoneFormat1 = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma");
			DateTimeFormatter zoneFormat1 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
			sunrise = zoneFormat1.format(currentTime1);	
			 
			ZonedDateTime zonedDateTime2 = ZonedDateTime.parse(sunsetin24.replace(" ", "T") + "+00:00");
			ZonedDateTime currentTime2 = zonedDateTime2.withZoneSameInstant(ZoneId.of(timezone));
			//DateTimeFormatter zoneFormat2 = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma");
			DateTimeFormatter zoneFormat2 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
			sunset = zoneFormat2.format(currentTime2);	
			 
			System.out.println("Sunrise: " + sunrise);
			System.out.println("Sunset: " + sunset);
			 
			LocalDateTime today = LocalDateTime.now();  //Create date			 
			ZoneId id = ZoneId.of(timezone);  //Create timezone			  
			ZonedDateTime zonedDateTime = ZonedDateTime.of(today, id);
			DateTimeFormatter ctformat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
			String currentdt = ctformat.format(zonedDateTime);	
			System.out.println("Current Date Time: " + currentdt);
			 			  
			String sunrisetimeStr = LocalDateTime.parse(sunrise, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")).format(DateTimeFormatter.ofPattern("HH:mm"));
			int sunriseinmin = (int) (Integer.valueOf(sunrisetimeStr.substring(0, 2)) * 60 + Integer.valueOf(sunrisetimeStr.substring(3)));
			 
			String sunsettimeStr = LocalDateTime.parse(sunset, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")).format(DateTimeFormatter.ofPattern("HH:mm"));
			int sunsetinmin = (int) (Integer.valueOf(sunsettimeStr.substring(0, 2)) * 60 + Integer.valueOf(sunsettimeStr.substring(3)));
			 
			String currenttimeStr = LocalDateTime.parse(currentdt, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")).format(DateTimeFormatter.ofPattern("HH:mm"));
			int currenttimeinmin = (int) (Integer.valueOf(currenttimeStr.substring(0, 2)) * 60 + Integer.valueOf(currenttimeStr.substring(3)));
			 	 
			sunrisediff = currenttimeinmin - sunriseinmin;
			sunsetdiff = currenttimeinmin - sunsetinmin;
			System.out.println("Time diff between Sunrise and Current time " + sunrisediff);
			System.out.println("Time diff between Sunset and Current time " + sunsetdiff);


		} else {
			System.out.println("GET request to sunrise-sunset API is not working!!");
		}
    }  
 
}
