package restassuredtest;

import static com.jayway.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class RestAssuredTestNGTestV3 {

	@BeforeClass
	public void setURI() {
		RestAssured.baseURI = "http://10.0.1.86/snl/";
	}

	/**
	 * test to get the list of board
	 */
	@Test
	public void getListOfBoard_v3() {

		Response res = given()
				.parameters("username", "su", "password", "root_pass", "grant_type", "client_credentials", "client_id",
						"4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b", "client_secret",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.auth().preemptive()
				.basic("4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.when().post("oauth/token").then().contentType(ContentType.JSON).extract().response();

	//	System.out.println(res.asString());

		String token = res.jsonPath().getString("access_token");
	//	System.out.println(token + ".......................................");

		Response res1 = given().auth().oauth2(token).when().get("rest/v3/board.json");

		Assert.assertEquals(res1.statusCode(), 200);
		System.out.println("Test Case One in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());

	}

	/**
	 * test for the creation of new board
	 */
	@Test
	public void testForNewBoard_v3() {

		Response res = given()
				.parameters("username", "su", "password", "root_pass", "grant_type", "client_credentials", "client_id",
						"4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b", "client_secret",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.auth().preemptive()
				.basic("4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.when().post("oauth/token").then().contentType(ContentType.JSON).extract().response();
		String token = res.jsonPath().getString("access_token");
		//System.out.println(res.asString());
	//	System.out.println(token);
		Response res1 = given().auth().oauth2(token).when().get("rest/v3/board.json").then()
				.contentType(ContentType.JSON).extract().response();
		//System.out.println(res1.asString());
		Assert.assertEquals(res1.statusCode(), 200);
		System.out.println("Test Case three in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());
	}

	/**
	 * test to get the details of the newly created board by its ID
	 */
	@Test
	public void testForGetDetailsOfBoardById_v3() {
		Response res = given()
				.parameters("username", "su", "password", "root_pass", "grant_type", "client_credentials", "client_id",
						"4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b", "client_secret",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.auth().preemptive()
				.basic("4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.when().post("oauth/token").then().contentType(ContentType.JSON).extract().response();

		String token = res.jsonPath().getString("access_token");

		Response res1 = given().auth().oauth2(token).when().get("rest/v3/board/new.json").then()
				.contentType(ContentType.JSON).extract().response();

		int board_id_generated = res1.jsonPath().getInt("response.board.id");

		Response res2 = given().auth().oauth2(token).when().get("rest/v3/board/" + board_id_generated + ".json").then()
				.contentType(ContentType.JSON).extract().response();

		// System.out.println(res1.asString() + "................");
		// System.out.println(board_id_generated);
		Assert.assertEquals(res1.statusCode(), 200);
		System.out.println("Test Case three in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());
	}

	/**
	 * test to destroy the created board
	 */
	@Test
	public void testForDestroyBoard_v3() {

		Response res = given()
				.parameters("username", "su", "password", "root_pass", "grant_type", "client_credentials", "client_id",
						"4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b", "client_secret",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.auth().preemptive()
				.basic("4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.when().post("oauth/token").then().contentType(ContentType.JSON).extract().response();

		String token = res.jsonPath().getString("access_token");

		Response res1 = given().auth().oauth2(token).when().get("rest/v3/board/new.json").then()
				.contentType(ContentType.JSON).extract().response();

		int board_id_generated = res1.jsonPath().getInt("response.board.id");

		Response res2 = given().auth().oauth2(token).when().contentType(ContentType.JSON)
				.delete("rest/v3/board/" + board_id_generated + ".json");

		Assert.assertEquals(res2.statusCode(), 200);
		// System.out.println(res2.asString());
		System.out.println("Test Case four in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());
	}

	/**
	 * test for adding new player
	 */
	@Test
	public void testForAddingNewPlayer_v3() {
		Response res = given()
				.parameters("username", "su", "password", "root_pass", "grant_type", "client_credentials", "client_id",
						"4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b", "client_secret",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.auth().preemptive()
				.basic("4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.when().post("oauth/token").then().contentType(ContentType.JSON).extract().response();

		String token = res.jsonPath().getString("access_token");

		Response res1 = given().auth().oauth2(token).when().get("rest/v3/board/new.json").then()
				.contentType(ContentType.JSON).extract().response();

		int board_id_generated = res1.jsonPath().getInt("response.board.id");

		String new_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"XYqq\"}}";
		Response res2 = given().auth().oauth2(token).header("Content-Type", "application/json").body(new_player).when()
				.post("rest/v3/player.json");
		System.out.println(res2.asString());
		Assert.assertEquals(res2.statusCode(), 200);
		System.out.println("Test Case five in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());

	}

	/**
	 * test for updating the details of newly created player
	 */
	@Test
	public void updatingPlayer_v3() {

		Response res = given()
				.parameters("username", "su", "password", "root_pass", "grant_type", "client_credentials", "client_id",
						"4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b", "client_secret",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.auth().preemptive()
				.basic("4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.when().post("oauth/token").then().contentType(ContentType.JSON).extract().response();

		String token = res.jsonPath().getString("access_token");

		Response res1 = given().auth().oauth2(token).when().get("rest/v3/board/new.json").then()
				.contentType(ContentType.JSON).extract().response();

		int board_id_generated = res1.jsonPath().getInt("response.board.id");

		String new_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"old player\"}}";
		Response res2 = given().auth().oauth2(token).header("Content-Type", "application/json").body(new_player).when()
				.post("rest/v3/player.json");

		int player_id_generated = given().auth().oauth2(token).when()
				.get("rest/v3/board/" + board_id_generated + ".json").then().extract().jsonPath()
				.getInt("response.board.players[0].id");

		String updated_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"new player\"}}";
		Response res3 = given().auth().oauth2(token).header("Content-Type", "application/json").body(updated_player)
				.when().put("http://10.0.1.86/snl/rest/v3/player/" + player_id_generated + ".json");
		Assert.assertEquals(res2.statusCode(), 200);

		System.out.println("Test Case six in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());
	}

	/**
	 * test to check the moves of a player
	 */
	@Test
	public void movePlayer_v2() {
		Response res = given()
				.parameters("username", "su", "password", "root_pass", "grant_type", "client_credentials", "client_id",
						"4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b", "client_secret",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.auth().preemptive()
				.basic("4a235cbe65900b3ab3a49bb5908e71cd206c287c93cbe0ce4e906a9bd1af3f9b",
						"be609f956c05b90ef8b7eb6dd96fcfa65cbd61be31df59f4eb791541dc8aa683")
				.when().post("oauth/token").then().contentType(ContentType.JSON).extract().response();

		String token = res.jsonPath().getString("access_token");

		Response res1 = given().auth().oauth2(token).when().get("rest/v3/board/new.json").then()
				.contentType(ContentType.JSON).extract().response();

		int board_id_generated = res1.jsonPath().getInt("response.board.id");

		String new_player = " {\"board\":" + board_id_generated + ", \"player\":{\"name\": \"old player\"}}";
		Response res2 = given().auth().oauth2(token).header("Content-Type", "application/json").body(new_player).when()
				.post("rest/v3/player.json");

		int player_id_generated = given().auth().oauth2(token).when()
				.get("rest/v3/board/" + board_id_generated + ".json").then().extract().jsonPath()
				.getInt("response.board.players[0].id");
	//	System.out.println("board id " + board_id_generated);
		//System.out.println("player id " + player_id_generated);

		RestAssured.given().auth().oauth2(token).when()
				.get("/rest/v3/move/" + board_id_generated + ".json?player_id=" + player_id_generated).then()
				.statusCode(200);
		System.out.println("Test Case seven in " + getClass().getSimpleName() + " with Thread Id:- "
				+ Thread.currentThread().getId());
	}

}
