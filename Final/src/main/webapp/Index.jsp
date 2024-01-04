<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta charset="utf-8">
    <style type = "text/css">

	    form{

                opacity: 0.80;
                display: flex;
                flex-direction: row;
                height: 60px;
                width: 500px;
                left:50%;
                top: 40%;
                margin: -16px 0 0 -240px;
                position: absolute;

            }
		
            input[type="text"]{ 
                background: #7DBEB4;
                border:2px black solid;
                text-align: left;
                font-size: 28px;
                padding: 6px;
                height: 55px;
                width: 400px;
                font-weight: bold;
                padding-left: 10px;
                
            }
            
            input[type="submit"]{
                background:#739DB0;
                border: 2px black solid;
                text-align: center;
                font-size: 24px;
                padding: 6px;
                height: 60px;
                width: 100px;
            }
            
            body{
                background-image: url(images/background.png);
                background-size:contain;
                background-repeat: no-repeat;
                background-attachment: fixed;
                background-position: bottom,center;
                

            }
    </style>

    <body bgcolor="1F2D32">

        <form action="SearchServlet" method="post" onsubmit="return validateForm()">
            <input type="text" id="searchTerm" name="searchTerm">
            <input type="submit" value="搜尋">
		</form>

    </body>

    <script>
		function validateForm() {

            var searchTerm = document.getElementById("searchTerm").value;
            
            if(searchTerm.trim() === ""){
            	alert('請輸入關鍵字!');
                return false;
            }
            return true;

        }
    </script>
    
</html>
