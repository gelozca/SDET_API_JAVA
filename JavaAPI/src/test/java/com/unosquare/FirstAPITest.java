package com.unosquare;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.*;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;

public class FirstAPITest {

	@Test
	public void f_Gherkin() {
		given().when().get("https://reqres.in/api/users/2").then().assertThat().statusCode(200)
				.contentType(ContentType.JSON);

		Reporter.log("Sucess 200 validation");

	}

	@Test
	public void f_Gherkin_2() {

		given().when().get("https://reqres.in/api/unknown/2").then().assertThat().statusCode(200)
				.contentType(ContentType.JSON)
				.and().assertThat().body("data.id", equalTo(2))
				.and().assertThat().body("data.name", equalTo("fuchsia rose"));

		Reporter.log("Sucess 200 validation");
	}

	@Test
	public void f_HttpObjects() {
		
		RestAssured.baseURI = "https://reqres.in/api/";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/unknown/2");

		int statusCode = response.getStatusCode();

		response.then().statusCode(200).body("data.id", equalTo(2));
		response.then().statusCode(200).body("data.name", equalTo("fuchsia rose"));
		Reporter.log("Sucess 200 validation");
	}

	@BeforeMethod
	public void beforeMethod() {
	}

}
