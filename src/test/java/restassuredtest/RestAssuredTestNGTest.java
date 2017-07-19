package restassuredtest;

import static com.jayway.restassured.RestAssured.given;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.*;

/**
 * 
 * @author mohdkamran
 *
 */
public class RestAssuredTestNGTest {

	/**
	 * Set the base url
	 */
	@BeforeClass
	public void setURI() {
		RestAssured.baseURI = "http://10.0.1.86/snl/";
	}

	/**
	 * checking whether the given link exist or not
	 */
	@Test
	public void checkLink() {

		RestAssured.given().when().get("http://10.0.1.86/snl/").then().statusCode(200);
		System.out.println("Test Case One with Thread Id:- " + Thread.currentThread().getId());

	}

	/**
	 * test to get the list of existing board
	 */
	@Test
	public void getListOfBoard() {

		Response res = given().when().get("rest/v1/board.json").then().contentType(ContentType.JSON).extract()
				.response();
		Assert.assertEquals(res.statusCode(), 200);

		System.out.println("Test Case two with Thread Id:- " + Thread.currentThread().getId());
	}

	/**
	 * test to check new board is created or not
	 */

	@Test
	public void testForNewBoard() throws MalformedURLException {
		Response res = given().when().get("rest/v1/board/new.json").then().extract().response();

		int board_id_generated = res.jsonPath().getInt("response.board.id");
		Assert.assertEquals(res.statusCode(), 200);

	//	System.out.println(res.asString() + "................");
		//System.out.println("id" + board_id_generated);
		System.out.println("Test Case three with Thread Id:- " + Thread.currentThread().getId());
	}

	/**
	 * test to get the details of the board by id
	 */
	@Test
	public void testForGetDetailsOfBoardById() {
		Response res = given().when().get("rest/v1/board/new.json").then().extract().response();

		int board_id_generated = res.jsonPath().getInt("response.board.id");

		Response res1 = given().when().get("rest/v1/board/" + board_id_generated + ".json").then()
				.contentType(ContentType.JSON).extract().response();

		//System.out.println(res1.asString() + "................");
	//	System.out.println(board_id_generated);
		Assert.assertEquals(res1.statusCode(), 200);

		System.out.println("Test Case four with Thread Id:- " + Thread.currentThread().getId());
	}

	/**
	 * test to destroy board
	 */
	@Test
	public void testForDestroyBoard() {
		Response res = given().when().get("rest/v1/board/new.json").then().extract().response();

		int board_id_generated = res.jsonPath().getInt("response.board.id");

		Response res1 = given().when().contentType(ContentType.JSON)
				.delete("rest/v1/board/" + board_id_generated + ".json");

		Assert.assertEquals(res1.statusCode(), 200);

		System.out.println("Test Case five with Thread Id:- " + Thread.currentThread().getId());
	}

	/**
	 * test for adding a new player
	 */
	@Test
	public void testForAddingNewPlayer() {

		Response res = given().when().get("rest/v1/board/new.json").then().extract().response();

		int board_id_generated = res.jsonPath().getInt("response.board.id");
		String new_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"XYqq\"}}";
		Response res1 = given().header("Content-Type", "application/json").body(new_player).when()
				.post("rest/v1/player.json");
	//	System.out.println(res1.asString());

		System.out.println("Test Case six with Thread Id:- " + Thread.currentThread().getId());
	}

	@Test
	public void updatingPlayer() {

		Response res = given().when().get("rest/v1/board/new.json").then().extract().response();

		int board_id_generated = res.jsonPath().getInt("response.board.id");

		String new_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"old player\"}}";
		Response res1 = given().header("Content-Type", "application/json").body(new_player).when()
				.post("rest/v1/player.json");

		int player_id_generated = given().when().get("rest/v1/board/" + board_id_generated + ".json").then().extract()
				.jsonPath().getInt("response.board.players[0].id");

		String updated_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"new player\"}}";
		Response res2 = given().header("Content-Type", "application/json").body(updated_player).when()
				.put("http://10.0.1.86/snl/rest/v1/player/" + player_id_generated + ".json");
		Assert.assertEquals(res2.statusCode(), 200);

		System.out.println("Test Case seven with Thread Id:- " + Thread.currentThread().getId());
	}

	@Test
	public void movePlayer() {
		Response res = given().when().get("rest/v1/board/new.json").then().extract().response();

		int board_id_generated = res.jsonPath().getInt("response.board.id");

		String new_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"old player\"}}";
		Response res1 = given().header("Content-Type", "application/json").body(new_player).when()
				.post("rest/v1/player.json");

		int player_id_generated = given().when().get("rest/v1/board/" + board_id_generated + ".json").then().extract()
				.jsonPath().getInt("response.board.players[0].id");
		//System.out.println("board id " + board_id_generated);
	//	System.out.println("player id " + player_id_generated);

		RestAssured.given().when().get("/rest/v1/move/" + board_id_generated + ".json?player_id=" + player_id_generated)
				.then().statusCode(200);
		System.out.println("Test Case eight with Thread Id:- " + Thread.currentThread().getId());
	}

}