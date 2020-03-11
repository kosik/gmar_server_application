package by.gmar.utilities.http;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;


/**
 *
 * @author s.kosik
 */
public class HttpClientPreferencesSender {
	private HttpParams params;
	private HttpProcessor httpproc;
	private HttpRequestExecutor httpExecutor;
	private String hostName;
	private int port;
	private HttpHost host;
	private DefaultHttpClientConnection httpDefConn;
	private HttpContext context;
	private ConnectionReuseStrategy connStrategy;

	public HttpRequestExecutor getHttpExecutor() {
		return null == httpExecutor ? httpExecutor = new HttpRequestExecutor()
				: httpExecutor;
	}

	public void setHttpExecutor(HttpRequestExecutor httpExecutor) {
		this.httpExecutor = httpExecutor;
	}

	public HttpProcessor getHttpproc() {
		if (null == httpproc) {
			httpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
					// Required protocol interceptors
					new RequestContent(), new RequestTargetHost(),
					// Recommended protocol interceptors
					new RequestConnControl(), new RequestUserAgent(),
					new RequestExpectContinue() });
		}
		return httpproc;
	}

	void setHttpproc(HttpProcessor httpproc) {
		this.httpproc = httpproc;
	}

	public HttpParams getParams() {
		return params;
	}

	public String getHostName() {
		return hostName;
	}

	public int getPort() {
		return port;
	}

	public HttpHost getHost() {
		return host;
	}

	public HttpClientPreferencesSender(final String hostName, int port)
			throws Exception {
		params = new SyncBasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");
		HttpProtocolParams.setUserAgent(params, "myclokServer/1.1");
		HttpProtocolParams.setUseExpectContinue(params, true);
		if (null == hostName)
			throw new Exception("Host Name must be specifyed");
		this.hostName = hostName;
		this.port = port;
		this.host = new HttpHost(hostName, 80);
	}

	public ConnectionReuseStrategy getConnStrategy() {
		if (null == connStrategy) {
			connStrategy = new DefaultConnectionReuseStrategy();
		}
		return connStrategy;
	}

	public DefaultHttpClientConnection getHttpDefConn() {
		if (null == httpDefConn) {
			httpDefConn = new DefaultHttpClientConnection();
		}
		return httpDefConn;
	}

	public HttpContext getContext() {
		if (null == context) {
			context = new BasicHttpContext(null);
			context.setAttribute(ExecutionContext.HTTP_CONNECTION,
					getHttpDefConn());
			context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, getHost());
		}
		return context;
	}

	public String executeRequest(final String target){
		if(null == target)
			return null;
		
		String[] targets = {target};
		List<HttpResponse> responces = null;
		
		try {
			
			responces = executeRequest(targets);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			
//			String strRepresentation = responces.get(0).getEntity().getContent().toString();
			HttpEntity entity = responces.get(0).getEntity();
			String res = EntityUtils.toString(entity);
			
			return res;
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public List<HttpResponse> executeRequest(final String[] targets)
			throws Exception {

		List<HttpResponse> resultList = null;

		try {
			resultList = new ArrayList<HttpResponse>();
			for (int i = 0; i < targets.length; i++) {
				if (!getHttpDefConn().isOpen()) {
					Socket socket = new Socket(getHost().getHostName(),
							getHost().getPort());
					
					getHttpDefConn().bind(socket, getParams());
				}
				BasicHttpRequest request = new BasicHttpRequest("GET",
						targets[i]);
				// System.out.println(">> Request URI: " +
				// request.getRequestLine().getUri());

				request.setParams(getParams());
				getHttpExecutor().preProcess(request, getHttpproc(),
						getContext());
				
				HttpResponse response = getHttpExecutor().execute(request,
						getHttpDefConn(), getContext());
				response.setParams(getParams());
				getHttpExecutor().postProcess(response, getHttpproc(),
						getContext());

				System.out.println("<< Response: " + response.getStatusLine());
				System.out.println(EntityUtils.toString(response.getEntity()));
				System.out.println("==============");
				if (!getConnStrategy().keepAlive(response, getContext())) {
					getHttpDefConn().close();
				} else {
					System.out.println("Connection kept alive...");
				}

				resultList.add(response);
			}

		} catch (Exception e) {
			System.out.println(e);

		} finally {
//			getHttpDefConn().close();
		}

		return resultList;
	}
}