package neu.s.dey.connecteddevices.project;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import neu.s.dey.connecteddevices.common.ConfigConst;
import neu.s.dey.connecteddevices.common.ConfigUtil;
import neu.s.dey.connecteddevices.labs.module01.GatewayHandlerApp;

public class SmtpClientConnector {

	public boolean isConnected;
	public File filename = new File ("C:\\User\\soman\\OneDrive\\Documents\\git\\workspace\\iot-gateway\\config\\ConnectedDevicesConfig.props");
	public final Logger _Logger =  Logger.getLogger(GatewayHandlerApp.class.getSimpleName());
	Session smtpSession;
	public boolean email_isvalid = false;
	
	// Define constructor
	public SmtpClientConnector() {

	}
	
	// Check if email id is valid
	public boolean isValid_email(InternetAddress email) {
	    try {
			email.validate();
			email_isvalid = true;
			_Logger.log(Level.INFO, "Valid address: " + email);
		} catch (AddressException e) {
			_Logger.log(Level.WARNING, "Invalid address: " + email);
		}
	    return email_isvalid;
	}
	
	// Perform SMTP connect
	public void connect() {
		if (! isConnected) {
			_Logger.info("Initializing SMTP gateway...");
			
			Properties props = new Properties();
        
			String portStr = String.valueOf(ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.PORT_KEY));
			String hostStr = String.valueOf(ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.HOST_KEY));
			String authkeyStr = String.valueOf(ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.ENABLE_AUTH_KEY));
			String tlskeyStr = String.valueOf(ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.ENABLE_CRYPT_KEY));
			String sockStr = String.valueOf(ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.SMTP_PROP_SOCKETFACTORY_CLASS));
					
			props.put(ConfigConst.SMTP_PROP_HOST_KEY, hostStr);
			props.put(ConfigConst.SMTP_PROP_PORT_KEY, portStr);
			props.put(ConfigConst.SMTP_PROP_AUTH_KEY, authkeyStr);
			props.put(ConfigConst.SMTP_PROP_ENABLE_TLS_KEY, tlskeyStr);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
						
			this.smtpSession = Session.getInstance(props, new Authenticator() {
				// Set the account information session，transport will send mail
				String authStr = String.valueOf(ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY));
				String fromAddrStr = String.valueOf(ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.FROM_ADDRESS_KEY));
				
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromAddrStr, authStr);
				}
			});			
			this.isConnected = true;
			
		} else {
			_Logger.info("No SMTP gateway connection.");
		}
	}
	
//	SMTP configurations for sending e-mail notification 
	public boolean publishMessage(String subject, byte[] payload) {
		if (! this.isConnected) {
			connect(); 
		}
		boolean success = false;
		String fromAddrStr = (String) ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.FROM_ADDRESS_KEY);	
		try {
			Message smtpMsg = new MimeMessage(this.smtpSession);
			InternetAddress fromAddr = new InternetAddress(fromAddrStr);			
			InternetAddress[] toAddr = InternetAddress.parse((String) ConfigUtil.getInstance().getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.TO_ADDRESS_KEY));
			boolean validtargetemail = false;
			for (int i=0; i < toAddr.length; i++ ) {
				isValid_email(toAddr[i]);
				validtargetemail = email_isvalid;
			}
			if (isValid_email(fromAddr) && validtargetemail) {
				smtpMsg.setFrom(fromAddr);
				smtpMsg.setRecipients(Message.RecipientType.TO, toAddr);
				smtpMsg.setSubject(subject);
				smtpMsg.setText(new String(payload));
				Transport.send(smtpMsg);
				success = true;
			}
		} catch (Exception e) {
			_Logger.log(Level.WARNING, "Failed to send SMTP message from address: " + fromAddrStr);
		}
		return success;
	}
}
