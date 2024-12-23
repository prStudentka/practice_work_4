package tests;

//import static core.Specification.responseSpecification;
//import static io.restassured.RestAssured.given;
//import static io.restassured.RestAssured.responseSpecification;
//import static org.hamcrest.Matchers.equalTo;

import core.Specification;
import core.Utils;
import endpoints.*;
import fixtures.Register;
import fixtures.UserDetail;
import groovy.json.JsonException;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
//import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import io.restassured.http.ContentType;

//import static org.hamcrest.Matchers.greaterThan;
//import static org.junit.jupiter.api.Assertions.fail;
import fixtures.UserDetail;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
//import org.apache.http.util.Asserts;
//import org.checkerframework.checker.index.qual.Positive;
//import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import core.Config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;


public class TestAPI
{
    private static RequestSpecification requestSpecification;
    private static String test_id;
    private UsersGET user_get;
    private UsersPUT user_put;

    @BeforeEach
    public void setUp() {
        requestSpecification = Specification.requestSpecification();
        test_id = "2";
        user_get = new UsersGET();
        user_put = new UsersPUT();
    }


    @Test
    @Step("Positive Check GET status_code = 200")
    @DisplayName("Тестирование запроса Get c проверкой status code = 200")
    public void testGetRequestStatusCode200() {
        StatusCodeBase.checked_status_code(
                test_id, requestSpecification,
                Specification.responseSpecification(),
                "GET", Config.USERS);
    }

    @Story(value = "Тестирование GET ")
    @Test
    @Step("Проверка юзера c id 2 - key/value")
    @DisplayName("Тестирование Positive test запроса Get c проверкой key/value ")
    public void testGetOneUserPositiveWithId2() throws IOException {
        user_get.checked_one_user(UserDetail.expected_id_2, requestSpecification);
    }

    @Story(value = "Тестирование GET ")
    @Step("Positive Check GET user by id, name, last_name")
    @ParameterizedTest(name = "positive {index} => reqres.in GET detail user of {0}")
    @MethodSource("hashMapProvider")
    public void testGetOneUserPositive(Map<String, String> data) throws IOException {
        user_get.checked_one_user(data.get("id"), requestSpecification, data.get("name"), data.get("last_name"));
    }


    static Stream<Map<String, String>> hashMapProvider() {
        return Stream.of(
                UserDetail.expected_id_2,
                UserDetail.expected_id_7
        );
    }

    @Story(value = "Тестирование GET ")
    @ParameterizedTest(name = "negative {index} => reqres.in GET detail user of {0} = {1}")
    @CsvSource(delimiter = ',', textBlock = """
         negative data with letter id, -1
         negative data with zero id, 0
         negative data with char id, A
         negative data with symbol id, @
    """)
    @Step("Negative Check GET user by id {1} and status code 404")
    @Severity(value = SeverityLevel.MINOR)
    public void testGetOneUserNegative(String desc, String user_id) throws IOException {
        user_get.checked_one_user(user_id, requestSpecification, Specification.responseSpecification(404));
    }


    @Story(value = "Тестирование PUT ")
    @ParameterizedTest(name = "positive {index} => reqres.in PUT user of {0} = {1} ({2}, {3})")
    @CsvSource(delimiter = ',', textBlock = """
         test data id, 2, morpheus, zion resident
         test data id, 2, ron, doctor
    """)
    @DisplayName("Тестирование запроса PUT с проверкой status code = 200")
    @Step("Positive Check status code PUT")
    public void testPutRequestCheckStatusCode(String description, String id, String name, String job) throws IOException {
        user_put.checked_status_code_put(Config.USERS, id, name, job);
    }

    @Story(value = "Тестирование PUT ")
    @ParameterizedTest(name = "positive {index} => reqres.in PUT user of {0} = {1} ({2}, {3})")
    @CsvSource(delimiter = ',', textBlock = """
         test data id, 2, patrick, actor
         test data id, 7, ron, doctor
    """)
    @DisplayName("Тестирование запроса PUT с обновлением данных")
    @Step("Positive Check PUT with status code 200 and update data body")
    public void testPutRequestCheckBody(String description, String id, String name, String job) throws IOException {
        user_put.checked_change_user(id, name, job);
    }

    @Story(value = "Тестирование PUT ")
    @ParameterizedTest(name = "negative {index} => reqres.in PUT user of {0} = {1} ({2}, {3})")
    @CsvSource(delimiter = ',', textBlock = """
         test data char id, A, jack, zion resident
         test data zero id, 0, ron, doctor
    """)
    @DisplayName("Тестирование запроса PUT с обновлением данных")
    @Step("Negative Check PUT with status code 200 and update data body")
    public void testPutRequestCheckBodyNegative(String description, String id, String name, String job) throws IOException {
        user_put.checked_change_user(id, name, job);
    }

    @Story(value = "Тестирование DELETE ")
    @Test
    @DisplayName("Тестирование запроса DELETE пользователя по id с проверкой status code = 204")
    @Step("Positive Check DELETE user by id")
    public void testDeleteRequestCheckStatusCode() throws IOException {
        UsersDELETE user_delete = new UsersDELETE();
        user_delete.checked_change_user(test_id);
    }

    @Story(value = "Тестирование DELETE ")
    @Test
    @DisplayName("Тестирование запроса DELETE пользователя по id")
    @Step("Negative Check DELETE user by id letters")
    public void testDeleteRequestCheckNegative() throws IOException {
        UsersDELETE user_delete = new UsersDELETE();
        user_delete.checked_change_user("Jack");
    }


    @Story(value = "Тестирование POST ")
    @Test
    @DisplayName("Тестирование запроса POST registr проверка status code = 200")
    public void testPostRequestCheckStatusCode() throws IOException {
        RegisterPOST registr = new RegisterPOST();
        registr.checked_status_code_post(Register.EMAIL, Register.PASSWORD);
    }

    @Story(value = "Тестирование POST ")
    @Test
    @DisplayName("Тестирование запроса POST registr проверка с email и password")
    public void testPostRequestCheck() throws IOException {
        RegisterPOST registr = new RegisterPOST();
        registr.checked_register_user(Specification.responseSpecification(), Register.EMAIL, Register.PASSWORD);
    }

    @Story(value = "Тестирование POST ")
    @DisplayName("Тестирование запроса POST registr проверка с email {0}")
    @Step("Negative Check POST registr by email {0} and password")
    @ParameterizedTest(name = "negative {index} => reqres.in POST register of {0}")
    @MethodSource("StringProvider")
    public void testPostRequestCheckNegative(String email) throws IOException {
        RegisterPOST registr = new RegisterPOST();
        registr.checked_register_user(email, Register.PASSWORD_FAIL);
    }

    static Stream<String> StringProvider() {
        return Stream.of(
                Register.EMAIL,
                Register.EMAIL_FAIL
        );
    }
}
