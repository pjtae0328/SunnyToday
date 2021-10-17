<%@page import="xyz.sunnytoday.dto.Post"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% 
	List<Post> boardShareList = (List) request.getAttribute("boardShareList");
%>

<!doctype html>
<html lang="ko">
<head>
    <%--head meta data--%>
    <c:import url="../layout/head_meta.jsp"/>
    <%--page title--%>
    <title>오늘도 맑음 -전체 카테고리</title>

    <%--페이지별 css/ js--%>
    <link href="${cssPath}/board.css" rel="stylesheet">
    <script src="${jsPath}/board_script.js"></script>

</head>
<body>
<%--header--%>
<c:import url="../layout/header.jsp"/>
<%--navbar--%>
<c:import url="../layout/navbar.jsp"/>

	<div class="How_was_your_day">
		<h2>정보공유</h2>
		혼자만 알기 아까운 정보! 나눠봅시다!
	</div>
	<hr>
	
<div>
<div class="menu-left">
	<div><h2>카테고리</h2></div>
	<div><a href="/board/main">전체 글</a></div>
	<div><a href="/board/list/daily">일상룩</a></div>
	<div><a href="/board/list/buy">지름 게시판</a></div>
	<div><a href="/board/list/share">정보공유</a></div>
	<div><a href="/board/list/asking">질문 응답</a></div>
	<div><a href="/board/list/mine">내가 쓴 글</a></div>
</div>

<section class="main-board">
<div id='full_article'>정보공유</div>
<table>
<thead>
	<tr class="division">
		<th>썸네일</th>
		<th>제목/본문</th>
		<th>작성자</th>
		<th>게시글평점</th>
		<th>날짜</th>
		<th>추천수</th>
	</tr>
</thead>
<tbody>
	<tr>
		<td colspan="6">공지글</td>
	</tr>
	<tr>
		<td colspan="6">공지글</td>
	</tr>
<c:forEach items="${list }" var="boardShareList">
<tr>
	<td rowspan="2"><img class="thumbnail" src="http://via.placeholder.com/40" alt="썸네일"></td>
	<td id='title'>
		<a href="/board/detail?postno=${boardShareList.post.post_no }">
		${boardShareList.post.title }
		</a>
	</td>
	<td rowspan="2">
		<i class="far fa-smile"></i>${boardShareList.nick }
	</td>
	<td rowspan="2">
		<div id='circle-grade'>평점</div>
	</td>
	<td rowspan="2">
		${boardShareList.post.write_date }
	</td>
	<td rowspan="2">추천수</td>
</tr>
<tr>
	<td id='content'>${boardShareList.post.content }</td>
</tr>
</c:forEach>
</tbody>
</table>
</section>
</div>
<div id='paging'>
<c:import url="../layout/boardTitlePaging.jsp" />
</div>
<%--footer--%>
<c:import url="../layout/footer.jsp"/>
</body>
</html>
