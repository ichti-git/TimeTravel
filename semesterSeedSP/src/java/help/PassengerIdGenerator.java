
package help;

import deploy.DeploymentConfiguration;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author maze<martin weber hansen at gmail.com>
 */
public class PassengerIdGenerator {
//    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
//    EntityManager em =  emf.createEntityManager();
//      
    public long getRandomId(){
        long LOWER_RANGE = 0; //assign lower range value
        Random random = new Random();
                                                 // long UPPER_RANGE-LOWER_RANGE
        long randomValue = 1 + (long)(random.nextDouble()*(10000000  - 0));
    return randomValue;
    }
}
