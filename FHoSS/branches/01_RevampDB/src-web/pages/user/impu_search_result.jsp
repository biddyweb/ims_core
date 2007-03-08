<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ page import="de.fhg.fokus.hss.util.SecurityPermissions" %>
<jsp:useBean id="resultList" type="java.util.List" scope="request"></jsp:useBean>
<jsp:useBean id="maxPages" type="java.lang.String" scope="request"></jsp:useBean>
<jsp:useBean id="currentPage" type="java.lang.String" scope="request"></jsp:useBean>
<jsp:useBean id="rowPerPage" type="java.lang.String" scope="request"></jsp:useBean>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="/hss.web.console/style/fokus_ngni.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="result.title" /></title>

</head>
<body>

<center>
<table>
<tr>
	<td><br/><br/><h1>Public User Identity - Search Results </h1><br/><br/></td>
</tr>
</table>
</center>

<center>
	<table class="as" width="500">
		<logic:notEmpty name="resultList">
			<tr class="header">
				<td class="header"> ID: </td>
				<td class="header"> <bean:message key="impu.head.id" />: </td>
				<td class="header"> Barring: </td>
				<td class="header"> Implicit-Set: </td>
				<td class="header"> <bean:message key="impu.head.regStatus" />: </td>		
				<td class="header"> Delete: </td>									
			</tr>
				
			<logic:iterate name="resultList" id="impu"
				type="de.fhg.fokus.hss.db.model.IMPU" indexId="ix">
				<tr class="<%= ix.intValue()%2 == 0 ? "even" : "odd" %>">
					<td>
						<bean:write name="impu" property="id" />
					</td>
					<td> 
						<a href="/hss.web.console/IMPU_Load.do?id=<bean:write name="impu" property="id" />"> 
							<bean:write name="impu" property="identity" />
						</a>	
					</td>
					<td>
						<%
							if (impu.getBarring() == 1){
								out.println("true");
							}
							else{
								out.println("false");
							}
						%>
					</td>
					<td>
						<bean:write name="impu" property="id_impu_implicitset" />
					</td>
						
					<td>
						<%
							out.println("statusul");
						%>
					</td>
					<td>
						<form method="post" action="/hss.web.console/IMPU_Delete.do" target="content" style="text-align: center">
							<input type="hidden" name="id" value="<bean:write name="impu" property="id" />"> 
							<input type="image" src="/hss.web.console/images/progress_rem.gif">
						</form>
					</td>
				</tr>
			</logic:iterate>
			
			<%if (Integer.parseInt(maxPages) > 1) {

			%>
			<tr>
				<td colspan="3" class="header"><script type="text/javascript"
					language="JavaScript">
					function submitForm(pageId){
						document.IMPU_SearchForm.page.value = pageId;
						document.IMPU_SearchForm.submit();
					}
				</script> 
				
				<html:form action="/IMPU_Search">
					<table>
						<tr>
							<td>
								<%
									int length = Integer.parseInt(maxPages) + 1;
									int cPage = Integer.parseInt(currentPage) + 1;
									for (int iy = 1; iy < length; iy++) {
										if (cPage != iy) {
									%>
									<a href="javascript:submitForm(<%=String.valueOf(iy)%>);"><%=iy%></a>
								<%
									} else {
									%> 
									<font style="color:#FF0000;font-weight: 600;"> <%=String.valueOf(iy)%>
									</font> 
								<% }
							}
							%>
			</td>
							<td><bean:message key="result.rowsPerPage" /><br>
							<html:hidden property="page"></html:hidden> 
							<html:select property="rowsPerPage" onchange="javascript:document.IMPU_SearchForm.submit();">

							<option value="20"
								<%= rowPerPage.equals("20") ? "selected" : "" %> >20 </option>
							<option value="30"
								<%= rowPerPage.equals("30") ? "selected" : "" %> >30 </option>
							<option value="50"
								<%= rowPerPage.equals("50") ? "selected" : "" %> >50</option>
							<option value="100"
								<%= rowPerPage.equals("100") ? "selected" : "" %> >100</option>
							</html:select></td>
						</tr>
					</table>
				</html:form></td>
			</tr>
			<%}

		%>
		</logic:notEmpty> 
		
		<tr><td>
		<logic:empty name="resultList">
			<bean:message key="result.emptryResultSet" />
		</logic:empty></td>
		</td></tr>
		</table>		
</center>

</body>
</html>