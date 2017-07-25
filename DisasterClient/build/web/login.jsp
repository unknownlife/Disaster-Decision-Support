<%-- 
    Document   : login
    Created on : 9 Jul, 2017, 7:35:26 PM
    Author     : Shikhar Jain
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        
       <form action="login" method="post" >
<table width="20%" bgcolor="0099CC" align="center">

<tr>
<td colspan=2><center><font size=4><b>Login Page</b></font></center></td>
</tr>

<tr>
<td>Username</td>
<td><input type="text" size=25 name="userId"></td>
</tr>

<tr>
<td>Password:</td>
<td><input type="Password" size=25 name="password"></td>
</tr>

<tr>
<td ><input type="Reset"></td>
<td><input type="submit" value="Login"></td>
</tr>

</table>
       </form>
    </body>
</html>
