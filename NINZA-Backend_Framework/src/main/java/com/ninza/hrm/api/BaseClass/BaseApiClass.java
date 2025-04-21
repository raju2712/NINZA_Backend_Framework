package com.ninza.hrm.api.BaseClass;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ninza.hrm.api.genericUtility.DataBaseUtility;
import com.ninza.hrm.api.genericUtility.fileUtility;

import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseApiClass {
	
	fileUtility fUtil = new fileUtility();
	DataBaseUtility dUtil = new DataBaseUtility();
	
	public static RequestSpecification ReqSpecObj;
	public static ResponseSpecification RespSpecObj;

	@BeforeSuite
	public void beforeSuite() throws IOException {

		String DB_URL = fUtil.readDataFromPropertyFile("dbUrl");
		String DB_USERNAME = fUtil.readDataFromPropertyFile("dbUserName");
		String DB_PASSWORD = fUtil.readDataFromPropertyFile("dbPasword");

		dUtil.getDbConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		System.out.println("===========DB Connected===========");
		
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setContentType(ContentType.JSON);
		//builder.setAuth(basic("username", "password"));
		//builder.addHeader("", "");
		builder.setBaseUri(fUtil.readDataFromPropertyFile("baseUri"));
		ReqSpecObj = builder.build();
		
		ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();
		respBuilder.expectContentType(ContentType.JSON);
		RespSpecObj = respBuilder.build();
	}
	
	@AfterSuite
	public void afterSuite() throws SQLException  {

		dUtil.closeDbConnection();
		System.out.println("===========DB Dis-Connected===========");
	}
}
