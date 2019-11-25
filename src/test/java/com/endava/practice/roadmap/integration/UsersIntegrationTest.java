package com.endava.practice.roadmap.integration;

import com.endava.practice.roadmap.domain.dao.UserRepository;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import com.endava.practice.roadmap.util.Resources;
import com.endava.practice.roadmap.util.TestUsers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.endava.practice.roadmap.util.TestUsers.CLIENT_2_NEW;
import static com.endava.practice.roadmap.util.TestUsers.CLIENT_NEW;
import static com.endava.practice.roadmap.util.TestUsers.CLIENT_NON_EXISTING;
import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.mapper.ObjectMapperType.JACKSON_2;
import static org.assertj.core.api.Assertions.assertThat;
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
@TestPropertySource("classpath:application-congratulations-test.properties")
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
    public void getAllUsersReturnsOK() {
        given()
            .accept(JSON)
        .expect()
            .statusCode(OK.value())
        .when()
            .get(usersPath);
    }

    @ParameterizedTest
    @EnumSource(value = TestUsers.class, names = {"ADMIN_EXISTING", "MANAGER_EXISTING", "ACCOUNTANT_EXISTING", "CLIENT_EXISTING"})
    public void getOneUserReturnsOK(TestUsers user) {

        given()
            .accept(JSON)
        .expect()
            .statusCode(OK.value())
            .body(
                    JSON_USERNAME, equalTo(user.getUsername()),
                    JSON_FULL_NAME, equalTo(user.getNickname()),
                    JSON_EMAIL, equalTo(user.getEmail()),
                    JSON_ROLE, equalTo(user.getRole().name()),
                    JSON_CREDITS, equalTo(user.getCredits()),
                    JSON_ACTIVE, equalTo(user.getActive()))
        .when()
            .get(usernamePathVariable.build(user.getUsername()));
    }

    @Test
    public void getNonExistingReturnsNotFound404 () {
        given()
            .accept(JSON)
        .expect()
            .statusCode(NOT_FOUND.value())
        .when()
            .get(usernamePathVariable.build(CLIENT_NON_EXISTING.getUsername()));
    }

    @Test
    public void postNewUserSuccessful201() {

        final TestUsers source = CLIENT_NEW;
        final UserDto newUserDto = source.buildUserDto();

            given()
                .contentType(JSON)
                .accept(JSON)
                .with()
                .body(newUserDto, JACKSON_2)
            .expect()
                .statusCode(CREATED.value())
                .body (JSON_USERNAME, equalTo(source.getUsername()),
                        JSON_FULL_NAME, equalTo(source.getNickname()),
                        JSON_EMAIL, equalTo(source.getEmail()),
                        JSON_ROLE, equalTo(source.getRole().name()),
                        JSON_CREDITS, equalTo(source.getCredits()),
                        JSON_ACTIVE, equalTo(source.getActive()))
            .when()
                .post(usersPath);

        final Integer presence = jdbcTemplate.queryForObject("SELECT count(*) FROM users WHERE user_name = ?", Integer.class, source.getUsername());
        assertThat(presence).as("POST /users updating database").isEqualTo(1);
        jdbcTemplate.update("DELETE FROM users WHERE user_name = ?", source.getUsername());
    }

    @ParameterizedTest
    @EnumSource(value = Resources.class,
            names = {"FILE_JSON_USER_MALFORMED", "FILE_JSON_USER_MISSING_NAME"})
    public void postUserJsonReturns400BadRequest(Resources resources) {
        given()
            .contentType(JSON)
            .accept(JSON)
            .body(resources.getFile())
        .expect()
            .statusCode(BAD_REQUEST.value())
            .body("status", equalTo("400 BAD_REQUEST"))
        .when()
            .post(usersPath);
    }

    @Test
    public void putUserReturnsAccepted202 () {

        final TestUsers originalSource = CLIENT_NEW;
        userRepository.save(originalSource.buildUser());
        final String originalUserName = originalSource.getUsername();

        final TestUsers updateSource = CLIENT_2_NEW;
        final String updatedUserName = updateSource.getUsername();
        final UserDto updatedUserDto = updateSource.buildUserDto();

        given()
            .contentType(JSON)
            .accept(JSON)
            .body(updatedUserDto, JACKSON_2)
        .expect()
            .statusCode(ACCEPTED.value())
            .body(JSON_USERNAME, equalTo(updatedUserName),
                    JSON_FULL_NAME, equalTo(updateSource.getNickname()),
                    JSON_EMAIL, equalTo(updateSource.getEmail()),
                    JSON_ROLE, equalTo(updateSource.getRole().name()),
                    JSON_CREDITS, equalTo(updateSource.getCredits()),
                    JSON_ACTIVE, equalTo(updateSource.getActive()))
        .when()
            .put(usernamePathVariable.build(originalUserName));

        final Integer originalExists = jdbcTemplate.queryForObject("SELECT count(*) FROM users WHERE user_name = ?", Integer.class, originalUserName);
        assertThat(originalExists).as("PUT /users user stayed the same").isEqualTo(0);

        final Integer updatedExists = jdbcTemplate.queryForObject("SELECT count(*) FROM users WHERE user_name = ?", Integer.class, updatedUserName);
        assertThat(updatedExists).as("PUT /users user is updated").isEqualTo(1);

        jdbcTemplate.update("DELETE FROM users WHERE user_name = ?", updatedUserName);
    }

    @Test
    public void putMissingUserReturnsNotFound404 () {
        given()
            .contentType(JSON)
            .accept(JSON)
            .body(CLIENT_2_NEW.buildUserDto(), JACKSON_2)
        .expect()
            .statusCode(NOT_FOUND.value())
        .when()
            .put(usernamePathVariable.build(CLIENT_NON_EXISTING.getId()));
    }

    @Test
    public void deleteUserReturnsNoContent204 () {
        userRepository.save(CLIENT_NEW.buildUser());

        expect()
            .statusCode(NO_CONTENT.value())
        .when()
            .delete(usernamePathVariable.build(CLIENT_NEW.getUsername()));

        assertThat(userRepository.existsByUsernameIgnoreCase(CLIENT_NEW.getUsername()))
            .as("DELETE /user").isFalse();
    }

    @Test
    public void deleteNonExistingUserReturnsNotFound404 () {
        expect()
            .statusCode(NOT_FOUND.value())
        .when()
            .delete(usernamePathVariable.build(CLIENT_NON_EXISTING.getUsername()));
    }
}
