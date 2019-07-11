package com.proxym;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RestController;


@Component
@RefreshScope
@RestController
public class CustomInvalideSignatureException extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CustomInvalideSignatureException.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2; // Needs to run before SendErrorFilter which has filterOrder == 0
    }

    @Override
    public boolean shouldFilter() {
        // only forward to errorPath if it hasn't been forwarded to already
        // return RequestContext.getCurrentContext().containsKey("UNAUTHORIZED");
        return true;
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Object e = ctx.get("UNAUTHORIZED");

            if (e != null) {
                //ZuulException zuulException = (ZuulException)e;
                // LOG.error("Zuul failure detected: " + zuulException.getMessage(), zuulException);
                LOG.error("Zuul failure detected: ");
                // Remove error code to prevent further error handling in follow up filters
                ctx.remove("UNAUTHORIZED");

                // Populate context with new response values
                ctx.setResponseBody("UNAUTHORIZED Request : Token invalid");
                ctx.getResponse().setContentType("application/json");
                ctx.setResponseStatusCode(500); //Can set any error code as excepted
            }
        } catch (Exception ex) {
            LOG.error("Exception filtering in custom error filter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }
}