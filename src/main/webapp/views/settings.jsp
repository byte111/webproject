<%@page import="com.deva.webproj.jaxb.Userprofile"%>
<%@page import="com.deva.webproj.vo.UserMO"%>
<%@page import="com.deva.webproj.jaxb.Usercredentials"%>
<%@page import="com.deva.webproj.constants.WebprojectConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Settings</title>
<script type="text/javascript">


</script>
</head>

<body>
	<div align="center">
		<b>User Profile Settings</b>
	</div>
	<!-- <div id="leftmenu">
		<jsp:include page="settingsleftmenu.jsp"></jsp:include>
	</div>
	
	<div id="leftmenu1dets" class="" style="dispaly:none">

	</div>
	
	<div id="leftmenu2dets"  hidden = "true">
		Profile div 
	</div>
	
	<div id="leftmenu3dets" hidden = "true">
		Password div
	</div>
	 -->
	<form name="updatedata" method="post" action="/updateprofile">
		<table>
			<tr>
				<td>User Id :</td>
				<td><%=((Userprofile) session.getAttribute(WebprojectConstants.LOGGEDUSERPROF)).getCompid()%></td>
			</tr>
			<tr>
				<td>User Name :</td>
				<td><input type="text"
					value="<%=((Userprofile) session.getAttribute(WebprojectConstants.LOGGEDUSERPROF)).getCompname()%>" /></td>
			</tr>

			<tr>
				<td>Address :</td>
				<td><input type="text"
					value="<%=((Userprofile) session.getAttribute(WebprojectConstants.LOGGEDUSERPROF)).getAddress()%>" /></td>
			</tr>

			<tr>
				<td>Retention Period :</td>
				<td><input type="text" id="retention"
					value="<%=((Userprofile)session.getAttribute(WebprojectConstants.LOGGEDUSERPROF)).getRetention()%>"></td>
			</tr>
			<tr>
				<td><input type="button" id="updtbtn" value="Update" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
