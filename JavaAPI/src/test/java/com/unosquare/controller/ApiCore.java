package com.unosquare.controller;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiCore {

	private Response response;
	private RequestSpecification httpRequest;

	public Response RESTFulService(String baseURI, String basePath, String restService, String filePath) {
		RestAssured.baseURI = baseURI;
		RestAssured.basePath = basePath;
		httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");

		if (!filePath.isEmpty()) {
			httpRequest.body(ConvertJsonToObject(filePath).toJSONString());
		}
		
		switch (restService) {

		case "post":
			this.response = httpRequest.post();
			break;

		case "put":
			this.response = httpRequest.put();
			break;

		case "get":
			this.response = httpRequest.get();
			break;

		default:
			this.response = null;
			break;
		}

		return this.response;
	}

	private JSONObject ConvertJsonToObject(String filePath) {

		try (FileReader reader = new FileReader(filePath)) {

			JSONParser json = new JSONParser();
			return (JSONObject) json.parse(reader);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
