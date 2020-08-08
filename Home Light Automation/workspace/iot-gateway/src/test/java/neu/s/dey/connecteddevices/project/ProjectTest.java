/**
 * 
 */
package neu.s.dey.connecteddevices.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.management.ObjectName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import neu.s.dey.connecteddevices.project.SmtpClientConnector;
import neu.s.dey.connecteddevices.project.DataUtil;
import neu.s.dey.connecteddevices.project.GeolocationData;

/**
 * Test class for all requisite Project functionality.
 * 
 * Instructions:
 * 1) Rename 'testSomething()' method such that 'Something' is specific to your needs; add others as needed, beginning each method with 'test...()'.
 * 2) Add the '@Test' annotation to each new 'test...()' method you add.
 * 3) Import the relevant modules and classes to support your tests.
 * 4) Run this class as unit test app.
 * 5) Include a screen shot of the report when you submit your assignment.
 * 
 * Please note: While some example test cases may be provided, you must write your own for the class.
 */
public class ProjectTest
{
	// setup methods
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}
	
	// test methods
	
	/**
	 * Gateway Device app retrieves GeolocationData in JSON format, then converts it to GeolocationData object. Test for each field if thery are equal.
	 */
	@Test
	public void testJSONtoGeolocationDataconversion()
	{
		CoapServerManager server = new CoapServerManager();
		server.start();	
		
		try {
			Thread.sleep(30000);
    		//System.out.println(server.geolocHandler.geolocdata.toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		DataUtil util = new DataUtil();
		
		GeolocationData geolocationdataobj = server.geolocHandler.geolocdata;
	
		String converted_in_Json = util.toJsonFromGeolocationData(geolocationdataobj);
		GeolocationData converted_in_GeolocationData = util.toGeolocationDataFromJson(converted_in_Json);
		
		//Test each field are same for response message.
		assertTrue(geolocationdataobj.getlat() == converted_in_GeolocationData.getlat());
		assertTrue(geolocationdataobj.getlng() == converted_in_GeolocationData.getlng());
	}
	
	/**
	 * Gateway Device app retrieves SunsetSunrise in JSON format, then converts it to GeolocationData object. Test for each field if thery are equal.
	 * @throws IOException 
	 */
	@Test
	public void testJSONtoSunsetSunriseDataconversion() throws IOException
	{
    	String GET_URL = "https://api.sunrise-sunset.org/json?lat=" + 42.3624704 + "&lng=" + -71.0639616 + "&date=today";
    	URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
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
			
			String converted_in_Json = util.toJsonFromSunriseSunsetData(sunobj);
			SunriseSunsetData converted_in_SunriseSunsetData = util.toSunriseSunsetDataFromJson(converted_in_Json);
			
				
			//Test each field are same for response message.
			assertTrue(sunobj.results.sunrise.equals(converted_in_SunriseSunsetData.results.sunrise));
			assertTrue(sunobj.results.sunset.equals(converted_in_SunriseSunsetData.results.sunset));
		}
		else {
			System.out.println("Unit test failed");
		}
	}
	
	/**
	 * Test If CPU Utilization value is Correct
	 */
	@Test
	public void testCPU_util_isvalid() {
		try {
			float cpuUtil = (float) (Math.rint((Double) ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "SystemCpuLoad") * 1000) / 10);
			System.out.println(cpuUtil);
			assertTrue("Bad CPU Utilization Value!" + String.valueOf(cpuUtil), (cpuUtil >= 0 && cpuUtil <= 100));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * Test If CPU Utilization value is Incorrect
	 */
	@Test		
	public void testCPU_util_isInvalid() {
		try {
			float cpuUtil = (float) (Math.rint((Double) ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "SystemCpuLoad") * 1000) / 10);
			assertFalse("Bad CPU Utilization Value!" + String.valueOf(cpuUtil), (cpuUtil < 0 || cpuUtil > 100));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	*Test If Memory Utilization value is Correct 
	*/
	@Test	
	public void testMemory_util_isvalid() {
		try {
      		long freemem = (Long) ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "FreePhysicalMemorySize");
      		long totalmem = (Long) ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "TotalPhysicalMemorySize");      		
      		float memUtil = (float)Math.rint(((totalmem-freemem)*100)/totalmem);
			assertTrue("Bad memory Utilization value!" + String.valueOf(memUtil), (memUtil >= 0 && memUtil < 100));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	/**
	*Test If Memory Utilization value is Incorrect 
	*/
	@Test			
	public void testMemory_util_isInvalid() {
		try {
      		long freemem = (Long) ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "FreePhysicalMemorySize");
      		long totalmem = (Long) ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "TotalPhysicalMemorySize");      		
      		float memUtil = (float)Math.rint(((totalmem-freemem)*100)/totalmem);
			assertFalse("Bad memory Utilization Value!" + String.valueOf(memUtil), (memUtil < 0 || memUtil > 100));
			assertFalse("Bad memory Utilization Value!" + String.valueOf(memUtil), (memUtil == 100));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *	Test for valid target email address 
	 */
	@Test
	public void testvalidtargetemail() {	
		SmtpClientConnector smtpconnection = new SmtpClientConnector();
		try {
			String email = "somanwita21@gmail.com";
			InternetAddress toAddr = new InternetAddress(email);
			smtpconnection.isValid_email(toAddr);
			
		} catch (AddressException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 *	Test for invalid target email address 
	 */
	@Test
	public void testinvalidtargetemail() {	
		SmtpClientConnector smtpconnection = new SmtpClientConnector();
		try {
			String foobar = "foobar";
			InternetAddress toAddr = new InternetAddress(foobar);
			smtpconnection.isValid_email(toAddr);
		} catch (AddressException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 	Test whether alert notification is sent correctly for Poor system Performance
	 */
	@Test
	public void testalertNotified1()
	{
		SystemPerformanceAdaptor adaptor = new SystemPerformanceAdaptor();
		adaptor.cpuUtil = 0;
		adaptor.memUtil = 100;
		adaptor.sendNotification();
		assertTrue(adaptor.isalertNotified == true);
	}
	
	/**
	 * 	Test whether alert notification is not sent for Good system Performance
	 */
	@Test
	public void testalertNotified2()
	{
		SystemPerformanceAdaptor adaptor = new SystemPerformanceAdaptor();
		adaptor.cpuUtil = 20;
		adaptor.memUtil = 40;
		adaptor.sendNotification();
		assertTrue(adaptor.isalertNotified == false);
	}
	
}
