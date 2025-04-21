package com.ninza.hrm.api.employeetest;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.sql.SQLException;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ninza.hrm.api.BaseClass.BaseApiClass;
import com.ninza.hrm.api.endPoints.iEndPoints;
import com.ninza.hrm.api.genericUtility.DataBaseUtility;
import com.ninza.hrm.api.genericUtility.JsonUtility;
import com.ninza.hrm.api.genericUtility.fileUtility;
import com.ninza.hrm.api.genericUtility.javaUtility;

import NINZA.api.pojoclass.EmployeePojo;
import NINZA.api.pojoclass.ProjectPojo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class createEmployee extends BaseApiClass{

	javaUtility jUtil = new javaUtility();
	fileUtility fUtil = new fileUtility();
	JsonUtility jsUtil = new JsonUtility();
	DataBaseUtility dUtil = new DataBaseUtility();

	@Test
	public void addEmployee() throws SQLException, IOException {

		String expData = "Employee Added Successfully";
		
		String DB_URL = fUtil.readDataFromPropertyFile("dbUrl");
		String DB_USERNAME = fUtil.readDataFromPropertyFile("dbUserName");
		String DB_PASSWORD = fUtil.readDataFromPropertyFile("dbPasword");

		String empName = "Tek_" + jUtil.togetRandomNumber();

		EmployeePojo ppObj = new EmployeePojo("QA", "27/01/2000", "raju123@gmail.com", empName, 3, "9632587412", "RAPO", "ROLE_EMPLOYEE", empName);
		
		// API Layer Testing
		Response resp = given().spec(ReqSpecObj).body(ppObj).when().post(iEndPoints.ADD_EMPLOYEE);
		
		resp.then().assertThat().statusCode(201).time(Matchers.lessThan(3000l)).spec(RespSpecObj)
		.log().all();
		
		String actSucMsg = jsUtil.getDataFromJsonPathUsingKey(resp, "msg");
		Assert.assertEquals(expData, actSucMsg);
		
		// DB Layer Testing
		dUtil.getDbConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		boolean flag = dUtil.executeQueryVerifyAndGetData("select * from employee",empName , 11);
		dUtil.closeDbConnection();
	}
}
