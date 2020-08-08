package neu.s.dey.connecteddevices.labs.module01;

//import libraries
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;

import javax.management.ObjectName;

import com.sun.management.OperatingSystemMXBean;


public class SystemCpuUtilTask {
	
	public float getDataFromSensor() throws Exception  {

 /* 
    	com.sun.management.OperatingSystemMXBean osMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    	float memUsage = (float) osMXBean.getProcessCpuLoad();    
    	float cpuUsage = (float) osMXBean.getSystemCpuLoad();
    	System.out.println(cpuUsage);
*/
		
//		Instantiate OperatingSystemMXBean management interface from ManagementFactory class		
//		Retrieve Process CPU utilization and System CPU utilization 
		float proc_cpuUtil = (float) (Math.rint((Double)ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "ProcessCpuLoad") * 1000) / 10);
		float sys_cpuUtil = (float) (Math.rint((Double)ManagementFactory.getPlatformMBeanServer().getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "SystemCpuLoad") * 1000) / 10);
    
		return sys_cpuUtil;    
}

}

		