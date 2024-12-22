package core;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import static core.Config.BASE_URL;

public class Specification {


    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responseSpecification() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK) // ---> Проверка статус код
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responseSpecification(int status_code) {
        return new ResponseSpecBuilder()
                .expectStatusCode(status_code)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
