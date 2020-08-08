package neu.s.dey.connecteddevices.common;

import java.io.File;

public class ConfigConst {
	
	
	public static final Object SMTP_PROP_HOST_KEY = "mail.smtp.host";;
	public static final Object SMTP_PROP_AUTH_KEY = "mail.smtp.auth";
	public static final Object SMTP_PROP_PORT_KEY = "mail.smtp.port";
	public static final Object SMTP_PROP_ENABLE_TLS_KEY = "mail.smtp.starttls.enable";

	public static String SECTION_SEPARATOR     = ".";

	public static File DEFAULT_CONFIG_FILE_NAME = new File ("/home/pi/workspace/iot-device/config/ConnectedDevicesConfig.props");

	public static String DEFAULT_HOST          = "127.0.0.1";

	public static String CLOUD         		= "cloud";
	public static String SMTP           		= "smtp";
	public static String GATEWAY_DEVICE 		= "gateway";
	public static String CONSTRAINED_DEVICE    = "device";

	public static String SMTP_CLOUD_SECTION = SMTP + SECTION_SEPARATOR + CLOUD;

	public static String FROM_ADDRESS_KEY      = "fromAddr";
	public static String TO_ADDRESS_KEY        = "toAddr";

	public static String HOST_KEY              = "host";
	public static String PORT_KEY              = "port";
	public static String SECURE_PORT_KEY       = "securePort";

	public static String USER_NAME_TOKEN_KEY   = "userNameToken";
	public static String USER_AUTH_TOKEN_KEY   = "authToken";

	public static String ENABLE_AUTH_KEY       = "enableAuth";
	public static String ENABLE_CRYPT_KEY      = "enableCrypt";
	
	public static String SMTP_PROP_SOCKETFACTORY_CLASS     = "socketFactory";


}
