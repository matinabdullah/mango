package io.pelle.mango.demo.server;

import static com.jayway.restassured.RestAssured.given;

import java.text.MessageFormat;

import org.springframework.http.MediaType;

import com.jayway.restassured.specification.RequestSpecification;

/**
 * Created by pelle on 2/19/14.
 */
public class BaseRestTest {

	private String baseUrl = "http://localhost:9090/remote";

	public BaseRestTest() {
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getUrl(String url, String... arguments) {
		return MessageFormat.format(getUrl(url), arguments);
	}

	public String getUrl(String url) {
		return getBaseUrl() + "/" + url;
	}

	public static RequestSpecification givenJson() {
		return given().contentType(MediaType.APPLICATION_JSON_VALUE);
	}

}
