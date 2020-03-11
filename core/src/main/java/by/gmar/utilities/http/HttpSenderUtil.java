package by.gmar.utilities.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;


/**
 *
 * @author s.kosik
 */
public class HttpSenderUtil {
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HttpSenderUtil.class);
    private HttpClient httpDefClient;
    
    public String getData(final String requestPath) throws Exception {
        if(null == requestPath)
            return null;
        final HttpGet httpGetMethod = new HttpGet(requestPath);
        final HttpResponse resp = getHttpDefClient().execute(httpGetMethod);
        return EntityUtils.toString(resp.getEntity());
    }
    
    @Deprecated
    public String executeHttpGETrequest(final String uri) {
        if (null == uri) {
            return null;
        }
        LOGGER.warn("executeHttpGETrequest " + uri);
        HttpGet httpGet = new HttpGet(uri);
        String resp = null;
        try {
            BasicResponseHandler handler = new BasicResponseHandler();
            resp = getHttpDefClient().execute(httpGet, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    public HttpClient getHttpDefClient() {
        if (null == httpDefClient) {
            httpDefClient = HttpClients.createDefault();
        }
        return httpDefClient;
    }

}