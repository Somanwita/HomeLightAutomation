package neu.s.dey.connecteddevices.labs.module01;

//Import Libraries
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import java.util.logging.*;

public class SystemPerformanceAdaptor {

	   public void sysPerformanceAdaptor() throws InterruptedException { 
		   
// 			  Create timer to schedule the task (named timertask) run continuously after a regular interval
	          Timer timer = new Timer();

//			  Create the target task (named timertask) which should run continuously	          
	          TimerTask timertask = new TimerTask() { 
	          @Override
	          public void run() { 

	        	  final Logger LOGGER =  Logger.getLogger(GatewayHandlerApp.class.getSimpleName()); 
	        	 
//				  Retrive cpu utilization percentage and memory utilization percentage from respective classes	        	  
	              SystemCpuUtilTask cpuobj = new SystemCpuUtilTask();
	              SystemMemUtilTask memobj = new SystemMemUtilTask();
	              try {
	            	  float cpuUtil = cpuobj.getDataFromSensor();
	            	  float memUtil = memobj.getDataFromSensor();
	            	  
	            	  String perfcpuData = " CPU Utilization=" + String.valueOf(cpuUtil);
	            	  String perfmemData = " Memory Utilization=" + String.valueOf(memUtil);

//		        	  Get the log messages using Java built in Logger class	            	  
	            	  LOGGER.info(perfcpuData);
	            	  LOGGER.info(perfmemData);
	            	  
	              	  } catch (Exception e) {
	              		  e.printStackTrace();
	              	  }
	          }};

//			  Schedules the execution after every 3 seconds	          	          
	          timer.schedule(timertask,0,3000);
	          
// 			  Thread Sleep for 10 seconds 
	          Thread.sleep(100000);  
	   }
	 
}
