/*
 * Copyright 2016 Garvan Institute of Medical Research
 */
package au.org.garvan.vsal.core.rest;

import au.org.garvan.vsal.beacon.rest.CorsResponseFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * REST VSAL application.
 *
 * @author Dmitry Degrave (dmeetry@gmail.com)
 * @version 1.0
 */
@ApplicationPath("/core")
public class VsalCore extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(CoreResource.class,CorsResponseFilter.class));
    }

}