package org.eclipse.jakarta.hello;

import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;


@DeclareRoles({"user","admin"})
@ApplicationPath("rest")
public class HelloApplication extends Application {}
