package cn.sdp.act.appengine.monitor.client;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.enqu.xml.utils.XMLUtils;
import org.w3c.dom.Document;

public class HttpExchangeClient {

	private static Log logger = LogFactory.getLog(HttpExchangeClient.class);

	private HttpClient client;

	public HttpExchangeClient() {
		System.out.println("HttpExchangeClient  ��ʼ��ʼ��");
		client = new DefaultHttpClient();
		System.out.println("HttpExchangeClient  ��ɳ�ʼ��");
	}

	private HttpEntity createReqEntity(Document doc) {
		HttpEntity entity = new DocEntity(doc);

		return entity;
	}

	/**
	 * return the document for the http respoonse
	 * 
	 * @param req
	 * @param targetUrl
	 * @return
	 * @throws IOException
	 */
	public Document sendGetRequest(Document req, String targetUrl)
			throws IOException {

		// create the get method
		HttpPost post = new HttpPost(targetUrl);

		HttpEntity entity = createReqEntity(req);
		post.setEntity(entity);
		try {
			HttpResponse response = client.execute(post);
			if (response == null) {
				System.out.println("The http response is null");
				return null;
			}
			InputStream inputStream = getRespStream(response);
			// transformer the input stream into the xml document
			Document respDoc = XMLUtils.constructXml(inputStream);
			return respDoc;
		} catch (ClientProtocolException e) {
			logger
					.warn("Can't send out the http request message for the exception: "
							+ e.getMessage());
		} finally {
			// do something clearing, but recently do nothing
		}
		return null;
	}

	private InputStream getRespStream(HttpResponse resp) {
		if (resp == null) {
			logger.warn("The response entity is null");
			return null;
		}

		HttpEntity respEntity = resp.getEntity();
		try {
			return respEntity.getContent();
		} catch (IllegalStateException e) {
			logger.warn(e.getMessage());
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return null;
	}

	public void close() {

	}

}
