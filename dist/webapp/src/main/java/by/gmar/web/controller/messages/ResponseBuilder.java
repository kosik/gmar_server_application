package by.gmar.web.controller.messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Builder pattern implementation for building {@link Response} objects.
 *
 * @author s.kosik
 */
public class ResponseBuilder {

    private Map<String, Object> model;

    private List<Message> messages;

    private List<FieldError> fieldErrors;

    private boolean success;
    
    private Set<String> excludes;

    @Autowired
    private MessageSource messageSource;

    ResponseBuilder(MessageSource messageSource) {

        super();

        this.messageSource = messageSource;

        model = new HashMap<>();
        messages = new ArrayList<>();
        fieldErrors = new ArrayList<>();
        success = true;
    }

    /**
     * Adds an object to the response model.
     * 
     * @param key The key under which the object will be added.
     * @param object The object to be added.
     * @return The {@link ResponseBuilder} object being invoked to allow chaining.
     */
    public ResponseBuilder addObject(final String key, final Object object) {
        model.put(key, object);
        return this;
    }

    public ResponseBuilder addObjects(final String key, final Map<String, Object> objects) {
        Map<String, Object> result = new HashMap<>();
        
        for(String item : objects.keySet()){
            if(getExcludes().contains(item))
                continue;
            Object value = objects.get(item);
            if(value instanceof Collection)
                continue;
            if(null != value){
                result.put(item, value);
            }
        }
        model.put(key, result);
        return this;
    }

    
    /**
     * Adds a success message to the response.
     * 
     * @param messageKey The message key under which the message will be looked up in the
     *        {@link MessageSource}.
     * @param messageParams Optional message parameters.
     * @return The {@link ResponseBuilder} object being invoked to allow chaining.
     * @see Message
     * @see MessageType
     */
    public ResponseBuilder addSuccessMessage(final String messageKey, final String... messageParams) {

        final String msg = messageSource.getMessage(messageKey, messageParams, LocaleContextHolder.getLocale());
        messages.add(new Message(MessageType.SUCCESS, msg));

        return this;
    }

    /**
     * Adds an info message to the response.
     * 
     * @param messageKey The message key under which the message will be looked up in the
     *        {@link MessageSource}.
     * @param messageParams Optional message parameters.
     * @return The {@link ResponseBuilder} object being invoked to allow chaining.
     * @see Message
     * @see MessageType
     */
    public ResponseBuilder addInfoMessage(final String messageKey, final String... messageParams) {

        final String msg = messageSource.getMessage(messageKey, messageParams, LocaleContextHolder.getLocale());
        messages.add(new Message(MessageType.INFO, msg));

        return this;
    }

    /**
     * Adds an error message to the response.
     * 
     * @param messageKey The message key under which the message will be looked up in the
     *        {@link MessageSource}.
     * @param messageParams Optional message parameters.
     * @return The {@link ResponseBuilder} object being invoked to allow chaining.
     * @see Message
     * @see MessageType
     */
    public ResponseBuilder addErrorMessage(final String messageKey, final String... messageParams) {

        final String msg = messageSource.getMessage(messageKey, messageParams, LocaleContextHolder.getLocale());
        messages.add(new Message(MessageType.ERROR, msg));
        success = false;
        return this;
    }

    public ResponseBuilder addErrorMessageWithCode(final String messageKey, final int code,
            final String... messageParams) {
        final String msg = messageSource.getMessage(messageKey, messageParams, LocaleContextHolder.getLocale());
        final Message message = new Message(MessageType.ERROR, msg);
        message.setCode(code);
        messages.add(message);
        success = false;
        return this;
    }

    /**
     * Adds an error message that is linked to a {@link Throwable} to the response.
     * 
     * @param throwable The {@link Throwable} that should be reported in the response. The message
     *        that will be displayed is being looked up in the {@link MessageSource} with the
     *        {@code throwable}'s class being used as the key.
     * @param messageParams Optional message parameters.
     * @return The {@link ResponseBuilder} object being invoked to allow chaining.
     */
    public ResponseBuilder addError(final Throwable throwable, final String... messageParams) {

        final String msg =
                messageSource.getMessage(throwable.getClass().getName(), messageParams,
                        LocaleContextHolder.getLocale());
        messages.add(new Message(MessageType.ERROR, msg));
        success = false;
        return this;
    }

