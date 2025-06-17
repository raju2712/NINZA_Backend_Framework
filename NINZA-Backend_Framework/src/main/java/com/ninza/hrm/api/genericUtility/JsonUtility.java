package com.ninza.hrm.api.genericUtility;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;

import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;

public class JsonUtility {

	/**
	 * @param resp
	 * @param jsonXpath
	 * @return
	 */
	public String getDataFromJsonPath(Response resp,String jsonXpath) {
		List<Object> list = JsonPath.read(resp.asString(),jsonXpath);
		//resp.jsonPath().get("msg");
		return list.get(0).toString();
	}
	
	/**
	 * 
	 * @param resp
	 * @param value
	 * @return
	 */
	public String getDataFromJsonPathUsingKey(Response resp,String value) {
		String data = resp.jsonPath().get(value);
		return data;
	}
	
	/**
	 * @param resp
	 * @param jsonXpath
	 * @param expData
	 * @return
	 */
	public boolean verifyDataOnJsonPath(Response resp,String expData,String jsonXpath ) {
		List<String> list= JsonPath.read(resp.asString(),jsonXpath);
		boolean flag = false;
		for(String str : list) {
			if(str.equals(expData)) {
				System.out.println(expData + "is available ========== PASS");
				flag = true;
			}
			else {
				System.out.println(expData + "is not available =========== FAIL");
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getAcessToken() throws IOException {
		fileUtility fUtil = new fileUtility();
		Response resp = given()
				.formParam("client_id", fUtil.readDataFromPropertyFile("clientId"))
				.formParam("client_secret", fUtil.readDataFromPropertyFile("clientSecret"))
				.formParam("grant_type", fUtil.readDataFromPropertyFile("grantType"))
				.when().post(fUtil.readDataFromPropertyFile("accessTokenUri"));
		
				resp.then().assertThat().statusCode(200).log().all();
		//Capture Project Name from Response
				String token = resp.jsonPath().get("access_token");
				return token;
	}
	
}
