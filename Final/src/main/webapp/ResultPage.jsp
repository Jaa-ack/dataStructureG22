<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <meta charset="utf-8">

    <style type = "text/css">
			input[type="text"]{ 
                opacity: 0.80;
                background: #7DBEB4;
                border:2px black solid;
                text-align: left;
                font-size: 23px;
                font-weight: bold;
                padding: 4px;
                padding-left: 10px;
                height: 49px;
                width: 782px;
                position: absolute;
                left:50%;
                top: 7%;
                margin: -16px 0 0 -440px;
                
            }
            
            input[type="submit"]{
                opacity: 0.80;
                -webkit-text-fill-color:black;
                background:#739DB0;
                border: 2px black solid;
                text-align: center;
                font-size: 24px;
                padding: 6px;
                height: 60px;
                width: 80px;
                position: absolute;
                left:50%;
                top: 7%;
                margin: -16px 0 0 360px;

            }

            .results{
                position: absolute;
                left: 50%;
                top:14%;
                margin: 0px 0 0 -440px;
                width: 880px;
                height: 1600px;
                display: flex;
                flex-direction: column;
            }
            
            .results div{
                background-color:#517C8E;
                opacity: 0.80;
                width: 880px;
                height: 144px;
                margin-top: 16px;
                border-radius: 0px;
                display: flex;
                flex-direction: row;
            }

            .results div #booktitle{
                background-color:#517C8E;
                opacity: 0.80;
                width: 700px;
                height: 70px;
                margin-top: 20px;
                border-radius: 0px;
                font-size: 30px;
                margin-left: 30px;
                display: flex;
                flex-direction: column;
                font-weight: bold;
                font-family:Arial, Helvetica, sans-serif;
            }
            .results div #bookinfo{
                background-color:#517C8E;
                opacity: 0.80;
                width: 700px;
                height: 40px;
                margin-top: 5px;
                margin-left: 10%;
                border-radius: 0px;
                font-size: 15px;
                margin-left: 0px;
                font-weight: bold;
                color:black;
            }

            .results div div{
                background-color:#7DBEB4;
                opacity: 0.80;
                height: 144px;
                width: 144px;
                margin-top: 0px;
                border-radius: 0px;
                display: flex;
                flex-direction: row;
                
            }

            
            .results div div img{
                top: 0px;
                bottom: 0px;
                left: 0px;
                right: 0px;
                margin: auto;
            }

            a{
                color: black;
                text-decoration: none;
            }
    </style>

    <body bgcolor="1F2D32">

		<form action="SearchServlet" method="post" onsubmit="return validateForm()">
            <input type="text" id="searchTerm" name="searchTerm" value = "${segments[0]}">
            <input type="submit" value="搜尋">
		</form>

        <div class="results">
			
            <div>
                <div>
                    <img src="images/1.png">
                </div>
                <div id="booktitle">
                    <a href="${segments[1]}"> ${segments[2]}</a>   
                    <div id="bookinfo">
                    相關關鍵字：${segments[3]}
                    </div>
                </div>
            </div>
			
            <div>
                <div>
                    <img src="images/2.png">
                </div>
                <div id="booktitle">
                    <a href="${segments[4]}"> ${segments[5]}</a>   
                    <div id="bookinfo">
                    相關關鍵字：${segments[6]}
                    </div>
                </div>
            </div>
            
            <div>
                <div>
                    <img src="images/3.png">
                </div>
                <div id="booktitle">
                    <a href="${segments[7]}"> ${segments[8]}</a>   
                    <div id="bookinfo">
                    相關關鍵字：${segments[9]}
                    </div>
                </div>
            </div>
            
            <div>
                <div>
                    <img src="images/4.png">
                </div>
                <div id="booktitle">
                    <a href="${segments[10]}"> ${segments[11]}</a>   
                    <div id="bookinfo">
                    相關關鍵字：${segments[12]}
                    </div>
                </div>
            </div>
            
            <div>
                <div>
                    <img src="images/5.png">
                </div>
                <div id="booktitle">
                    <a href="${segments[13]}"> ${segments[14]}</a>   
                    <div id="bookinfo">
                    相關關鍵字：${segments[15]}
                    </div>
                </div>
            </div>

            <div>
                <div>
                    <img src="images/6.png">
                </div>
                <div id="booktitle">
                    <a href="${segments[16]}"> ${segments[17]}</a>   
                    <div id="bookinfo">
                    相關關鍵字：${segments[18]}
                    </div>
                </div>
            </div>

            <div>
                <div>
                    <img src="images/7.png">
                </div>
                <div id="booktitle">
                    <a href="${segments[19]}"> ${segments[20]}</a>   
                    <div id="bookinfo">
                    相關關鍵字：${segments[21]}
                    </div>
                </div>
            </div>

            <div>
                <div>
                    <img src="images/8.png">
                </div>
                <div id="booktitle">
                    <a href="${segments[22]}"> ${segments[23]}</a>   
                    <div id="bookinfo">
                    相關關鍵字：${segments[24]}
                    </div>
                </div>
            </div>

            <div>
                <div>
                    <img src="images/9.png">
                </div>
                <div id="booktitle">
                    <a href="${segments[25]}"> ${segments[26]}</a>   
                    <div id="bookinfo">
                    相關關鍵字：${segments[27]}
                    </div>
                </div>
            </div>

            <div>
                <div>
                    <img src="images/10.png">
                </div>
                <div id="booktitle">
                    <a href="${segments[28]}"> ${segments[29]}</a>   
                    <div id="bookinfo">
                    相關關鍵字：${segments[30]}
                    </div>
                </div>
            </div>
        </div>
        
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
