package com.ninza.hrm.api.projecttest;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.ninza.hrm.api.BaseClass.BaseApiClass;
import com.ninza.hrm.api.endPoints.iEndPoints;
import com.ninza.hrm.api.genericUtility.DataBaseUtility;
import com.ninza.hrm.api.genericUtility.JsonUtility;
import com.ninza.hrm.api.genericUtility.fileUtility;
import com.ninza.hrm.api.genericUtility.javaUtility;

import NINZA.api.pojoclass.ProjectPojo;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;

public class createProject extends BaseApiClass {

	javaUtility jUtil = new javaUtility();
	fileUtility fUtil = new fileUtility();
	JsonUtility jsUtil = new JsonUtility();
	DataBaseUtility dUtil = new DataBaseUtility();

	@Test
	public void addSingleProject() throws SQLException, IOException, InterruptedException {

		String DB_URL = fUtil.readDataFromPropertyFile("dbUrl");
		String DB_USERNAME = fUtil.readDataFromPropertyFile("dbUserName");
		String DB_PASSWORD = fUtil.readDataFromPropertyFile("dbPasword");
		String projectName = "ABC_" + jUtil.togetRandomNumber();
		String createdBy = "Raju";

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));

		driver.get("http://49.249.28.218:8091/");
		driver.findElement(By.id("username")).sendKeys("rmgyantra");
		driver.findElement(By.id("inputPassword")).sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[contains(text(),'Sign in')]")).click();
		Thread.sleep(3000); // Manually handle popup
		driver.findElement(By.linkText("Projects")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Create Project')]")).click();
		driver.findElement(By.name("projectName")).sendKeys(projectName);
		driver.findElement(By.name("createdBy")).sendKeys(createdBy);
		WebElement drop = driver.findElement(By.xpath("//label[text()='Project Status* ']/following-sibling::select"));
		Select sel = new Select(drop);
		sel.selectByValue("Created");
		driver.findElement(By.xpath("//input[@value='Add Project']")).click();

		WebElement searchByDD = driver
				.findElement(By.xpath("//select[@class='form-control' and contains(.,'Search')]"));
		Select s = new Select(searchByDD);
		s.selectByValue("createdBy");
		// capture Project ID
		driver.findElement(By.xpath("//div[@class='col-sm-6']/input[@type='text']")).sendKeys(createdBy);

		String FpId = driver.findElement(By.xpath("//td[text()='" + createdBy + "']/preceding-sibling::td[contains(.,'NH')]"))
				.getText();
		System.out.println("Project Id is: "+FpId);
		// Delete
		driver.findElement(By.xpath("//td[text()='" + createdBy + "']/parent::tr/child::td/a[@class='delete']"))
				.click();
		driver.findElement(By.xpath("//input[@value='Delete']")).click();
		
		driver.close();
				
	//Backend======================================================================================================
		
        ProjectPojo ppObj = new ProjectPojo(projectName, "Raju", "Created", 0);
		// Create Project
		Response resp = given().spec(ReqSpecObj).body(ppObj).when().post(iEndPoints.ADD_PROJECT);
		resp.then().assertThat().statusCode(201).time(Matchers.lessThan(3000l)).spec(RespSpecObj).log().all();
		String BpId = resp.jsonPath().get("projectId");
		// View Project
		Response resp1 = given().spec(ReqSpecObj).body(ppObj).when().get("/project/" + BpId + "");
		resp1.then().assertThat().statusCode(200).time(Matchers.lessThan(3000l)).spec(RespSpecObj).log().all();
		// Update Project
		ProjectPojo ppObj1 = new ProjectPojo(projectName, "RajuR", "Completed", 0);
		Response resp2 = given().spec(ReqSpecObj).body(ppObj1).when().put("/project/" + BpId + "");
		resp2.then().assertThat().statusCode(200).time(Matchers.lessThan(3000l)).spec(RespSpecObj).log().all();

		String actPjMsg = jsUtil.getDataFromJsonPathUsingKey(resp, "projectName");
		Assert.assertEquals(projectName, actPjMsg);

		// DB Layer Testing
		dUtil.getDbConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		dUtil.executeQueryVerifyAndGetData("select * from project", projectName, 4);
		dUtil.closeDbConnection();

		// Delete Project
		Response resp3 = given().when().spec(ReqSpecObj).when().delete("/project/" + BpId + "");
		resp3.then().assertThat().statusCode(204).log().all();
	}
	
}