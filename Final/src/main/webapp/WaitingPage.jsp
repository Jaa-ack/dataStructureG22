<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta charset="utf-8">

    <style type = "text/css">

        #animate{
            position: absolute;
            height: 300px;
            width: 300px;
            left: 50%;
            top: 30%;
            margin: -150px 0 0 -150px;
            background-image: url(images/a-background.png);
            background-size:contain;
            background-repeat: no-repeat;
            background-position: bottom,center;
            
        }

        #animate div{
            position: absolute;
            height: 50px;
            width: 300px;
            left: 0%;
            top:45%;
            display: flex;
            flex-direction: row;
            overflow: hidden;

        }

        #animate div div{
            height: 50px;
            width: 100px;
            top:0%;
            background-image: url(images/a3.png);
            background-size:contain;
            background-repeat: no-repeat;
            background-position: bottom,center;
            position: relative;
            animation: Move1 5s infinite ;
            transition: ease-in-out;
            
        }

        @keyframes Move1{
            0%{ left:100%;}
            40% {left:30%;}
            60%{left:30%}
            100%{left:-50%;}
        }

    </style>

    <body bgcolor="1F2D32">

        <div id = "animate">
            <div>
                <div></div>
            </div>
        </div>
    <form id="form1" onsubmit=" return validateForm()" action="SearchServlet">
            <input type="text" id="searchTerm" name="searchTerm" value="1">
            <input type="submit" value="搜尋">
		</form>

        <script type="text/javascript">
            var url = location.href ;
            var temp = url.split('=');
            var b = document.querySelector('#searchTerm');
            b.setAttribute('value',temp[1]);
            alert(temp[1]);
            form1.submit();
        </script>
        

    </body>
    <script>
       
    </script>
</html>
