package neu.s.dey.connecteddevices.common;

//  Import Libraries
import java.io.BufferedReader;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

//  Define ConnectedDevicesConfig.props File Location
	public static File default_configFile = new File ("C:\\User\\soman\\OneDrive\\Documents\\git\\workspace\\iot-gateway\\config\\ConnectedDevicesConfig.props");

//  Set default config file location in the configFile variable
	public static File fileName = default_configFile;

//  Boolean variable which returns whether the config file has been loaded	
	public boolean isLoaded = false;

//  Boolean variable which returns whether any value is set for a given key 	
	public boolean hasProperty = false;
	
//  Boolean variable which returns whether any section exists in the file 	
	public boolean hasSection = false;
	
//	Create a Hashmap named 	propmap which contains all the keys and their respective value for a section
    public HashMap<String, Comparable> propmap = new HashMap<String, Comparable>();
    
//	Create ArrayList which contains all the sections in the file    
    public List<String> sections = new ArrayList<String>();

//	Default Constructor
	public ConfigUtil() {
	}

//  Define ConfigUtil constructor which loads the configuration file	
	public ConfigUtil(File fileName) {
		this.fileName = fileName;
		loadConfig(fileName);
	}

//	Read the file line by line and set the flag isLoaded accordingly
	public void loadConfig(File fileName) {
		String st = null; 
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			st = br.readLine();
			if (!st.isEmpty() && st.substring(0, 1).equals("[")) sections.add(st);
			while ((st = br.readLine()) != null) {
				if (!st.isEmpty() && st.substring(0, 1).equals("[")) sections.add(st);
				if (st.equals("[smtp.cloud]")) {
					while (!st.isEmpty())  {
							st = br.readLine();
						if (st.isEmpty()) {
							break;
						}								
							String[] tokens = st.split(" = ");	
							String key = tokens[0];
							
							if (key.equals("port"))	{
								int value = Integer.parseInt(tokens[1]);
								propmap.put(key, value);

							}
							else if (key.equals("enableAuth") || key.equals("enableCrypt")) {
								boolean value = Boolean.parseBoolean(tokens[1]);	
								propmap.put(key, value);
							}
							else {
								String value = tokens[1];
								propmap.put(key, value);
							}
						
					}
				}
			}
			isLoaded = true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	 }

//	Create ConfigUtil instance
	public static ConfigUtil getInstance() {
		ConfigUtil config = new ConfigUtil(fileName);
		return config;
	}

//	Retrieve value with their respective type for given section and given key 	
	public <T> Comparable getProperty(String section_name, String key) {
			return  propmap.get(key);	
	}

//	Retrieve value with their respective type for given section and given key 
	public boolean hasProperty(String section_name, String key) {
		if (ConfigUtil.getInstance().getProperty(section_name, key).equals("Not Set"))
			hasProperty = false;
		else hasProperty = true;
		return hasProperty;
	}

//	Check whether a given section exists in the file or not
	public boolean hasSection(String section_name) {
		if (sections.contains(section_name))
			hasSection = true;
		return hasSection;
	}

}
