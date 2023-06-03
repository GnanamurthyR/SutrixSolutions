package com.sutrix.demo.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = Servlet.class, immediate = true, property = {
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=/query/fetch/pages"
})
public class QueryServlet extends SlingSafeMethodsServlet {

    @Reference
    QueryBuilder queryBuilder;
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        ResourceResolver resourceResolver = request.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        StringBuilder sb = new StringBuilder();
        Map<String, String> map = new HashMap<>();
        map.put("path", "/content/mydemo/en");
        map.put("type", "cq:page");
        map.put("p.limit", "-1");
        Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
        SearchResult result = query.getResult();
        List<Hit> hits = result.getHits();
        for (Hit hit : hits){
            try {
                String title = hit.getTitle();
                String path = hit.getPath();
                sb.append(title + "<br>");
                sb.append(path + "<br>");
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        }
        response.getWriter().write(sb.toString());
        response.setContentType("text/html");
    }
}
