package org.eclipse.jakarta.hello.api;


import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.jakarta.hello.Person;
import org.eclipse.jakarta.hello.jwtConfig.Config;

@Path("/auth")
@PermitAll
public class Auth {

    @Inject
    private Config config;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getToken(Person user){

        return config.generateToken(user.getUsername(), user.getPassword());
    }

}
