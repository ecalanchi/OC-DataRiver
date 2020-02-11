/**
 * 
 */
package org.akaza.openclinica.web.filter;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.akaza.openclinica.dao.core.CoreResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;

/**
 * @author Enrico
 *
 */
public class OpenClinicaLoginUrlAuthenticationEntryPoint extends
		LoginUrlAuthenticationEntryPoint {
	
	private PortMapper portMapper = new PortMapperImpl();
    private PortResolver portResolver = new PortResolverImpl();
    private boolean forceHttps = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenClinicaLoginUrlAuthenticationEntryPoint.class);
    

	/* 
	 * Extended by DataRiver to avoid UNVALIDATED REDIRECTS
	 * VAPT Cryptonet Labs 2020-02-04
	 */
	@Override
	   protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response,
	            AuthenticationException authException) {

	        String loginForm = determineUrlToUseForThisRequest(request, response, authException);
	        int serverPort = portResolver.getServerPort(request);
	        String scheme = request.getScheme();

	        RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();

	        String urlHost = null;
			try {
				urlHost = new URL(CoreResources.getField("sysURL.base")).getHost();
			} catch (MalformedURLException e) {
				urlHost=request.getServerName();
				LOGGER.warn("SysURL empty in datainfo.properties");
			}
			System.out.println("##### "+urlHost);
	        
	        urlBuilder.setScheme(scheme);
//	        urlBuilder.setServerName(request.getServerName());
	        urlBuilder.setServerName(urlHost);
	        urlBuilder.setPort(serverPort);
	        urlBuilder.setContextPath(request.getContextPath());
	        urlBuilder.setPathInfo(loginForm);

	        if (forceHttps && "http".equals(scheme)) {
	            Integer httpsPort = portMapper.lookupHttpsPort(new Integer(serverPort));

	            if (httpsPort != null) {
	                // Overwrite scheme and port in the redirect URL
	                urlBuilder.setScheme("https");
	                urlBuilder.setPort(httpsPort.intValue());
	            } else {
	                //logger.warn("Unable to redirect to HTTPS as no port mapping found for HTTP port " + serverPort);
	            }
	        }

	        System.out.println("##### "+urlBuilder.getUrl());
	        return urlBuilder.getUrl();
	    }
	
}
