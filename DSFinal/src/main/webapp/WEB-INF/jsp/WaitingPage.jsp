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
            background-image: url(a-background.png);
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
            background-image: url(a3.png);
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
        

    </body>
</html>