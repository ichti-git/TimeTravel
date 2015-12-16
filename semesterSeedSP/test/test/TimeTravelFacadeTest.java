/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import deploy.DeploymentConfiguration;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import net.sf.cglib.core.CollectionUtils;
import org.eclipse.jetty.server.Server;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import timeTravel.entities.Airline;
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
        DeploymentConfiguration.PU_NAME = "Test_PU";
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void getAirlinesTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Test_PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Airline airline1 = new Airline("Test Airline", "www.TestAirline.com");
        em.persist(airline1);
        Airline airline2 = new Airline("NotSoRealAirline", "www.RealAirline.com");
        em.persist(airline2);
        em.getTransaction().commit();
        em.close();
        
        Facade facade = new Facade();
        List<Airline> airlines = facade.getAirlines();
        assertThat(airlines.get(0).getName(), anyOf(is(airline1.getName()), is(airline2.getName())));
        assertThat(airlines.get(1).getName(), anyOf(is(airline1.getName()), is(airline2.getName())));
        assertThat(airlines.get(0).getUrl(), anyOf(is(airline1.getUrl()), is(airline2.getUrl())));
        assertThat(airlines.get(1).getUrl(), anyOf(is(airline1.getUrl()), is(airline2.getUrl())));
        assertEquals(2, airlines.size());
    }
    
}
