package facades;

import deploy.DeploymentConfiguration;
import entity.Role;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import security.PasswordHash;

public class UserFacade {

    private EntityManagerFactory emf;
    
    public UserFacade(EntityManagerFactory e) {
        emf = e;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public User getUserByUserId(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }
    /*
     Return the Roles if users could be authenticated, otherwise null
     */

    public List<String> authenticateUser(String userName, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, userName);
            if (user == null) {
                return null;
            }
            try {
                boolean authenticated = PasswordHash.validatePassword(password, user.getPassword());
                return authenticated ? user.getRolesAsStrings() : null;
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }

        } finally {
            em.close();
        }

    }
    public Role roles(String roleName){
        EntityManager em = getEntityManager();
        em = emf.createEntityManager();
        Role userRole = em.find(Role.class, roleName);
        return userRole;
        
    }
    
    public void createUser(String userName, String password, String firstName, String lastName, String email, String phone) {
        try {
            User user = new User(userName, PasswordHash.createHash(password), firstName, lastName, email, phone);
            EntityManager em = getEntityManager();
            em = emf.createEntityManager();
            Role role = em.find(Role.class, "User");
            //Role role = new Role("User");
            
            
            user.AddRole(role);
            
            try {
                
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
                
            } finally {
                em.close();
            }
            
            
            
        }       catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    
       
    }


}
