package com.flipkart.geomapping.resources.filters;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import org.activejpa.jpa.JPA;
import org.activejpa.jpa.JPAContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaContextResponseFilter implements ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(JpaContextResponseFilter.class);

    protected JPAContext getContext() {
        if(JPA.instance.getDefaultConfig()!=null)
            return JPA.instance.getDefaultConfig().getContext(false);
        return null;
    }

    
    public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
        logger.info(" Destroy filter called");
        JPAContext context = getContext();
        if (context != null) {
            if (context.isTxnOpen()) {
                context.closeTxn(true);
            }
            context.close();
            logger.info("Context Destroyed");
        }
        return containerResponse;
    }

}
