/*
package com.target.myRetail.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.target.myRetail.MyRetailApplication;
import com.target.myRetail.model.Product;
import com.target.myRetail.model.CurrentPrice;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
        import org.springframework.boot.test.web.client.TestRestTemplate;
        import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


@SpringBootTest(classes = MyRetailApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
public class MyRetailControllerIntegrationTest
{
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    */
/*@Test
    void contextLoads(ApplicationContext context) {
        assertThat(context).isNotNull();
    }*//*


    @Test
    public void testEmployeeDetails(ApplicationContext context) {
        */
/*Product prod = Product.builder().id("13860428").name("The Big Lebowski (Blu-ray) (Widescreen)")
                .currentPrice(CurrentPrice.builder().value(13.49d).currencyCode("USD").build()).build();
        ResponseEntity<Product> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/myRetail/product/" + "13860428", Product.class);
                //assertEquals(201, responseEntity.getStatusCodeValue());
*//*


        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Product> entity = new HttpEntity<Product>(null, headers);
        ResponseEntity<Product> response = restTemplate.exchange(
                createURLWithPort("/myRetail/product/13860428"),
                HttpMethod.GET, entity, Product.class);
        String expected = "{id:13860428,name:The Big Lebowski (Blu-ray) (Widescreen),currentPrice:{value:13.49,currencyCode:USD}}}";
        try {
            JSONAssert.assertEquals(expected, String.valueOf(response.getBody()), false);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + "8080" + uri;
    }
}
*/
