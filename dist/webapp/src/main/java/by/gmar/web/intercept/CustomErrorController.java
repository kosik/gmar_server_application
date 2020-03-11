package by.gmar.web.intercept;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gmar.web.WebConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author s.kosik
 */
@Controller
class CustomErrorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorController.class);
    
    /**
     * Display an error page, as defined in web.xml <code>custom-error</code> element.
     */
    @RequestMapping(WebConstants.STATUS)
    public String generalError(HttpServletRequest request, HttpServletResponse response, Model model) {
        // retrieve some useful information from the request
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        // String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        String exceptionMessage = getExceptionMessage(throwable, statusCode);

        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        String message = MessageFormat.format(
                "{0} returned for {1} with message {2}", 
                statusCode, requestUri, exceptionMessage);

        LOGGER.debug("status code " + statusCode);
        LOGGER.debug(exceptionMessage);
        LOGGER.debug(requestUri);
        LOGGER.debug(message);
        
        model.addAttribute("errorMessage", message);
        model.addAttribute("statusMsg", message);
     
        return "status";
    }

    private String getExceptionMessage(Throwable throwable, Integer statusCode) {
        if (throwable != null) {
            return Throwables.getRootCause(throwable).toString();
        }
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return httpStatus.getReasonPhrase();
    }
}