<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>dashboard</title>
</head>
<style>
.content
{
	background-color: aqua;
	font-family: sans-serif;
	font-size: small;
	border: 1px solid black;
}
</style>
<body>

<p>Send Invoice Documents</p>
<form action="UploadMessage" method="post"
                        enctype="multipart/form-data">
<div id="content" class="content">

<p><span>Upload Message</span></p>
<br/>

<select name="receiverList">

	<option value="BLRDEV02" selected >BLRDEV02</option>
	<option value="BLRDEV02">BLRDEV03</option>
	<option value="BLRDEV02">BLRDEV03</option>
	
</select>
<br/><br/>
<input type="file" name="dataFile" id="fileChooser" size="50" />

<input type="submit" value="Upload File" />

</div>
</form>
</body>
</html>