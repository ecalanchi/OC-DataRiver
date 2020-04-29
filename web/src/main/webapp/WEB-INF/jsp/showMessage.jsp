<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.web.util.HtmlUtils" %>
<jsp:useBean scope='request' id='formMessages' class='java.util.HashMap'/>

<%
String key = request.getParameter("key");
boolean hasMessages = false;
if (formMessages.get(key)!=null) {
	ArrayList messages = (ArrayList) formMessages.get(key);
	if (messages !=null) {
	  for (int messagecount = 0; messagecount < messages.size(); messagecount++) {
		hasMessages = true;
        String message = (String) messages.get(messagecount);
        %><div ID="spanAlert-<%=HtmlUtils.htmlEscape(key)%>" class="alert"><%=HtmlUtils.htmlEscape(message)%></div><%
	  }
    }
}

if (hasMessages) {
	%><br /><br /><%
}
%>
