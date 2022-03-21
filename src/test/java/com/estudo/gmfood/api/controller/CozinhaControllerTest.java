package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.domain.model.Cozinha;
import com.estudo.gmfood.domain.repository.CozinhaRepository;
import com.estudo.gmfood.util.DatabaseCleaner;
import com.estudo.gmfood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CozinhaControllerTest {

    public static final int COZINHA_ID_INEXISTENTE = 100;
    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private Cozinha cozinhaAmericana;
    private int quantidadeCozinhasCadastradas;
    private String jsonCorretoCozinhaChinesa;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        prepararDados();

        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-chinesa.json");
    }

    @Test
    public void deveriaRetornarStatus200QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveriaConter2CozinhasQuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(quantidadeCozinhasCadastradas));
    }

    @Test
    public void deveriaRetornarStatus201QuandoCadastrarCozinha() {
        given()
            .body(jsonCorretoCozinhaChinesa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }
    @Test
    public void deveriaRetornarRespostaEStatusCorretosQuandoConsultarCozinhaExistente() {
        given()
            .pathParam("cozinhaId", cozinhaAmericana.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
           .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(cozinhaAmericana.getNome()));
    }

    @Test
    public void deveriaRetornarStatus404QuandoConsultarCozinhaExistente() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Tailandesa");
        cozinhaRepository.save(cozinha);

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Americana");
        cozinhaRepository.save(cozinha1);

        quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
    }
}