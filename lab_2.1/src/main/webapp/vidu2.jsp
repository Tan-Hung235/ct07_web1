<%@ page contentType="text/html;charset=UTF-8" %>

<%!
    int dem = 0;
%>

<%
    dem++;
    String lop = "CT07";
    String username1="dhgia";
    session.setAttribute("username",username1);
%>

<html>
<body>

<h2>Viết kiểu biểu thức <%= (2+3) %></h2>

<h1>Xin chào <%= lop %></h1>
<h1>Số lượng truy cập: <%= dem %></h1>
<h1>Người đăng nhập: <%=session.getAttribute("username")%> </h1>

</body>
</html>