package neu.s.dey.connecteddevices.common;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

public class SensorData implements Serializable	{

//	Define variable for Current Timestamp 
	public String timeStamp = null;

//	Define variable for name
	public String name = "Not Set";

//	Define variable for current Temperature value 	
	public float curValue = 0.0f;

//	Define variable for average Temperature value 	
	public float avgValue = 0.0f;
	
//	Define variable for minimum Temperature value 	
	private float minValue = 0.0f;

//	Define variable for maximum Temperature value 
	private float maxValue = 0.0f;

//	Define variable for total Temperature value 	
	private float totValue = 0.0f;

//	Define variable for Count value 	
	public int sampleCount = 0;

//	Retrieve timestamp 	
	public Timestamp updateTimeStamp() {
		 Date date = new Date();		 	 
		 Timestamp ts = new Timestamp(date.getTime());
		 return ts;		
	}

//	Define SensorData Constructor
	public SensorData()	{
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

//	Calculate values of sampleCount, totValue, minValue, avgValue	
	public void addValue(float val) {
		updateTimeStamp(); 
		++this.sampleCount;
		this.curValue = val;
		this.totValue += val;
		if (this.curValue < this.minValue) {
			this.minValue = this.curValue;
		}
		if (this.curValue > this.maxValue) {
			this.maxValue = this.curValue;
		}
		if (this.totValue != 0 && this.sampleCount > 0) {
			this.avgValue = this.totValue / this.sampleCount;
		}
	}

//	Define getter method for Current Value	
	public float getCurValue() {
		return curValue;
	}

//	Define getter method for Average Value	
	public float getAvgValue() {
		return avgValue;
	}

//	Define getter method for Minimum Value	
	public float getMinValue() {
		return minValue;
	}

//	Define getter method for Maximum Value	
	public float getMaxValue() {
		return maxValue;
	}

//	Define getter method for Count	
	public int getSampleCount() {
		return sampleCount;
	}

//	Define the custom string to be printed in the output and to be sent via email on excessive Temperature	
	public String printString() {
        this.setName("Temperature");
        String customStr = 
            getName() + ":" + "\n" + "\tTime:    " + updateTimeStamp() +  "\n" + "\tCurrent: " + String.valueOf(this.curValue) + 
            "\n" + "\tAverage: " + String.valueOf(this.avgValue) + "\n" + "\tSample:  " + String.valueOf(this.sampleCount) +  "\n" + "\tMin:     " + String.valueOf(this.minValue) + "\n" + "\tMax:     " + String.valueOf(this.maxValue);
        return customStr ; 
	}
	
	public String toString() {
	       String String_obj = 
	          "Name=" + this.getName() + "," + 
	          "Timestamp=" + this.timeStamp +  "," + 
	          "Current Value=" + String.valueOf(this.getCurValue()) + "," + 
	          "Average Value=" + String.valueOf(this.getAvgValue()) + "," + 
	          "Sample Count=" + String.valueOf(this.getSampleCount()) +  "," + 
	          "Minimum Value=" + String.valueOf(this.getMinValue()) + "," + 
	          "Maximum Value=" + String.valueOf(this.getMaxValue());
	        
	           return String_obj; 
	}

}