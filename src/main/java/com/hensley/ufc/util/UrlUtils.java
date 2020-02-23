package com.hensley.ufc.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.hensley.ufc.pojo.response.UrlParseRequest;

@Service
public class UrlUtils {

	private Logger LOG = Logger.getLogger(UrlUtils.class.toString());


	public UrlParseRequest parse(String baseUrl) {
		HtmlPage page = null;

		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);

		try {
			page = client.getPage(baseUrl);
		} catch (FailingHttpStatusCodeException e) {
			LOG.log(Level.WARNING, String.format("%s url parsing error: %s", FailingHttpStatusCodeException.class, e.getLocalizedMessage()));
			client.close();
			return new UrlParseRequest(page, e.getLocalizedMessage(), false);
		} catch (MalformedURLException e) {
			LOG.log(Level.WARNING, String.format("%s url parsing error: %s", MalformedURLException.class, e.getLocalizedMessage()));
			client.close();
			return new UrlParseRequest(page, e.getLocalizedMessage(), false);
		} catch (IOException e) {
			LOG.log(Level.WARNING, String.format("%s url parsing error: %s", IOException.class, e.getLocalizedMessage()));
			client.close();
			return new UrlParseRequest(page, e.getLocalizedMessage(), false);
		}
		// System.out.println(page.asXml());
		client.close();
		return new UrlParseRequest(page, null, true);
	}

};