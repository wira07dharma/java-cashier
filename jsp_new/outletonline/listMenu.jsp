<%-- 
    Document   : listMenu
    Created on : Sep 28, 2015, 2:55:37 PM
    Author     : dimata005
--%>
<%@page import="com.dimata.common.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.PstMaterial"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
        String standartRate = PstSystemProperty.getValueByName("ID_STANDART_RATE");
        String priceType = PstSystemProperty.getValueByName("PRICE_TYPE_SHOPPING_CHART");
        Hashtable searcName = PstMaterial.getMaterialRequestSearchByHash(0, 0, "","", standartRate, priceType);
	String query = (String)request.getParameter("q");
	response.setHeader("Content-Type", "text/html");
	int cnt=1;
	for(int i=0;i<searcName.size();i++)
	{
		if(searcName.get(""+i).toString().toUpperCase().startsWith(query.toUpperCase()))
		{
			out.print((String)searcName.get(""+i) +"\n");
			if(cnt>=10)
				break;
			cnt++;
		}
	}
%>