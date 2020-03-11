package by.gmar.utilities.http;

import by.gmar.model.dto.HTTPAWrapper;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;


/**
 *
 * @author s.kosik
 */
public class HttpSenderUtil2 {
    private HttpClient httpDefClient;
    private List<NameValuePair> postParameters;
    private static CookieStore cookieStore;
    private HttpClientContext localContext;
    private HttpPost httpPost;
        
    private HttpClientContext getHttpContext(){
        if(null == localContext){
            localContext = HttpClientContext.create();
            localContext.setCookieStore(getCookieStore());
        }
        return localContext;
    }

    public static CookieStore getCookieStore(){
        if(null == cookieStore){
            cookieStore = new BasicCookieStore();
        }
        return cookieStore;
    }

    public String getData(final String requestPath) throws Exception {
        if(null == requestPath)
            return null;
        final HttpGet httpGetMethod = new HttpGet(requestPath);
        final HttpResponse resp = getHttpDefClient().execute(httpGetMethod, getHttpContext());
        return EntityUtils.toString(resp.getEntity());
    }

    public HttpPost getHttpPost(){
        if(null == httpPost){
            httpPost = new HttpPost();
        }
        return httpPost;
    }
    
    
    public String getData(final String requestPath, final String params) throws Exception {
        if(null == requestPath)
            return null;
        getHttpPost().setURI(new URI(requestPath));
        if(null != params || !params.isEmpty())
            getHttpPost().setEntity(new StringEntity(params));
        
        final HttpResponse resp = getHttpDefClient()
                .execute(getHttpPost(), getHttpContext());
        return EntityUtils.toString(resp.getEntity());
    }
    
    public String getData(final String requestPath, List<NameValuePair> params) throws Exception {
        if(null == requestPath)
            return null;
        getHttpPost().setURI(new URI(requestPath));
        getHttpPost().setEntity(new UrlEncodedFormEntity(params));
        
        final HttpResponse resp = getHttpDefClient()
                .execute(getHttpPost(), getHttpContext());
        return EntityUtils.toString(resp.getEntity());
    }

    public String getData(final HTTPAWrapper params) throws Exception {
        if(null == params)
            return null;
        final HttpPost method = new HttpPost(params.getEndpoint());
        if(null != params.getParams()){
            method.setEntity(new UrlEncodedFormEntity(params.getParams()));
            System.out.println("new UrlEncodedFormEntity(params.getParams()))");
        }
        if(null != params.getHeaders()){
            for(NameValuePair item : params.getHeaders()){
                method.setHeader(item.getName(), item.getValue());
            }
        }//
        
        final HttpResponse resp = getHttpDefClient().execute(method, getHttpContext());

        final String respose = EntityUtils.toString(resp.getEntity());
        System.out.println(respose);

        return respose;
    }
    
    public String sendEntity(final HTTPAWrapper params) throws Exception {
        if(null == params)
            return null;
        final HttpPost method = new HttpPost(params.getEndpoint());
        if(null != params){
            method.setEntity(new ByteArrayEntity(params.getEntity().getBytes()));
        }
        if(null != params.getHeaders()){
            for(NameValuePair item : params.getHeaders()){
                method.setHeader(item.getName(), item.getValue());
            }
        }
        
        final HttpResponse resp = getHttpDefClient().execute(method, getHttpContext());

        final String respose = EntityUtils.toString(resp.getEntity());
        System.out.println(respose);

        return respose;
    }    

    public HttpClient getHttpDefClient() {
        if (null == httpDefClient) {
            httpDefClient = HttpClients.createDefault();
        }
        return httpDefClient;
    }

    public void setHttpDefClient(HttpClient httpDefClient) {
        this.httpDefClient = httpDefClient;
    }

    public List<NameValuePair> getPostParameters() {
        if(null == postParameters){
            postParameters = new ArrayList<NameValuePair>();
        }
        return postParameters;
    }

    public void setPostParameters(ArrayList<NameValuePair> postParameters) {
//        postParameters.add(new BasicNameValuePair("param1", "param1_value"));
        this.postParameters = postParameters;
    }
}