<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Document Interchange System</title>
<style type="text/css">

.display-error
{
   width: 400px;
   border: 1px solid #D8D8D8;
   padding: 5px;
   border-radius: 5px;
   font-family: Arial;
   font-size: 11px;
   text-transform: uppercase;
   background-color: rgb(255, 249, 242);
   color: rgb(211, 0, 0);
   text-align: center;
}


</style>
</head>

<body>
<div class="display-error">
	Exception happened in application. Details : <c:out value="${errormsg}"/>
</div>
</body>
</html>