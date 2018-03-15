<%--
  Created by IntelliJ IDEA.
  User: liyongjun
  Date: 17/1/30
  Time: 下午7:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery-1.9.0.min.js"></script>
</head>
<body>
<input type="button" value="click me" onclick="requestJson();"/>
<input type="button" value="click me" onclick="requestPOJO();"/>

</body>
<script>
    var jsonUser = JSON.stringify({"username":"张三","age":"111",address:"武当山"})
    function requestJson(){
        $.ajax({
            type:'post',
            url:'${pageContext.request.contextPath}/requestJson',
            contentType:'application/json;charset=utf-8',
            data:jsonUser,
            success :function(data){
                console.log(data);
            }
        })
    };
    function requestPOJO(){
        $.ajax({
            type:'post',
            url:'${pageContext.request.contextPath}/responseJson',
            data:'username=张三丰&age=123&address=武当山',
            success :function(data){
                console.log(data);
            }
        })
    }


</script>
</html>
