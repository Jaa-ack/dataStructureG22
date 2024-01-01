<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta charset="utf-8">
    <style type = "text/css">

            #search{ 
                opacity: 0.80;
                background: #7DBEB4;
                border:2px black solid;
                text-align: left;
                font-size: 28px;
                padding: 6px;
                height: 60px;
                width: 400px;
                font-weight: bold;
                padding-left: 10px;
                position: absolute;
                left:50%;
                top: 40%;
                margin: -16px 0 0 -240px;
                
            }
            
            #searchButton{
                opacity: 0.80;
                -webkit-text-fill-color:black;
                background:#739DB0;
                border: 2px black solid;
                text-align: center;
                font-size: 24px;
                padding: 6px;
                height: 60px;
                width: 100px;
                position: absolute;
                left:50%;
                top: 40%;
                margin: -16px 0 0 160px;

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

        <div id = "searchArea">
            <input id="search" type="search" ></input>
            <input id="searchButton" type="button" value="搜尋" class="input_box" onclick="search()">
        </div>

    </body>

    <script type="text/javascript">
        function search(){

            var text = "";
            var text = document.getElementById('search').value;

            if(text !== ""){
                location.href=("resultPage.html");
             }else{
               alert('請輸入關鍵字!');
            }

        }
    </script>
    
</html>