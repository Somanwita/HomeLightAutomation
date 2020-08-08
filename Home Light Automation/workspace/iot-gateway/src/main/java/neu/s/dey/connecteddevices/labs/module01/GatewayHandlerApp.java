package neu.s.dey.connecteddevices.labs.module01;

// Import Libraries
import java.util.logging.Logger;
import com.labbenchstudios.iot.common.BaseDeviceApp;
import com.labbenchstudios.iot.common.DeviceApplicationException;

public class GatewayHandlerApp extends BaseDeviceApp
{
	// static
	
	private static final Logger _Logger = Logger.getLogger(GatewayHandlerApp.class.getSimpleName());
	
	/**
	 * Main method - application entrance.
	 * Instantiate SystemPerformanceAdaptor class to retrieve CPU and Memory utilization percentage when Application is on
	 * @param args Command line args.
	 */
	public static void main(String[] args) 
	{
		GatewayHandlerApp app = new GatewayHandlerApp(GatewayHandlerApp.class.getSimpleName());
		app.startApp();
		SystemPerformanceAdaptor sysPerfAdaptor = new SystemPerformanceAdaptor();
		try {
			sysPerfAdaptor.sysPerformanceAdaptor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// private var's
	
	
	// constructors
	
	public GatewayHandlerApp()
	{
		super();
	}
	
	public GatewayHandlerApp(String appName)
	{
		super(appName);
	}
	
	// protected methods
	
	@Override
	protected void start() throws DeviceApplicationException
	{
		_Logger.info("Hello - GatewayHandlerApp here!");
		
	}
	
	@Override
	protected void stop() throws DeviceApplicationException
	{
		_Logger.info("Stopping GatewayHandlerApp app...");
	}
	
}

