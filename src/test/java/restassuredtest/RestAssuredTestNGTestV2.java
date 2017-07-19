package restassuredtest;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.authentication;
import static com.jayway.restassured.RestAssured.basic;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class RestAssuredTestNGTestV2 {

	@BeforeClass
	public void setURI() {
		RestAssured.baseURI = "http://10.0.1.86/snl/";
	}

	/**
	 * test for getting the board
	 */
	@Test
	public void getListOfBoard_v2() {

		Response res = given().authentication().basic("su", "root_pass").when().get("rest/v2/board.json");
		Assert.assertEquals(res.statusCode(), 200);
		// System.out.println(res.asString() + "........................");
		System.out.println("Test Case One in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());

	}

	/**
	 * test for the creation of new board
	 */
	@Test
	public void testForNewBoard_v2() {

		Response res = given().authentication().basic("su", "root_pass").when().get("rest/v2/board/new.json");
		Assert.assertEquals(res.statusCode(), 200);
		System.out.println("Test Case two in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());
	}

	/**
	 * test to check the newly created board by its ID
	 */
	@Test
	public void testForGetDetailsOfBoardById_v2() {
		Response res = given().authentication().basic("su", "root_pass").when().get("rest/v2/board/new.json").then()
				.extract().response();

		int board_id_generated = res.jsonPath().getInt("response.board.id");

		Response res1 = given().authentication().basic("su", "root_pass").when()
				.get("rest/v2/board/" + board_id_generated + ".json").then().contentType(ContentType.JSON).extract()
				.response();

		//System.out.println(res1.asString() + "................");
	//	System.out.println(board_id_generated);
		Assert.assertEquals(res1.statusCode(), 200);
		System.out.println("Test Case three in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());
	}

	/**
	 * test for destroying the created board
	 */
	@Test
	public void testForDestroyBoard_v2() {
		Response res = given().authentication().basic("su", "root_pass").when().get("rest/v2/board/new.json").then()
				.extract().response();

		int board_id_generated = res.jsonPath().getInt("response.board.id");

		Response res1 = given().authentication().basic("su", "root_pass").when().contentType(ContentType.JSON)
				.delete("rest/v2/board/" + board_id_generated + ".json");

		Assert.assertEquals(res1.statusCode(), 200);
		System.out.println("Test Case four in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());
	}

	/**
	 * test for adding the new player
	 */
	@Test
	public void testForAddingNewPlayer_v2() {

		Response res = given().authentication().basic("su", "root_pass").when().get("rest/v2/board/new.json").then()
				.extract().response();

		int board_id_generated = res.jsonPath().getInt("response.board.id");
		String new_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"XYqq\"}}";
		Response res1 = given().authentication().basic("su", "root_pass").header("Content-Type", "application/json")
				.body(new_player).when().post("rest/v2/player.json");
	//	System.out.println(res1.asString());
		Assert.assertEquals(res1.statusCode(), 200);
		System.out.println("Test Case five in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());
	}

	/**
	 * test to update the details of the newly created player
	 */
	@Test
	public void updatingPlayer_v2() {

		Response res = given().authentication().basic("su", "root_pass").when().get("rest/v2/board/new.json").then()
				.extract().response();

		int board_id_generated = res.jsonPath().getInt("response.board.id");

		String new_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"old player\"}}";
		Response res1 = given().authentication().basic("su", "root_pass").header("Content-Type", "application/json")
				.body(new_player).when().post("rest/v2/player.json");

		int player_id_generated = given().authentication().basic("su", "root_pass").when()
				.get("rest/v2/board/" + board_id_generated + ".json").then().extract().jsonPath()
				.getInt("response.board.players[0].id");

		String updated_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"new player\"}}";
		Response res2 = given().authentication().basic("su", "root_pass").header("Content-Type", "application/json")
				.body(updated_player).when()
				.put("http://10.0.1.86/snl/rest/v2/player/" + player_id_generated + ".json");
		Assert.assertEquals(res2.statusCode(), 200);
		System.out.println("Test Case six in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());
	}

	/**
	 * test to check the move of a player
	 */
	@Test
	public void movePlayer_v2() {
		Response res = given().authentication().basic("su", "root_pass").when().get("rest/v2/board/new.json").then()
				.extract().response();

		int board_id_generated = res.jsonPath().getInt("response.board.id");

		String new_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"old player\"}}";
		Response res1 = given().authentication().basic("su", "root_pass").header("Content-Type", "application/json")
				.body(new_player).when().post("rest/v2/player.json");

		int player_id_generated = given().authentication().basic("su", "root_pass").when()
				.get("rest/v2/board/" + board_id_generated + ".json").then().extract().jsonPath()
				.getInt("response.board.players[0].id");
		//System.out.println("board id " + board_id_generated);
		//System.out.println("player id " + player_id_generated);

		RestAssured.given().authentication().basic("su", "root_pass").when()
				.get("/rest/v2/move/" + board_id_generated + ".json?player_id=" + player_id_generated).then()
				.statusCode(200);

		System.out.println("Test Case seven in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());
	}

}
