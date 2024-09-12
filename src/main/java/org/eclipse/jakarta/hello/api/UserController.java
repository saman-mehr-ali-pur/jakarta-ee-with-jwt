package org.eclipse.jakarta.hello.api;

import jakarta.annotation.Resource;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.*;
import jakarta.transaction.NotSupportedException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.jakarta.hello.Person;

@Path("hello")
@RolesAllowed({"user","admin"})
public class UserController {




	@PersistenceUnit(unitName="jpa")
	 EntityManagerFactory emf;

	@Resource
	UserTransaction userTransaction;


	@GET
	@Path("/say-hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String getHello(){
		return "hello world";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson(@QueryParam("name") String name) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
		Person person = new Person();
		person.setUsername(name);
		person.setId(1);
		userTransaction.begin();
		EntityManager em = emf.createEntityManager();
		person = em.find(Person.class,1);
		userTransaction.commit();

		return person;
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public Person savePerson(Person person)  {
		EntityManager em;
		try {
			em = emf.createEntityManager();
			userTransaction.begin();
			em.joinTransaction(); // Ensure EntityManager joins the ongoing transaction
			em.persist(person); // Persist the person object
			em.flush();

			userTransaction.commit();
			return person;
		} catch (HeuristicRollbackException e) {
            throw new RuntimeException(e);
        } catch (SystemException e) {
            throw new RuntimeException(e);
        } catch (HeuristicMixedException e) {
            throw new RuntimeException(e);
        } catch (NotSupportedException e) {
            throw new RuntimeException(e);
        } catch (RollbackException e) {
            throw new RuntimeException(e);
        }

    }




}