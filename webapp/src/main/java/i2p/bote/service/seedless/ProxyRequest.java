/*
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                    Version 2, December 2004
 *
 * Copyright (C) sponge
 *   Planet Earth
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long
 * as the name is changed.
 *
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *  0. You just DO WHAT THE FUCK YOU WANT TO.
 *
 * See...
 *
 *	http://sam.zoy.org/wtfpl/
 *	and
 *	http://en.wikipedia.org/wiki/WTFPL
 *
 * ...for any additional details and license questions.
 */
package i2p.bote.service.seedless;

import net.i2p.util.Log;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author sponge
 */
class ProxyRequest {
    private Log log = new Log(ProxyRequest.class);

    /**
     * Instance
     */
    ProxyRequest() {
    }

    /**
     * This function makes an HTTP GET request of the specified URL using a proxy if provided.
     * If successful, the HTTPURLConnection is returned.
     *
     * @param strURL A string representing the URL to request, eg, "http://sponge.i2p/"
     * @param header the X-Seedless: header to add to the request.
     * @return HTTPURLConnection or null
     */
    HttpURLConnection doURLRequest(String strURL, String header, String pass) {
        try {
            URL url;
            // System.out.println("HTTP Request: " + strURL);
            url = new URL(strURL);
            if(pass != null) {
                final String login = "admin";
                final String password = pass;

                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password.toCharArray());
                    }
                });
            }

            URLConnection c = url.openConnection();
            c.setUseCaches(false);
            c.setConnectTimeout(1000 * 45); // Eepproxy will time out in 1 minute
            if(header != null) {
                c.setRequestProperty("X-Seedless", header);
            }
            if(c instanceof HttpURLConnection) {
                // instanceof returns true only if the object is not null.
                HttpURLConnection h = (HttpURLConnection) c;
                h.connect();
                return h;
            }
            return null;
        } catch(IOException ex) {
            log.debug("Can't get URL \"" + strURL + "\"", ex);
        }
        return null;
    }
}