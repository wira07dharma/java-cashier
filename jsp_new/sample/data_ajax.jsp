<%-- 
    Document   : data_ajax
    Created on : Aug 31, 2015, 5:16:47 PM
    Author     : dimata005
--%>

<%@page import="com.dimata.common.entity.location.Location"%>
<%@page import="com.dimata.common.entity.location.PstLocation"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%!
    public String drawList(Vector objectClass){
        //2
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); 
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("Name", "20%");
        ctrlist.addHeader("Address", "20%");
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        ctrlist.reset();
        int index = -1;
        Vector rowx = new Vector(1, 1);
        for (int i = 0; i < objectClass.size(); i++) {
            Location location = (Location) objectClass.get(i);
	    int no = i+1;
            rowx = new Vector(1, 1);
            rowx.add(" <a href=\"#\" class=selected data-options='{"
                    + "\"name\":\""+location.getName()+"\"}\' "
                    + ">"+location.getName()+"</a>");
            rowx.add(" <a href=\"#\" class=selected data-options='{"
                    + "\"name\":\""+location.getName()+"\"}\' "
                    + ">"+location.getAddress()+"</a>");
            lstData.add(rowx);
        }
        return ctrlist.drawBootstrapStripted();
    }
%>
<%

int pageShow = FRMQueryString.requestInt(request, "pageShow");
String searchType = FRMQueryString.requestString(request, "searchType");
String name = FRMQueryString.requestString(request, "studentName");
String where="";
switch(pageShow){
    case 1:
        if(!name.equals("")){
            where = PstLocation.fieldNames[PstLocation.FLD_NAME]+" like '%"+name+"%'";
        }
       Vector vLoc = PstLocation.list(0, 0,where, "");
       if(searchType.equals("parent")){
           %>
            <div class="row">
                <div class="col-md-12">
                    <form id="searchList" name="searchList">
                        <input type="hidden" name="searchType" value="child">
                        <input type="hidden" name="pageShow" value="1">
                        <div class="col-md-1">
                            <h5><b>Name</b></h5>
                        </div>
                        <div class="col-md-4">
                            <input type="text" name="studentName" value="<%=name%>" class="form-control">
                        </div>
                        <div class="input-group-btn">
                            <button class="search btn btn-flat" type="submit"  class="btn btn-primary" id="searchBtn"><i class="glyphicon glyphicon-search"></i></button>
                        </div>
                    </form>
                </div>
            </div>
           <%
       }%>
           <div class="col-md-12">
                <div class="row" id="resultSearch">
                    <%=drawList(vLoc)%>
                </div>
            </div>
       <% 
       break;
    case 2 :
        %>
        <div class="col-md-12">
                <div class="row" >
                    Lokasi : <%=name%>
                </div>
        </div>
        <%
        break;
    default:
        break;
}
%>
