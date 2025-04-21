package com.ninza.hrm.api.genericUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class fileUtility {
	
	public String readDataFromPropertyFile(String Key) throws IOException {
		FileInputStream pfis = new FileInputStream("./ConfigEnvData/ConfigData.properties");
		Properties prop = new Properties();
		prop.load(pfis);
		String value = prop.getProperty(Key);
		return value;
	}

	
}
