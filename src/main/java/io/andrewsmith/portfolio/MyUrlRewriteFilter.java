package io.andrewsmith.portfolio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;

@Component
public class MyUrlRewriteFilter extends UrlRewriteFilter {
    private static final String CONFIG_LOCATION = "classpath:/urlrewrite.xml";

    //Inject the Resource from the given location
    @Value(CONFIG_LOCATION)
    private Resource resource;

    @Override
    protected void loadUrlRewriter(FilterConfig filterConfig) throws ServletException {
        try {
            //Create a UrlRewrite Conf object with the injected resource
            Conf conf = new Conf(filterConfig.getServletContext(), resource.getInputStream(), resource.getFilename(), "@@yourOwnSystemId@@");
            checkConf(conf);
        } catch (IOException ex) {
            throw new ServletException("Unable to load URL rewrite configuration file from " + CONFIG_LOCATION, ex);
        }
    }

}
