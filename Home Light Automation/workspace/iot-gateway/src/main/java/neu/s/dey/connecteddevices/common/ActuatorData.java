package neu.s.dey.connecteddevices.common;

import java.sql.Timestamp;
import java.util.Date;

public class ActuatorData {
	
	public final static int COMMAND_OFF = 0;
	public final static int COMMAND_ON = 1;
	public final static int COMMAND_SET = 2;
	public final static int COMMAND_RESET = 3;
	public final static int COMMAND_HUMIDITY_SENSEHAT = 4;
	public final static int COMMAND_HUMIDITY_HTS221 = 5;

//	Define variable for name
	private String name = "Not Set";

//	Define variable for command	
	public int command = 0;

//	Define variable for value 	
	public Float value = 0.0f;

//	Retrieve timestamp 	
	private Timestamp updateTimeStamp() {
		 Date date= new Date();		 	 
		 Timestamp ts = new Timestamp(date.getTime());
		 return ts;		
	}

//	Define ActuatorData Constructor
	public ActuatorData()	{
		super();
		updateTimeStamp(); 
	}

//	Define getter method for Name	
	public String getName() {
		return name;
	}

//	Define setter method for Name	
	public void setName(String name) {
		this.name = name;
	}

//	Define getter method for Value	
	public float getValue() {
		return value;
	}
	
//	Define setter method for Value	
	public void setValue(Float value) {
		this.value = value;
	}
	
//	Define getter method for Command	
	public int getCommand() {
		return command;
	}
	
//	Define setter method for Command	
	public void setCommand(int command) {
		this.command = command;
	}
	
	public String toString() {
	       String String_obj = 
	          "Name=" + this.getName() + "," + 
	          "Command=" + String.valueOf(this.getCommand()) +  "," + 
	          "value=" + String.valueOf(this.getValue()) ;
	        
	           return String_obj; 
	}

}
