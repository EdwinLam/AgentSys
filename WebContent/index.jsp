<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head><title>test</title></head>
	<body>
		<c:forEach items="${agCpProduct}" var="item">  
       		 ${item.id }--${item.name }--<img src="${item.imgUrl}"/>--${item.introduce}--${item.defaultPackageId }<br />
		</c:forEach>
		 <a href="/index.do?action=registerinit">注册请点击这里</a>
	</body>
</html>