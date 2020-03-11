package by.gmar.web.controller.messages;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;


/**
 *
 * @author s.kosik
 */
@Component
public class ResponseBuilderFactoryBean implements FactoryBean<ResponseBuilder> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public ResponseBuilder getObject() throws Exception {
        return new ResponseBuilder(messageSource);
    }

    @Override
    public Class<?> getObjectType() {
        return ResponseBuilder.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public ResponseBuilder instance() {

        try {
            final ResponseBuilder responseBuilder = getObject();
            return responseBuilder;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
