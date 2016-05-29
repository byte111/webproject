<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
</head>
<script type="text/javascript">

function downloadData()
{
	alert(document.forms.dataDownloadForm.correlationId1.value)
	document.forms.dataDownloadForm.correlationId.value=document.forms.dataDownloadForm.correlationId1.value;
	alert(document.forms.dataDownloadForm.correlationId.value);
//	document.dataDownloadForm.action='dataDownload';
	document.forms.dataDownloadForm.submit();
	
}

$('#listDataTable').find('tr').click( function(){
  var row = $(this).find('td:first').text();
 // $('#correlationId').prop('value',row);
 document.forms.dataDownloadForm.correlationId.value= row;
 document.forms.dataDownloadForm.action="dataDownload";
   alert('You clicked ' + row + 'hidden ='+document.forms.dataDownloadForm.correlationId.value);
  $('#dataDownloadForm').submit();
 
});
</script>
<body>
	
	<table id="listDataTable" style="border : 1px solid black;">
		<tr style="border : 1px solid black;">
				<td style="border : 1px solid black;"><c:out value="Correlation Id" /></td>
				<td style="border : 1px solid black;"><c:out value="Sender Id"></c:out></td>
				<td style="border : 1px solid black;"><c:out value="Sent Date/Time"></c:out></td>
				<td style="border : 1px solid black;"><c:out value="Download"></c:out></td>
		</tr>
		
		<c:forEach var="data" items="${listDataupload}">
			<tr style="border : 1px solid black;">
				<td style="border : 1px solid black;"><c:out value="${data.correlationId}" /></td>
				<td style="border : 1px solid black;"><c:out value="${data.senderId}"></c:out></td>
				<td style="border : 1px solid black;"><c:out value="${data.sentDateTime}"></c:out></td>
				<td style="border : 1px solid black;"><a href="###" >Download</a></td>				
			</tr>
			
		</c:forEach>
		
	</table>
<form id="dataDownloadForm"  method="post">	
	<input name="correlationId" type="hidden" />
</form>
</body>
</html>