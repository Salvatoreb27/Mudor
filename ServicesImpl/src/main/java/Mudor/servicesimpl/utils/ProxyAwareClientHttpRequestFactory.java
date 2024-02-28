package Mudor.servicesimpl.utils;

import org.springframework.http.client.SimpleClientHttpRequestFactory;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;

public class ProxyAwareClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

    private Proxy proxy;

    public ProxyAwareClientHttpRequestFactory(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    protected HttpURLConnection openConnection(URL url, Proxy proxy) throws IOException {
        HttpURLConnection connection = super.openConnection(url, proxy);
        configureProxy(connection);
        return connection;
    }

    private void configureProxy(HttpURLConnection connection) {
        if (proxy != null) {
            String proxyHost = proxy.address().toString();
            connection.setRequestProperty("Proxy", proxyHost);
        }
    }

    public HttpURLConnection openConnection(URI uri, Proxy proxy) throws IOException {
        HttpURLConnection connection = super.openConnection(uri.toURL(), proxy);
        configureProxy(connection);
        return connection;
    }
}
