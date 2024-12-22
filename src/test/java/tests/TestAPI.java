package tests;

import static core.Specification.responseSpecification;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;

import core.Specification;
import endpoints.StatusCodeBase;
import endpoints.UsersGET;
import fixtures.UserDetail;
import groovy.json.JsonException;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.fail;
import fixtures.UserDetail;
import io.restassured.specification.ResponseSpecification;
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

    @BeforeEach
    public void setUp() {
        requestSpecification = Specification.requestSpecification();
        test_id = "2";
        user_get = new UsersGET();
    }


    @Test
    @DisplayName("Тестирование запроса Get c проверкой status code = 200")
    public void testGetRequestStatusCode200() {
        StatusCodeBase.checked_status_code(
                test_id, requestSpecification,
                responseSpecification(),
                "GET", Config.USERS);
    }


    @Test
    @DisplayName("Тестирование Positive test запроса Get c проверкой key/value ")
    public void testGetOneUserPositiveWithId2() throws IOException {
        user_get.checked_one_user(UserDetail.expected_id_2, requestSpecification);
    }

    @Story(value = "Тестирование GET user detail")
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

    @ParameterizedTest(name = "negative {index} => reqres.in GET detail user of {0} = {1}")
    @CsvSource(delimiter = ',', textBlock = """
         negative data with letter id, -1
         negative data with zero id, 0
         negative data with zero id, A
         negative data with zero id, @
    """)
    @Severity(value = SeverityLevel.MINOR)
    public void testGetOneUserNegative(String desc, String user_id) throws IOException {
        user_get.checked_one_user(user_id, requestSpecification, responseSpecification(404));
    }
}
