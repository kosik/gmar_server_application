package by.gmar.model.dto;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


//TODO: rewrite it with new api HttpURLConnection

/**
 *
 * @author s.kosik
 */
public class HTTPAWrapper {
    private String endpoint;
    private List<NameValuePair> params;
    private List<NameValuePair> headers;
    private String entity;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public List<NameValuePair> getParams() {
        if(null == params){
            params = new ArrayList<NameValuePair>();
        }
        return params;
    }

    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public List<NameValuePair> getHeaders() {
        if(null == headers){
            headers = new ArrayList<NameValuePair>();
        }
        return headers;
    }

    public void setHeaders(List<NameValuePair> headers) {
        this.headers = headers;
    }
    
}
