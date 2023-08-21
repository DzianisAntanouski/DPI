package com.sap.cloud.sdk.showcase;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.sap.cloud.sdk.testutil.MockUtil;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith( Arquillian.class )
public class HelloWorldServletTest
{
    private static final MockUtil mockUtil = new MockUtil();

    @ArquillianResource
    private URL baseUrl;

    @Deployment
    public static WebArchive createDeployment()
    {
        return TestUtil.createDeployment(HelloWorldServlet.class);
    }

    @BeforeClass
    public static void beforeClass()
    {
        mockUtil.mockDefaults();
    }

    @Before
    public void before()
    {
        RestAssured.baseURI = baseUrl.toExternalForm();
        mockUtil.mockErpDestination();
    }

    @Test
    public void testService()
    {
        Response response = given().get("hello");
        String body = response.body().asString();

        assertTrue(!body.isEmpty());
        assertFalse(response.getContentType().equals("application/json"));
     }
}
