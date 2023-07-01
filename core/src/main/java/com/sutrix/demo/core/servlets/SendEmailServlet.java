package com.sutrix.demo.core.servlets;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;

import java.io.IOException;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_PATHS;

@Component(
        service = Servlet.class, immediate = true,
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                SLING_SERVLET_PATHS + "=/bin/sendemail"
        }
)
public class SendEmailServlet extends SlingAllMethodsServlet {

    private static Logger log = LoggerFactory.getLogger(SendEmailServlet.class);

    @Reference
    MessageGatewayService messageGatewayService;
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        try{
            if (messageGatewayService != null) {
                MessageGateway<HtmlEmail> gateway = messageGatewayService.getGateway(HtmlEmail.class);
                HtmlEmail htmlEmail = new HtmlEmail();
                htmlEmail.addTo("murthygnana630@gmail.com");
                htmlEmail.setSubject("Test Mail");
                htmlEmail.setContent("Hi Gnanamurthy, <br/> Are You Free Tmrw<br/> Thanks and Regards", "text/html");
                gateway.send(htmlEmail);
            }
        }catch (EmailException e){

        }
        response.getWriter().write("Custom Mail Sender Servlet Loading");
    }
}
