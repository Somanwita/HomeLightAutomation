package neu.s.dey.connecteddevices.common;

import com.google.gson.Gson;

public class DataUtil {
	
	//method to convert sensor data to JSON
	public String toJsonFromSensorData(SensorData sensorData)	{
		String jsonData = null;
		if (sensorData != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(sensorData);
		}
		return jsonData;
	}
	
	//method to convert JSON to sensor data object
	public SensorData toSensorDataFromJson(String jsonData)	{
		SensorData sensorData = null;
		if (jsonData != null && jsonData.trim().length() > 0) {
			Gson gson = new Gson();
			sensorData = gson.fromJson(jsonData, SensorData.class);
		}
		return sensorData;
	}
	
	//method to convert actuator data to JSON
	public String toJsonFromActuatorData(ActuatorData actuatorData)	{
		String jsonData = null;
		if (actuatorData != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(actuatorData);
		}
		return jsonData;
	}
	
	//method to convert JSOn to actuator data object
	public ActuatorData toActuatorDataFromJson(String jsonData)	{
		ActuatorData actuatorData = null;
		if (jsonData != null && jsonData.trim().length() > 0) {
			Gson gson = new Gson();
			actuatorData = gson.fromJson(jsonData, ActuatorData.class);
		}
		return actuatorData;
	}

}
