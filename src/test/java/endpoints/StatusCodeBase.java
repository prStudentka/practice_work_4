package endpoints;

import groovy.json.JsonException;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;

public class StatusCodeBase {
    /* Основной класс для проверки статус кода

     **/

    @DisplayName("Тестирование status_code")
    public static void checked_status_code(
            String id,
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification,
            String request_name,
            String path
    ) throws JsonException
    {
        given()
                .pathParam("id", id)
                //---> Указание RequestSpecification для формирования request
                .spec(requestSpecification)
                .request(request_name, path)
                .then()
                .spec(responseSpecification);
    }
}
