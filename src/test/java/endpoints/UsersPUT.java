package endpoints;


import core.Specification;
import groovy.json.JsonException;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.UpdateUserSchema;

import java.io.IOException;

import static core.Config.USERS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UsersPUT extends StatusCodeBase {

    private static final String NAME_FIELD = "name";
    private static final String JOB_FIELD = "job";

    @Description("Checked one user by name and last_name")
    private void checked_user(
            String id,
            RequestSpecification reqSpecification,
            ResponseSpecification resSpecification,
            String name,
            String job
    ) throws JsonException
    {
        given()
                .log().all()
                .pathParam("id", id)
                .spec(reqSpecification)
                .body(new UpdateUserSchema(name, job))
                .when()
                //---> Endpoint для выполнения запроса POST
                .request("PUT", USERS)
                .then()
                .spec(resSpecification)
                .log().ifValidationFails()
                .assertThat()
                //---> Проверка Body по key и value в json
                .body(NAME_FIELD, equalTo(name))
                .body(JOB_FIELD, equalTo(job))
                .extract()
                .response()
                .body();
    }


    @Step("PUT user by id")
    @Description("Checked one user by id, name, last_name and status_code 201")
    public void checked_change_user(String id, String name, String job) throws IOException {
        checked_user(id, Specification.requestSpecification(), Specification.responseSpecification(), name, job);
    }


    @Step("PUT user by id")
    @Description("Checked status_code 200 and schema")
    public void checked_status_code_put(String path, String id, String name, String job) throws IOException {
        checked_status_code(
                id,
                Specification.requestSpecification(),
                Specification.responseSpecification(),
                "PUT",
                path,
                name,
                job
        );
    }
}
