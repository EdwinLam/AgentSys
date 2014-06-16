<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head><title>test</title></head>
	<body>
	<form action="/index.do?action=register" method="post">
		名字:<input type="text" name="name"/>
		账号:<input type="text" name="account"/>
		密码:<input type="text" name="psw"/>
		<input type="submit" id="su" value="提交"/ >
	</form>
	</body>
</html>