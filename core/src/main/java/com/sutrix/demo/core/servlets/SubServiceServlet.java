package com.sutrix.demo.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service= Servlet.class, immediate = true,
        property={
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=/bin/sample/subservice/servlet"
        })
public class SubServiceServlet extends SlingSafeMethodsServlet {
    private Logger logger;
    @Reference
    ResourceResolverFactory resourceResolverFactory;
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        logger = LoggerFactory.getLogger(SubServiceServlet.class);
        ResourceResolver serviceResourceResolver = null;
        Map<String, Object> serviceMap = new HashMap<>();
        serviceMap.put(ResourceResolverFactory.SUBSERVICE, "gmsubservice");
        try {
            serviceResourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceMap);
            Resource appResource = serviceResourceResolver.getResource("/apps/mydemo/components/content/breadcrumb");
            Resource contentResource = serviceResourceResolver.getResource("/content/mydemo/en/pageone");
            logger.debug("App Resource :" +appResource);
            logger.debug("Content Resource :" +contentResource);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(serviceResourceResolver != null && serviceResourceResolver.isLive()) {
                serviceResourceResolver.close();
            }
        }
    }
}
