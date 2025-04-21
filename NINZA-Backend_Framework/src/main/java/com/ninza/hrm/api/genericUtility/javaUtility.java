package com.ninza.hrm.api.genericUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class javaUtility {
	
	public int togetRandomNumber() {
		Random random = new Random();
		int randomNum = random.nextInt(1000);
		return randomNum;
	}
	
	//SimpleDateFormat sdf;
	/**
	 * This method is used to get the current date
	 * 
	 * @return
	 */
	public String togetSystemDateYYYYMMDD() {
		Date dateObj = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String date = sdf.format(dateObj);
		return date;
	}

	/**
	 * This method is used to generate date according to our input
	 * 
	 * @param days
	 * @return
	 */
	public String togetRequiredDate(int days) {
		Date dateObj = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String date = sdf.format(dateObj);
		Calendar cal = sdf.getCalendar();
		         cal.add(Calendar.DAY_OF_MONTH, -30);
		String reqDate = sdf.format(cal.getTime());
		return reqDate;
	}
	
	/**
	 * This method is used to get random name of 9 characters
	 * @return
	 */
	public StringBuilder getRandomName() {
		int  n = 9;
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqurstuvwxyz";
		StringBuilder sb = new StringBuilder(n);
		for(int i=0;i<n;i++) {
			int index = (int)(AlphaNumericString.length()* Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}return sb;
	}
	
	/**
	 * This method is used to get random number of 10 digits
	 * @return
	 */
	public StringBuilder getRandomMobileNumber() {
		int  n = 10;
		String AlphaNumericString = "0123456789";
		StringBuilder sb = new StringBuilder(n);
		for(int i=0;i<n;i++) {
			int index = (int)(AlphaNumericString.length()* Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}return sb;
	}

	
}
