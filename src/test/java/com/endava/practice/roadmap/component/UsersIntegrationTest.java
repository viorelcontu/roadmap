package com.endava.practice.roadmap.component;

import com.endava.practice.roadmap.persistence.dao.UserRepository;
import com.endava.practice.roadmap.persistence.entity.User;
import com.endava.practice.roadmap.util.JsonResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

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
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

//TODO find an alternative for HttpStatus.CODE.value()
public class UsersIntegrationTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllUsersReturnsOK() {
        final String uri = getUsersURL();

        given()
            .accept(JSON)
        .expect()
            .statusCode(OK.value())
        .when()
            .get(uri);
    }

    @Test
    public void getOneUserReturnsOK() {
        final String uri = getUsersURL() + USER_ID;

        given()
            .accept(JSON)
        .expect()
            .statusCode(OK.value())
            .body(
                "id", comparesEqualTo(USER_ID.intValue()),
                "name", equalTo(USER_NAME))
        .when()
            .get(uri);
    }

    @Test
    public void getNonExistingReturnsNotFound404 () {
        final String uri = getUsersURL() + USER_ID_NON_EXISTING;

        given()
            .accept(JSON)
        .expect()
            .statusCode(NOT_FOUND.value())
        .when()
            .get(uri);
    }

    @Test
    public void postNewUserSuccessful201() {
        final String uri = getUsersURL();

        given()
            .contentType(JSON)
            .accept(JSON)
            .with()
            .body(JSON_USER_NEW.getFile())
        .expect()
            .statusCode(CREATED.value())
            .body("id", equalTo(1),
                "name", equalTo("Arnold"))
        .when()
            .post(uri);

        assertThat(userRepository.existsById(1L)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(value = JsonResource.class, names = {"JSON_USER_MALFORMED", "JSON_USER_MISSING_NAME", "JSON_USER_NULL_NAME"})
    public void postUserJsonReturns400BadRequest(JsonResource jsonResource) {

        final String uri = getUsersURL();
        given()
            .contentType(JSON)
            .accept(JSON)
            .body(jsonResource.getFile())
        .expect()
            .statusCode(BAD_REQUEST.value())
            .body("status", equalTo("400 BAD_REQUEST"))
        .when()
            .post(uri);
    }

    @Test
    public void putUserReturnsAccepted202 () {
        final String uri = getUsersURL() + USER_ID;

        given()
            .contentType(JSON)
            .accept(JSON)
            .body(JSON_USER_UPDATE.getFile())
        .expect()
            .statusCode(ACCEPTED.value())
        .when()
            .put(uri);

        Optional<User> userOptional = userRepository.findById(USER_ID);
        assertThat(userOptional).contains(new User(42L, "Unknown President"));
    }

    @Test
    public void putMissingUserReturnsNotFound404 () {
        final String uri = getUsersURL() + USER_ID_NON_EXISTING;

        given()
            .contentType(JSON)
            .accept(JSON)
            .body(JSON_USER_NOT_FOUND.getFile())
        .expect()
            .statusCode(NOT_FOUND.value())
        .when()
            .put(uri);
    }

    @Test
    public void putUserWithMismatchedIdsReturnsBadRequest400 () {
            final String uri = getUsersURL() + USER2_ID;

        given()
            .contentType(JSON)
            .accept(JSON)
            .body(JSON_USER_UPDATE.getFile())
        .expect()
            .statusCode(BAD_REQUEST.value())
        .when()
            .put(uri);
    }

    @Test
    public void deleteUserReturnsNoContent204 () {
        final String uri = getUsersURL() + USER_ID;

        expect()
            .statusCode(NO_CONTENT.value())
        .when()
            .delete(uri);

        assertThat(userRepository.existsById(USER_ID)).isFalse();
    }

    @Test
    public void deleteNonExistingUserReturnsNotFound404 () {
        final String uri = getUsersURL() + USER_ID_NON_EXISTING;

        expect()
            .statusCode(NOT_FOUND.value())
        .when()
            .delete(uri);
    }

    @Test
    public void countReturnsEntityCountFromDB() {
        final String uri = getUsersURL() + "count";
        final long actualUserCount = userRepository.count();

        expect()
            .body(equalTo(String.valueOf(actualUserCount)))
        .when()
            .get(uri);
    }

}
