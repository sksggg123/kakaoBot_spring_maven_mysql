<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<div class="blog_area" name="linkList" style="margin: 10%">
	<c:forEach items="${result}" var="list">
			<a> ${list} </a> <br/>
    </c:forEach>
</div>

<script type="text/javascript">
	
</script>