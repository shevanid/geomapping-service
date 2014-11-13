package com.flipkart.geomapping.resources.filters;

import org.activejpa.jpa.JPA;
import org.activejpa.jpa.JPAContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class JpaContextRequestFilter implements ContainerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JpaContextRequestFilter.class);

    protected JPAContext getContext() {
        if(JPA.instance.getDefaultConfig()!=null)
            return JPA.instance.getDefaultConfig().getContext(false);
        return null;
    }

    public ContainerRequest filter(ContainerRequest containerRequest) {
        JPAContext context = getContext();
            if (context != null) {
                context.getEntityManager();
                logger.info("Context Created");
            }

        return containerRequest;
    }
}