    /**
     * Convenient method for adding {@link Error}-s which occurred during the <i>binding</i> and
     * <i>validation</i> process in a Spring {@link Controller} method.
     * 
     * @param errors The errors which have occurred.
     * @return The {@link ResponseBuilder} object being invoked to allow chaining.
     */
    public ResponseBuilder addErrors(final Errors errors) {

        addGlobalErrors(errors);
        addFieldErrors(errors);
        success = errors == null || (!errors.hasErrors() && !errors.hasFieldErrors());

        return this;
    }

    public ResponseBuilder addErrorsByKeys(final Errors errors) {

        for (final org.springframework.validation.FieldError springFieldError : errors.getFieldErrors()) {

            final String defaultMsgKey = springFieldError.getDefaultMessage();
            String msg = null;
            try {
                msg = messageSource.getMessage(defaultMsgKey, null, LocaleContextHolder.getLocale());
            } catch (Exception e) {
                msg = springFieldError.getDefaultMessage();
            }
            final String customMsg = findCustomMessage(springFieldError);
            final FieldError fieldError =
                    new FieldError(springFieldError.getField(), customMsg != null ? customMsg : msg);
            fieldErrors.add(fieldError);
            success = false;
        }
        success = errors == null || (!errors.hasErrors() && !errors.hasFieldErrors());

        return this;
    }
    
    public ResponseBuilder indicateFailure() {
        success = false;
        return this;
    }

    public ResponseBuilder indicateSuccess() {
        success = true;
        return this;
    }

    private void addGlobalErrors(final Errors errors) {

        for (final ObjectError springGlobalError : errors.getGlobalErrors()) {

            final String defaultMsg = springGlobalError.getDefaultMessage();
            final String customMsg = findCustomMessage(springGlobalError);
            messages.add(new Message(MessageType.ERROR, customMsg != null ? customMsg : defaultMsg));
        }
    }

    public ResponseBuilder addFieldError(final String fieldName, final String messageKey,
            final String... messageParams) {
        final String codeMsg = messageSource.getMessage(messageKey, messageParams, LocaleContextHolder.getLocale());
        final FieldError fieldError = new FieldError(fieldName, codeMsg);
        fieldErrors.add(fieldError);
        success = false;
        return this;
    }

    private void addFieldErrors(final Errors errors) {

        for (final org.springframework.validation.FieldError springFieldError : errors.getFieldErrors()) {

            final String defaultMsg = springFieldError.getDefaultMessage();
            final String customMsg = findCustomMessage(springFieldError);
            final FieldError fieldError =
                    new FieldError(springFieldError.getField(), customMsg != null ? customMsg : defaultMsg);
            fieldErrors.add(fieldError);
            success = false;
        }
    }

    /**
     * Searches for a custom error message using the defined {@link MessageSource}.
     * 
     * @param error The {@link ObjectError} the custom error messages is looked up for.
     * @return The message if found; {@code null} otherwise.
     */
    private String findCustomMessage(final ObjectError error) {

        for (String code : error.getCodes()) {
            final String codeMsg =
                    messageSource.getMessage(code, error.getArguments(), null, LocaleContextHolder.getLocale());
            if (codeMsg != null) {
                return codeMsg;
            }
        }

        return null;
    }

    /**
     * Builds a {@link Response} object.
     * 
     * @return The resulting {@link Response} object.
     */
    public Response build() {

        final Response response = new Response();
        response.setModel(model);
        response.setMessages(messages);
        response.setFieldErrors(fieldErrors);
        response.setSuccess(success);

        return response;
    }

    public void intoModel(final Model model) {

        model.addAllAttributes(this.model);
        model.addAttribute("messages", messages);
    }

    public Set<String> getExcludes() {
        if(null == excludes){
            excludes = new HashSet<>();
            excludes.add("class");
            excludes.add("accountNonExpired");
            excludes.add("credentialsNonExpired");
        }
        return excludes;
    }
    
    
}
