package neu.s.dey.connecteddevices.project;

//Import Libraries
import java.util.Timer;
import java.util.TimerTask;
import java.io.UnsupportedEncodingException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.logging.*;

public class SystemPerformanceAdaptor {
	   float cpuUtil = 0.0f;
	   float memUtil = 0.0f;
	   String perfcpuData;
	   String perfmemData;
	   boolean isalertNotified = false;
	   
//	   Define Logger	   
	   public final Logger LOGGER =  Logger.getLogger(SystemPerformanceAdaptor.class.getSimpleName()); 

	   // Send email notification if CPU utilization or Memory Utilization is less than 0 or more than 100
	   public void sendNotification() {
 		   try {
			   if ((cpuUtil >= 100) || (memUtil >= 100) || (cpuUtil <= 0) || (memUtil <= 0)) {
				   String subject = "Poor System Performance";
				   String message = perfcpuData + perfmemData;
				   SmtpClientConnector smtpConnector = new SmtpClientConnector();
				   smtpConnector.publishMessage(subject, message.getBytes());
				   isalertNotified = true;
			   }
		   } catch (Exception e) {
			   LOGGER.log(Level.WARNING, "Failed to send SMTP message.", e);
		   }
	   	}  
	 
	   public void sysPerformanceAdaptor() throws InterruptedException { 
		   
// 			  Create timer to schedule the task (named timertask) run continuously after a regular interval
	          Timer timer = new Timer();

//			  Create the target task (named timertask) which should run continuously	          
	          TimerTask timertask = new TimerTask() { 
	          @Override
	          public void run() { 

	        	  final Logger LOGGER =  Logger.getLogger(GatewayHandlerApp.class.getSimpleName()); 
	        	 
	              try {
//	          		Instantiate OperatingSystemMXBean management interface from ManagementFactory class	to Retrieve System CPU utilization 
	            	cpuUtil =  (float) (Math.rint((Double) ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "SystemCpuLoad") * 1000) / 10);

	            	//Instantiate OperatingSystemMXBean management interface from ManagementFactory class		
//	          		Retrieve free physical memory size and total physical memory size		
	          		long freemem = (Long) ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "FreePhysicalMemorySize");
	          		long totalmem = (Long) ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "TotalPhysicalMemorySize");
	          		
//	          		Calculate memory usage utilization
	          		memUtil = (float)Math.rint(((totalmem-freemem)*100)/totalmem);
	            	  
	                perfcpuData = " CPU Utilization=" + String.valueOf(cpuUtil);
	            	perfmemData = " Memory Utilization=" + String.valueOf(memUtil);

//		        	Get the log messages using Java built in Logger class	            	  
	            	LOGGER.info(perfcpuData);
	            	LOGGER.info(perfmemData);
	            	sendNotification();
	            	  
	              } catch (Exception e) {
	              	 e.printStackTrace();
	              }
	          }};

//			  Schedules the execution after every 3 seconds	          	          
	          timer.schedule(timertask,0,3000);
	          
// 			  Thread Sleep for 10 seconds 
	          Thread.sleep(100000);  
	   }
	   
		public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException //, MqttException
		{
			SystemPerformanceAdaptor obj = new SystemPerformanceAdaptor();
			obj.sysPerformanceAdaptor();
		}
	 
}
