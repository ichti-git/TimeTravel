/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import static com.jayway.restassured.path.json.JsonPath.from;
import deploy.DeploymentConfiguration;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rest.ApplicationConfig;
import timeTravel.entities.Airport;
import timeTravel.facade.Facade;

/**
 *
 * @author sofus
 */
public class TimeTravelFacadeTest {

    static Server server;

    public TimeTravelFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
/*
    @Test
    public void getAirlinesTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Test_PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Airline airline1 = new Airline("Test Airline", "www.TestAirline.com");
        em.persist(airline1);
        Airline airline2 = new Airline("NotSoRealAirline", "www.RealAirline.com");;
        em.persist(airline2);
        em.getTransaction().commit();
        em.close();
        
        Facade facade = new Facade();
        List<Airline> airlines = facade.getAirlines();
        List<Airline> expected = Arrays.asList(airline1, airline2);
        assertThat(airlines, expected);
        assertEquals(2, airlines.size());
    }
    * */
}
