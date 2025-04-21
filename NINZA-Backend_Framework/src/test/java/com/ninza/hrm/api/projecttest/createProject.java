package com.ninza.hrm.api.projecttest;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.ninza.hrm.api.BaseClass.BaseApiClass;
import com.ninza.hrm.api.endPoints.iEndPoints;
import com.ninza.hrm.api.genericUtility.DataBaseUtility;
import com.ninza.hrm.api.genericUtility.JsonUtility;
import com.ninza.hrm.api.genericUtility.fileUtility;
import com.ninza.hrm.api.genericUtility.javaUtility;

import NINZA.api.pojoclass.ProjectPojo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.sql.SQLException;

public class createProject extends BaseApiClass{
	
	javaUtility jUtil = new javaUtility();
	fileUtility fUtil = new fileUtility();
	JsonUtility jsUtil = new JsonUtility();
	DataBaseUtility dUtil = new DataBaseUtility();

	@Test
	public void addSingleProjectWithCreated() throws SQLException, IOException {

		String expData = "Successfully Added";
		
		String DB_URL = fUtil.readDataFromPropertyFile("dbUrl");
		String DB_USERNAME = fUtil.readDataFromPropertyFile("dbUserName");
		String DB_PASSWORD = fUtil.readDataFromPropertyFile("dbPasword");

		String projectName = "ABC_" + jUtil.togetRandomNumber();

		ProjectPojo ppObj = new ProjectPojo(projectName, "Raju", "Created", 0);
		
		// API Layer Testing
		Response resp = given().spec(ReqSpecObj).body(ppObj).when().post(iEndPoints.ADD_PROJECT);
		
		resp.then().assertThat().statusCode(201).time(Matchers.lessThan(3000l)).spec(RespSpecObj)
		.log().all();
		
		String actSucMsg = jsUtil.getDataFromJsonPathUsingKey(resp, "msg");
		Assert.assertEquals(expData, actSucMsg);
		
		// DB Layer Testing
		dUtil.getDbConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		boolean flag = dUtil.executeQueryVerifyAndGetData("select * from project",projectName , 4);
		dUtil.closeDbConnection();
	}
}