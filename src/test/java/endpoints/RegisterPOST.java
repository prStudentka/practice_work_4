package endpoints;

import core.Specification;
import fixtures.Register;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.PostRegisterSchema;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.hamcrest.core.StringContains;

import java.io.IOException;

import static core.Config.REGISTER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RegisterPOST extends StatusCodeBase{

    private static final String ID_FIELD = "id";
    private static final String TOKEN_FIELD = "token";

    @Description("Checked register")
    private void checked_register(
            RequestSpecification reqSpecification,
            ResponseSpecification resSpecification,
            String email,
            String password
    ) {

        given()
                .log().all()
                .spec(reqSpecification)
                .when()
                .body(new PostRegisterSchema(email, password))
                //---> Endpoint для выполнения запроса POST
                .request("POST", REGISTER)//---> Endpoint для выполнения запроса POST
                .then()
                .spec(resSpecification)
                .assertThat()
                .log().ifStatusCodeMatches(Matchers.greaterThan(HttpStatus.SC_OK)) //---> Проверка статус код
                .body(ID_FIELD, equalTo(Register.ID))
                .body(TOKEN_FIELD, equalTo(Register.TOKEN));
    }

    @Description("Checked register fail")
    private void checked_register_fail(
            RequestSpecification reqSpecification,
            ResponseSpecification resSpecification,
            String email,
            String password
    ) {

        given()
                .log().all()
                .spec(reqSpecification)
                .when()
                .body(new PostRegisterSchema(email, password))
                //---> Endpoint для выполнения запроса POST
                .request("POST", REGISTER)//---> Endpoint для выполнения запроса POST
                .then()
                .spec(resSpecification)
                .assertThat()
                .log().ifStatusCodeMatches(Matchers.greaterThan(HttpStatus.SC_OK)) //---> Проверка статус код
                .log().ifError().body("error", equalTo("Missing password"));
    }


    @Step("POST regiser with email and password")
    @Description("Checked register status_code and id, token")
    public void checked_register_user(ResponseSpecification responseSpecification, String email, String password) throws IOException {
        checked_register(Specification.requestSpecification(), responseSpecification, email, password);
    }

    @Step("Negative POST regiser with email and password")
    @Description("Checked fail register status_code and id, token")
    public void checked_register_user(String email, String password) throws IOException {
        checked_register_fail(Specification.requestSpecification(), Specification.responseSpecification(HttpStatus.SC_BAD_REQUEST), email, password);
    }

    @Step("POST register")
    @Description("Checked status_code 200 and schema")
    public void checked_status_code_post(String email, String password) throws IOException {
        checked_status_code(
                Specification.requestSpecification(),
                Specification.responseSpecification(),
                "POST",
                REGISTER,
                email,
                password
        );
    }
}
