<%--
  Created by IntelliJ IDEA.
  User: liyongjun
  Date: 17/2/2
  Time: 上午8:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/js/jquery-3.1.1.js"></script>
</head>
<body>
<button onclick="getVerison(1)"></button>
</body>
<script>
    function getVerison(kid) {
        $.post("/api/getVersionByKid",{"kid":kid},function (data) {
            var date = new Date(data[0].updateTime);
            Y = date.getFullYear() + '-';
            M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
            D = date.getDate() + ' ';
            console.log(Y+M+D);
        });
    };


</script>
</html>
