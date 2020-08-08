package neu.s.dey.connecteddevices.labs.module01;

//Import Libraries
import java.lang.management.ManagementFactory;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class SystemMemUtilTask {
	
	public float getDataFromSensor() throws Exception  {

//		Instantiate OperatingSystemMXBean management interface from ManagementFactory class		
//		Retrieve free physical memory size and total physical memory size		
		long freemem = (Long) ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "FreePhysicalMemorySize");
		long totalmem = (Long) ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "TotalPhysicalMemorySize");
		
//		Calculate memory usage utilization
		float memUtil = (float)Math.rint(((totalmem-freemem)*100)/totalmem);
		return memUtil;
		
	}

}
