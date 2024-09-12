package org.eclipse.jakarta.hello.securityConfig;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jakarta.hello.jwtConfig.Config;


@ApplicationScoped
public class AuthenticatonMechanism implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStore identityStore;
    @Inject
    private Config jwtConfig;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {

        String token = request.getHeader("Authorization");



        if (token==null)
           return httpMessageContext.doNothing();
        if (jwtConfig.isValid(token.substring(7))){
            return httpMessageContext.responseUnauthorized();
        }

        String username =  jwtConfig.getClaim("iss",token.substring(7),String.class);
        String password = jwtConfig.getClaim("password",token.substring(7),String.class);

        CredentialValidationResult result = identityStore.validate(new UsernamePasswordCredential(username,password));


        if (result.getStatus().equals(CredentialValidationResult.Status.VALID)){
            return httpMessageContext.notifyContainerAboutLogin(result.getCallerPrincipal(),result.getCallerGroups());
        }

        return httpMessageContext.responseUnauthorized();
    }
}
