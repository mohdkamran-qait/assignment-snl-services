package restassuredtest;

import static com.jayway.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class TestForTestNGParametrization {

	@BeforeClass
	public void setURI() {
		RestAssured.baseURI = "http://10.0.1.86/snl/";
	}

	@Parameters({ "username", "password" })
	@Test
	public void getListOfBoard_v2_By_UsingParameters(String username, String password) {

		System.out.println(username);
		System.out.println(password);
		Response res = given().authentication().basic(username, password).when().get("rest/v2/board.json");
		Assert.assertEquals(res.statusCode(), 200);
		System.out.println(res.asString() + "........................");

	}
}
