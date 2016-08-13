<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Document Interchange System</title>
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
<!--  <link rel="stylesheet" href="/resources/demos/style.css">  --> 
 <style>
    label, input { display:block; }
    input.text { margin-bottom:12px; width:95%; padding: .4em; }
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    div#users-contain { width: 350px; margin: 20px 0; }
    div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
    div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
  </style>
  
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
  
  <script>
  $( function() {
    var dialog, form,
 
      // From http://www.whatwg.org/specs/web-apps/current-work/multipage/states-of-the-type-attribute.html#e-mail-state-%28type=email%29
      emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,
      name = $( "#name" ),
      email = $( "#email" ),
      password = $( "#password" ),
      allFields = $( [] ).add( name ).add( email ).add( password ),
      tips = $( ".validateTips" );
 
    function updateTips( t ) {
      tips
        .text( t )
        .addClass( "ui-state-highlight" );
      setTimeout(function() {
        tips.removeClass( "ui-state-highlight", 1500 );
      }, 500 );
    }
 
    function checkLength( o, n, min, max ) {
      if ( o.val().length > max || o.val().length < min ) {
        o.addClass( "ui-state-error" );
        updateTips( "Length of " + n + " must be between " +
          min + " and " + max + "." );
        return false;
      } else {
        return true;
      }
    }
 
    function checkRegexp( o, regexp, n ) {
      if ( !( regexp.test( o.val() ) ) ) {
        o.addClass( "ui-state-error" );
        updateTips( n );
        return false;
      } else {
        return true;
      }
    }
 
    function addUser() {
      var valid = true;
      allFields.removeClass( "ui-state-error" );
 
     // valid = valid && checkLength( name, "Company Name", 3, 16 );
     // valid = valid && checkLength( email, "email", 6, 80 );
     // valid = valid && checkLength( compid, "Company Id", 6, 16 );
 
     // valid = valid && checkRegexp( name, /^[a-z]([0-9a-z_\s])+$/i, "Username may consist of a-z, 0-9, underscores, spaces and must begin with a letter." );
      //valid = valid && checkRegexp( email, emailRegex, "eg. ui@jquery.com" );
      //valid = valid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );
 
      if ( valid ) {
       /* $( "#users tbody" ).append( "<tr>" +
          "<td>" + name.val() + "</td>" +
          "<td>" + email.val() + "</td>" +
          "<td>" + password.val() + "</td>" +
        "</tr>" );*/
		
		
		document.forms['createuser'].submit();
		
        dialog.dialog( "close" ); 
		
		
      }
      return valid;
    }
 
    dialog = $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 400,
      width: 350,
      modal: true,
      buttons: {
        "Submit": addUser,
        Cancel: function() {
          dialog.dialog( "close" );
        }
      },
      close: function() {
        document.forms['createuser'].reset();
        allFields.removeClass( "ui-state-error" );
      }
    });
 
 
    $( "#create-user" ).button().on( "click", function() {
      dialog.dialog( "open" );
    });
  } );
  </script>
</head>




<body>

	<div
		style="height: 250px; width: 600px; background-color: aqua; position: absolute; left: 500px; top: 100px;"
		align="center">

		<div id="content">
			<span style="font-family: sans-serif; color: #003b6f;">
				<p style="font-size: 30px;">Document Interchange System</p>
			</span>

			<form id="loginform" action="loadHomePage" method="POST">
				<div style="font-family: sans-serif; color: #003b6f; font-size: 15px;">
					<label>Login Id &nbsp;&nbsp;&nbsp;&nbsp;</label>
					<input type="text"
						name="loginId" id="loginId" style="size: 15px;"/> <br>
					<br> <label>Password</label> 
					<input type="password" name="password" id="password"
						style="size: 15px;"/> <br>
					<br> 
						<input type="submit" id="loginbtn" value="Login"/>
						

				</div>
			</form>
			<br/>
			<button id="create-user">Register to login.</button>
		</div>
	</div>



<div id="dialog-form" title="Create new user">
  <p class="validateTips">All form fields are required.</p>
 
  <form id="createuser" action="createuser" method="POST">
      <label for="namelbl">Name</label>
      <input type="text" name="name" id="name" value="" class="text ui-widget-content ui-corner-all">
      <label for="compidlbl">Id</label>
      <input type="text" name="compid" id="compid" value="" class="text ui-widget-content ui-corner-all">
      <label for="addresslbl">Address</label>
      <input type="text" name="address" id="address" value="" class="text ui-widget-content ui-corner-all">
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
   
  </form>
</div>
 


</body>
</html>