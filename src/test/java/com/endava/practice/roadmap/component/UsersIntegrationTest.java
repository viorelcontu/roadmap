package com.endava.practice.roadmap.component;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.model.entity.User;
import com.endava.practice.roadmap.util.JsonResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import javax.transaction.Transactional;

import static com.endava.practice.roadmap.util.JsonResource.JSON_USER_NEW;
import static com.endava.practice.roadmap.util.JsonResource.JSON_USER_NOT_FOUND;
import static com.endava.practice.roadmap.util.JsonResource.JSON_USER_UPDATE;
import static com.endava.practice.roadmap.util.TestUtils.USER2_ID;
import static com.endava.practice.roadmap.util.TestUtils.USER_ID;
import static com.endava.practice.roadmap.util.TestUtils.USER_ID_NON_EXISTING;
import static com.endava.practice.roadmap.util.TestUtils.USER_NAME;
import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public class UsersIntegrationTest {

    public static final String USERS_PATH = "/users";
    final private UserRepository userRepository;
    final private URI usersPath;
    final private UriComponentsBuilder usersIdPath;

    public UsersIntegrationTest(@Autowired UserRepository userRepository, @LocalServerPort int port) {
        this.userRepository = userRepository;
        usersPath = fromUriString("http://localhost").port(port).path(USERS_PATH).build().toUri();
        usersIdPath = fromUri(usersPath).path("/{id}");
    }

    @Test
    public void getAllUsersReturnsOK() {
        given()
            .accept(JSON)
        .expect()
            .statusCode(OK.value())
        .when()
            .get(usersPath);
    }

    @Test
    public void getOneUserReturnsOK() {
        given()
            .accept(JSON)
        .expect()
            .statusCode(OK.value())
            .body(
                "id", comparesEqualTo(USER_ID.intValue()),
                "name", equalTo(USER_NAME))
        .when()
            .get(usersIdPath.build(USER_ID));
    }

    @Test
    public void getNonExistingReturnsNotFound404 () {
        given()
            .accept(JSON)
        .expect()
            .statusCode(NOT_FOUND.value())
        .when()
            .get(usersIdPath.build(USER_ID_NON_EXISTING));
    }

    @Test
    public void postNewUserSuccessful201() {
        final Long newUserId = 45L;
        given()
            .contentType(JSON)
            .accept(JSON)
            .with()
            .body(JSON_USER_NEW.getFile())
        .expect()
            .statusCode(CREATED.value())
            .body("id", equalTo(newUserId.intValue()),
                "name", equalTo("Arnold"))
        .when()
            .post(usersPath);

        assertThat(userRepository.existsById(newUserId)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(value = JsonResource.class, names = {"JSON_USER_MALFORMED", "JSON_USER_MISSING_NAME", "JSON_USER_NULL_NAME"})
    public void postUserJsonReturns400BadRequest(JsonResource jsonResource) {

        given()
            .contentType(JSON)
            .accept(JSON)
            .body(jsonResource.getFile())
        .expect()
            .statusCode(BAD_REQUEST.value())
            .body("status", equalTo("400 BAD_REQUEST"))
        .when()
            .post(usersPath);
    }

    @Test
    public void putUserReturnsAccepted202 () {
        given()
            .contentType(JSON)
            .accept(JSON)
            .body(JSON_USER_UPDATE.getFile())
        .expect()
            .statusCode(ACCEPTED.value())
        .when()
            .put(usersIdPath.build(USER_ID));

        Optional<User> userOptional = userRepository.findById(USER_ID);
        assertThat(userOptional).contains(new User(42L, "Unknown President"));
    }

    @Test
    public void putMissingUserReturnsNotFound404 () {
        given()
            .contentType(JSON)
            .accept(JSON)
            .body(JSON_USER_NOT_FOUND.getFile())
        .expect()
            .statusCode(NOT_FOUND.value())
        .when()
            .put(usersIdPath.build(USER_ID_NON_EXISTING));
    }

    @Test
    public void putUserWithMismatchedIdsReturnsBadRequest400 () {
        given()
            .contentType(JSON)
            .accept(JSON)
            .body(JSON_USER_UPDATE.getFile())
        .expect()
            .statusCode(BAD_REQUEST.value())
        .when()
            .put(usersIdPath.build(USER2_ID));
    }

    @Test
    public void deleteUserReturnsNoContent204 () {
        expect()
            .statusCode(NO_CONTENT.value())
        .when()
            .delete(usersIdPath.build(USER_ID));

        assertThat(userRepository.existsById(USER_ID)).isFalse();
    }

    @Test
    public void deleteNonExistingUserReturnsNotFound404 () {
        expect()
            .statusCode(NOT_FOUND.value())
        .when()
            .delete(usersIdPath.build(USER_ID_NON_EXISTING));
    }

    @Test
    public void countReturnsEntityCountFromDB() {
        final long actualUserCount = userRepository.count();

        expect()
            .body(equalTo(String.valueOf(actualUserCount)))
        .when()
            .get(fromUri(usersPath).path("/count").toUriString());
    }
}
