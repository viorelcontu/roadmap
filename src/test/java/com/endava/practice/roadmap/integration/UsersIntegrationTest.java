package com.endava.practice.roadmap.integration;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.util.Resources;
import com.endava.practice.roadmap.util.TestUsers;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.endava.practice.roadmap.config.security.AuthenticationInterceptor.AUTHENTICATION_HEADER;
import static com.endava.practice.roadmap.util.TestUsers.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.mapper.ObjectMapperType.JACKSON_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UsersIntegrationTest {

    private static final String JSON_USERNAME = "username";
    private static final String JSON_FULL_NAME = "nickname";
    private static final String JSON_EMAIL = "email";
    private static final String JSON_ROLE = "role";
    private static final String JSON_CREDITS = "credits";
    private static final String JSON_ACTIVE = "active";

    final private UserRepository userRepository;
    final private URI usersPath;
    final private UriComponentsBuilder usernamePathVariable;
    final private JdbcTemplate jdbcTemplate;

    public UsersIntegrationTest(@Autowired UserRepository userRepository,
                                @Autowired JdbcTemplate jdbcTemplate,
                                @LocalServerPort int port) {
        final String localhost = "http://localhost";
        final String path = "/users";
        usersPath = fromUriString(localhost).port(port).path(path).build().toUri();
        usernamePathVariable = fromUri(usersPath).path("/{username}");

        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    public void getAllUsersReturnsOK200() {
        authorizedRestCall()
        .when()
            .get(usersPath)
        .then()
            .statusCode(OK.value());
    }

    @Test
    public void getAllUsersReturnsUnauthorized401(){
        unauthorizedRestCall()
        .when()
            .get(usersPath)
        .then()
            .statusCode(UNAUTHORIZED.value());
    }

    @Test
    public void getAllUsersReturnsForbidden403() {
        forbiddenRestCall()
        .when()
            .get(usersPath)
        .then()
            .statusCode(FORBIDDEN.value());
    }

    @ParameterizedTest
    @EnumSource(value = TestUsers.class, names = {"ADMIN_EXISTING", "MANAGER_EXISTING", "AUDIT_EXISTING", "CLIENT_EXISTING"})
    public void getOneUserReturnsOK(TestUsers user) {
        authorizedRestCall()
        .when()
            .get(usernamePathVariable.build(user.getUsername()))
        .then()
            .statusCode(OK.value())
            .body(
                    JSON_USERNAME, equalTo(user.getUsername()),
                    JSON_FULL_NAME, equalTo(user.getNickname()),
                    JSON_EMAIL, equalTo(user.getEmail()),
                    JSON_ROLE, equalTo(user.getRole().name()),
                    JSON_CREDITS, equalTo(user.getCredits()),
                    JSON_ACTIVE, equalTo(user.getActive()));
    }

    @Test
    public void getNonExistingReturnsNotFound404 () {
        authorizedRestCall()
        .when()
            .get(usernamePathVariable.build(CLIENT_NON_EXISTING.getUsername()))
        .then()
            .statusCode(NOT_FOUND.value());
    }

    @Test
    public void postNewUserSuccessful201() {

        final TestUsers source = CLIENT_NEW;
        final UserDto newUserDto = source.buildUserDto();

        authorizedRestCall()
            .with()
                .body(newUserDto, JACKSON_2)
        .when()
            .post(usersPath)
        .then()
            .statusCode(CREATED.value())
            .body(JSON_USERNAME, equalTo(source.getUsername()),
                    JSON_FULL_NAME, equalTo(source.getNickname()),
                    JSON_EMAIL, equalTo(source.getEmail()),
                    JSON_ROLE, equalTo(source.getRole().name()),
                    JSON_CREDITS, equalTo(source.getCredits()),
                    JSON_ACTIVE, equalTo(source.getActive()));

        final Integer presence = jdbcTemplate.queryForObject("SELECT count(*) FROM users WHERE user_name = ?", Integer.class, source.getUsername());
        assertThat(presence).as("POST /users updating database").isEqualTo(1);
        jdbcTemplate.update("DELETE FROM users WHERE user_name = ?", source.getUsername());
    }

    @ParameterizedTest
    @EnumSource(value = Resources.class,
            names = {"FILE_JSON_USER_MALFORMED", "FILE_JSON_USER_MISSING_NAME"})
    public void postUserJsonReturns400BadRequest(Resources resources) {
        authorizedRestCall()
            .body(resources.getFile())
        .when()
            .post(usersPath)
        .then()
            .statusCode(BAD_REQUEST.value())
            .body("status", equalTo("400 BAD_REQUEST"));
    }

    @Test
    public void putUserReturnsAccepted202 () {

        final TestUsers originalSource = CLIENT_NEW;
        userRepository.save(originalSource.buildUser());
        final String originalUserName = originalSource.getUsername();

        final TestUsers updateSource = CLIENT_2_NEW;
        final String updatedUserName = updateSource.getUsername();
        final UserDto updatedUserDto = updateSource.buildUserDto();

        authorizedRestCall()
            .body(updatedUserDto, JACKSON_2)
        .when()
            .put(usernamePathVariable.build(originalUserName))
        .then()
            .statusCode(ACCEPTED.value())
            .body(JSON_USERNAME, equalTo(updatedUserName),
                    JSON_FULL_NAME, equalTo(updateSource.getNickname()),
                    JSON_EMAIL, equalTo(updateSource.getEmail()),
                    JSON_ROLE, equalTo(updateSource.getRole().name()),
                    JSON_CREDITS, equalTo(updateSource.getCredits()),
                    JSON_ACTIVE, equalTo(updateSource.getActive()));

        final Integer originalExists = jdbcTemplate.queryForObject("SELECT count(*) FROM users WHERE user_name = ?", Integer.class, originalUserName);
        assertThat(originalExists).as("PUT /users user stayed the same").isEqualTo(0);

        final Integer updatedExists = jdbcTemplate.queryForObject("SELECT count(*) FROM users WHERE user_name = ?", Integer.class, updatedUserName);
        assertThat(updatedExists).as("PUT /users user is updated").isEqualTo(1);

        jdbcTemplate.update("DELETE FROM users WHERE user_name = ?", updatedUserName);
    }

    @Test
    public void putMissingUserReturnsNotFound404 () {
        authorizedRestCall()
            .body(CLIENT_2_NEW.buildUserDto(), JACKSON_2)
        .when()
            .put(usernamePathVariable.build(CLIENT_NON_EXISTING.getId()))
        .then()
            .statusCode(NOT_FOUND.value());
    }

    @Test
    public void deleteUserReturnsNoContent204 () {
        userRepository.save(CLIENT_NEW.buildUser());

        authorizedRestCall()
        .when()
            .delete(usernamePathVariable.build(CLIENT_NEW.getUsername()))
        .then()
            .statusCode(NO_CONTENT.value());

        assertThat(userRepository.existsByUsernameIgnoreCase(CLIENT_NEW.getUsername()))
            .as("DELETE /user").isFalse();
    }

    @Test
    public void deleteNonExistingUserReturnsNotFound404 () {
        authorizedRestCall()
        .when()
            .delete(usernamePathVariable.build(CLIENT_NON_EXISTING.getUsername()))
        .then()
            .statusCode(NOT_FOUND.value());
    }

    private RequestSpecification authorizedRestCall() {
        return restCallForUser(MANAGER_EXISTING);
    }

    private RequestSpecification unauthorizedRestCall() {
        return restCallForUser(CLIENT_NON_EXISTING);
    }

    private RequestSpecification forbiddenRestCall() {
        return restCallForUser(CLIENT_EXISTING);
    }

    private RequestSpecification restCallForUser(TestUsers user) {
        return given()
                .contentType(JSON)
                .accept(JSON)
                .header(AUTHENTICATION_HEADER, user.getToken());
    }
}
