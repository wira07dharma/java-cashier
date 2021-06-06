<%-- 
    Document   : check_sales
    Created on : Aug 20, 2015, 2:29:01 PM
    Author     : dimata005
--%>

<%@ page import="com.dimata.cashierweb.entity.admin.AppObjInfo"%>
<%@ page import="com.dimata.qdep.system.I_SystemInfo"%>

<%
if(salesLog==null) {
	response.sendRedirect(approot + "/index.html");
}
%>

