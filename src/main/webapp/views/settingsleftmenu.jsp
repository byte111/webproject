<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<style>



.lefttabs
{
	 border: 1px solid black;
    display: inline-block;
    box-sizing:border-box;
    -moz-box-sizing:border-box;
    -webkit-box-sizing:border-box;
	margin: 2px;
	padding: 10px;
	width: 200px;
	background: #b5b2b2;
}


</style>

<script>
$(document).ready(function() {
	
///	$('#leftmenu3dets').hide();
//	$('#leftmenu2dets').hide();
//	$('#leftmenu1dets').show();	
    
});

	$('#leftmenu1').click(function(e) 
	    {  
	    	$('#leftmenu3dets').hide();
	    	$('#leftmenu2dets').hide();
	    	$('#leftmenu1dets').show();	
	    });
	    
	    $('#leftmenu2').click(function(e)
	    {
	    	$('#leftmenu3dets').hide();
	    	$('#leftmenu2dets').show();
	    	$('#leftmenu1dets').hide();	
	    });
	    
	    
	    $('#leftmenu3').click(function(e)
	    {
	    	    	$('#leftmenu3dets').show();
	    	    	$('#leftmenu2dets').hide();
	    	    	$('#leftmenu1dets').hide();	
	    });
  </script>

<div id="">
	<div id="leftmenu1" class="lefttabs">
		Update Update Profile
	</div>
	<br>
	<div id="leftmenu2" class="lefttabs">
		
	</div>
	<br>
	<div id="leftmenu3" class="lefttabs">
		Change Password
	</div>


</div>
