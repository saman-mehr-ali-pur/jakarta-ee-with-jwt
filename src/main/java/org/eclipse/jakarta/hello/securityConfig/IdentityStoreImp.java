package org.eclipse.jakarta.hello.securityConfig;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.eclipse.jakarta.hello.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;


@ApplicationScoped
public class IdentityStoreImp implements IdentityStore {


    @PersistenceContext
    private EntityManager entityManager;

    public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential){
        String password = usernamePasswordCredential.getPasswordAsString();
        String username = usernamePasswordCredential.getCaller();

        List<Person> persons = entityManager.createQuery("SELECT p FROM Person p WHERE p.username = :username " +
                        "and p.password= :password").
                setParameter("username",username).
                setParameter("password",password).
                setMaxResults(30).getResultList();

        if (!persons.isEmpty())
            return new CredentialValidationResult(persons.get(0).getUsername(),Set.of("user"));

        return CredentialValidationResult.INVALID_RESULT;
    }


}
