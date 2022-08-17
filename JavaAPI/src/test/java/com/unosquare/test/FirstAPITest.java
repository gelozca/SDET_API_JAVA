package com.unosquare.test;

import org.testng.annotations.Test;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.*;
import org.json.simple.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import com.unosquare.controller.ApiCore;

public class FirstAPITest {

	ApiCore apiCore;

	@Test
	public void f_Gherkin() {
		given().when().get("https://reqres.in/api/users/2").then().assertThat().statusCode(200)
				.contentType(ContentType.JSON);

		Reporter.log("Sucess 200 validation");

	}

	@Test
	public void f_Gherkin_2() {

		given().when().get("https://reqres.in/api/unknown/2").then().assertThat().statusCode(200)
				.contentType(ContentType.JSON).and().assertThat().body("data.id", equalTo(2)).and().assertThat()
				.body("data.name", equalTo("fuchsia rose"));

		Reporter.log("Sucess 200 validation");
	}

	@Test
	public void f_HttpObjects() {

		RestAssured.baseURI = "https://reqres.in/api";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/users/2");

		Reporter.log(response.asPrettyString());
		Reporter.log(Integer.toString(response.getStatusCode()));
	}

	@Test
	public void f_FirstPost() {

		JSONObject requestParams = new JSONObject();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();

		requestParams.put("name", "JohnAPI");
		requestParams.put("job", "QA");

		RestAssured.baseURI = "https://reqres.in/api";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");
		httpRequest.body(requestParams.toString());
		Response response = httpRequest.post("/users");

		response.then().statusCode(201).body("createdAt", containsString(formatter.format(now).toString()));
		Reporter.log(response.body().toString());
		Reporter.log(formatter.format(now).toString());
	}

	@Test
	public void f_GetSingleUser() {
		Response resp = apiCore.RESTFulService("https://reqres.in/api", "/users/2", "get", "");

		Reporter.log(resp.asPrettyString());
		Reporter.log(Integer.toString(resp.getStatusCode()));

		Assert.assertEquals(resp.getStatusCode(), 200);
	}

	@Test
	public void f_GetSingleUserNotFound() {
		Response resp = apiCore.RESTFulService("https://reqres.in/api", "/users/23", "get", "");

		Reporter.log(resp.asPrettyString());
		Reporter.log(Integer.toString(resp.getStatusCode()));

		Assert.assertEquals(resp.getStatusCode(), 404);
	}

	@Test
	public void f_GetListResource() {
		Response resp = apiCore.RESTFulService("https://reqres.in/api", "/unknown", "get", "");

		Reporter.log(resp.asPrettyString());
		Reporter.log(Integer.toString(resp.getStatusCode()));

		Assert.assertEquals(resp.getStatusCode(), 200);
	}

	@Test
	public void f_GetSingleResource() {
		Response resp = apiCore.RESTFulService("https://reqres.in/api", "/unknown/2", "get", "");

		Reporter.log(resp.asPrettyString());
		Reporter.log(Integer.toString(resp.getStatusCode()));

		Assert.assertEquals(resp.getStatusCode(), 200);
	}

	@Test
	public void f_PostLogin() {

		Response resp = apiCore.RESTFulService("https://reqres.in/api", "/login", "post", "./Json/Register.json");

		Reporter.log(resp.asPrettyString());
		Reporter.log(Integer.toString(resp.getStatusCode()));

		Assert.assertEquals(resp.getStatusCode(), 200);
	}

	@Test
	public void f_PostRegister() {

		Response resp = apiCore.RESTFulService("https://reqres.in/api", "/register", "post", "./Json/Register.json");

		Reporter.log(resp.asPrettyString());
		Reporter.log(Integer.toString(resp.getStatusCode()));

		Assert.assertEquals(resp.getStatusCode(), 200);

	}

	@Test
	public void f_PostRegisterUnsuccessfull() {

		Response resp = apiCore.RESTFulService("https://reqres.in/api", "/register", "post",
				"./Json/RegisterUnsuccessful.json");

		Reporter.log(resp.asPrettyString());
		Reporter.log(Integer.toString(resp.getStatusCode()));

		Assert.assertEquals(resp.getStatusCode(), 400);

	}

	@Test
	public void f_PostLoginUnsuccessfull() {

		Response resp = apiCore.RESTFulService("https://reqres.in/api", "/login", "post",
				"./Json/RegisterUnsuccessful.json");

		Reporter.log(resp.asPrettyString());
		Reporter.log(Integer.toString(resp.getStatusCode()));

		Assert.assertEquals(resp.getStatusCode(), 400);

	}

	@Test
	public void f_PutUpdate() {

		Response resp = apiCore.RESTFulService("https://reqres.in/api", "/users/2", "put", "./Json/Update.json");

		Reporter.log(resp.asPrettyString());
		Reporter.log(Integer.toString(resp.getStatusCode()));

		Assert.assertEquals(resp.getStatusCode(), 200);

	}

	@BeforeMethod
	public void beforeMethod() {

		apiCore = new ApiCore();
	}

}
