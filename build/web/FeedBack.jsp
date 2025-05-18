<%-- 
    Document   : FeedBack
    Created on : 18 May 2025, 20:27:48
    Author     : Chathura
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="DAO.dbdao" %>

<!DOCTYPE html>
<html>
<head>
    <title>Give Feedback</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }
        button { font-size: 3rem; background: none; border: none; cursor: pointer; margin: 0 15px; }
        button:hover { transform: scale(1.2); }
        .emoji-label { font-weight: bold; font-size: 1rem; }
    </style>
</head>
<body>

<h2>Rate Your Experience</h2>

<div>
    <form id="feedbackForm" action="FeedbackServlet" method="post">
        <button type="submit" name="rating" value="5" title="Best">😍<br/><span class="emoji-label">Best</span></button>
        <button type="submit" name="rating" value="3" title="Good">😊<br/><span class="emoji-label">Good</span></button>
        <button type="submit" name="rating" value="1" title="Sad">😞<br/><span class="emoji-label">Sad</span></button>
    </form>
</div>

</body>
</html>
