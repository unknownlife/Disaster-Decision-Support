<%-- 
    Document   : success
    Created on : 10 Jul, 2017, 10:38:32 AM
    Author     : Shikhar Jain
--%>

<%@page import="dto.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DisasterDecisionSupport</title>
        
        <link rel="stylesheet" href="pop-upstyle.css" type="text/css">


        <script type="text/javascript" src="pop-up.js"></script>
        
        <style type="text/css"> 
<!-- 
 
 #navbar ul { 
	margin: 0; 
	padding: 5px; 
	list-style-type: none; 
	text-align: center; 
	background-color: #111; 
	} 
 
#navbar ul li {  
	display: inline; 
	} 
 
#navbar ul li a { 
	text-decoration: none; 
	padding: .2em 1em; 
	color: #fff; 
	background-color: #000; 
	} 

#navbar ul li a:hover { 
	color: #000; 
	background-color: #4CAF50; 
	} 
 

</style>
    </head>
    <body>
        
        <div id="navbar"> 
  <ul> 
	<li><a href="index_1.html">visualization</a></li> 
	<li><a href="#/home.html">search key</a></li> 
	<li><a href="#/home.html"> N to S Query</a></li> 
	<li><a href="index_2.html">attack ontology</a></li>
	<li><a href="#/home.html"> Emergency Contacts</a></li>
	<li><a href="index_2.html"> list of case</a></li>
	<li><a href="#/home.html"> sparql query</a></li>
	 
	<!--  below code display attached website -->
	<!-- <li><a href="http://github.com">Link 5</a></li> -->
  </ul> 
</div>
        
        <h3>login Successful!</h3>
        
        <%
           /* User user = (User) request.getAttribute("user"); */
        %> 
        
        <jsp:useBean id="user" class="dto.User" scope="request" ></jsp:useBean>
        
        
        Hello <%= user.getUserName() %>
        <br>
        <br>
        <div class="popcontent td-center">
            <a href="javascript:popmeup('pop-up.htm');"><h3>Emergency Situtation</h3></a>
        </div>
    </body>
</html>
