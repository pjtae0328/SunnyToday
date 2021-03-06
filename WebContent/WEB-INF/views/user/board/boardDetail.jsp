<%@page import="xyz.sunnytoday.dto.Post"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% 
	Post detailBoard = (Post) request.getAttribute("detailBoard");
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
    <script src="${jsPath}/httpRequest.js"></script>

</head>
<body>
<%--header--%>
<c:import url="../layout/header.jsp"/>
<%--navbar--%>
<c:import url="../layout/navbar.jsp"/>

<div class="How_was_your_day">
	<h2></h2>
	당신의 이야기를 들려주세요 :)
</div>
<hr>

<div class="detailarea">
		<div id="detailTitle">${detailBoard.title }</div>
		<hr>
		<div style="margin-bottom: 40px;">	
		<span id="detailNick">작성자: ${nick }</span>
		<span id="btnlist">	
			<a href="/board/report?postno=${detailBoard.post_no }">
			<button id="btnReport">신고</button>
			</a>
			<c:if test="${loginMember.userno eq detailBoard.user_no }">
			<a href="/board/update?postno=${detailBoard.post_no }">
			<button id="btnUpdate">수정</button>
			</a>
			<a href="/board/delete?postno=${detailBoard.post_no }">
			<button id="btnDelete">삭제</button>
			</a>
			</c:if>
		</span>
		</div>

		<c:if test="${not empty detailFile }">
		<div id="preview" style="text-align: center;">
			<img style="resize: both;"
				src="/upload/${detailFile.url }">
		</div>
		</c:if>
		<div id="detailContent"> ${detailBoard.content } </div>
		
		<div id="likeList">
		<form action="<%=request.getContextPath() %>/board/like" method="post">
			<input type="hidden" name="postno" id="postno" value="${detailBoard.post_no }" />
			<button name="like" value="1" id="like">추천</button>
			<button name="disLike" value="-1" id="disLike">반대</button>
		</form>	
		</div>	

		<button id="btnList">뒤로가기</button>
		<hr>
		
		<form action="<%=request.getContextPath() %>/board/comments/insert" method="get">
			<input type="hidden" name="postno" id="postno" value="${detailBoard.post_no }" />
			<div id="onlyWrite	r">
				<input type="checkbox" name="onlyWriter" id="onlyWriter" value="onlyWriter"> 
				<label for="onlyWriter">작성자만 보기</label>	
			</div>
			<div style="margin: 5px 20px 30px 20px;">
				<span id="commentsWriter">${loginMember.nick }</span>
				<input type="text" name="commentsContent" id="commentsContent" placeholder="댓글을 작성해보세요" />
				<input type="submit" id="btnCommentsInsert" value="댓글입력"/>
			</div>
		</form>
		<hr>
		
		<c:if test="${empty comments }">
		<h3>댓글이 없습니다, 댓글을 작성해보세요!</h3>
		</c:if>
		
		<c:if test="${not empty comments }">
				<table style="width: 100%">
			<c:forEach items="${comments }" var="comments">
					<tr id="CommentsAdd">
					
						<c:if test="${comments.comments.show eq 'Y' and ((detailBoard.user_no eq loginMember.userno)or(comments.comments.user_no eq loginMember.userno))}">
							<td id="commentsNickinTable">
							${comments.member }
								<c:if test="${detailBoard.user_no eq comments.comments.user_no }">
								<i class="fas fa-sun"></i>
								</c:if>
							</td>
							
							<td id="commentsContentinTable_${comments.comments.comments_no }" class="CCT"><i class="fas fa-lock" style="color: #949435"></i>${comments.comments.content }</td>
							
								<c:if test="${loginMember.userno eq comments.comments.user_no }">								
								<td style="width:20%">
									<form action="<%=request.getContextPath() %>/board/comments/update" method="get" style="display: inline;">
									<button type="button" id="CommentsUpdateinTable">수정</button>
									<input type="hidden" name="comments_no" id="updateComments_no" value="${comments.comments.comments_no }" />
									<input type="hidden" name="newComments" />
									</form>
									<form action="<%=request.getContextPath() %>/board/comments/delete" method="get" style="display: inline;">
									<button type="button" id="CommentsDeleteinTable">삭제</button>
									<input type="hidden" name="comments_no" id="deleteComments_no" value="${comments.comments.comments_no }" />
									</form>
								</td>
								</c:if>
								
								<c:if test="${loginMember.userno ne comments.comments.user_no }">
									<td id="CommentsReportinTable">신고</td>
								</c:if>
								
						</c:if>
						
						<c:if test="${comments.comments.show eq 'Y' and  ((detailBoard.user_no ne loginMember.userno)and(comments.comments.user_no ne loginMember.userno))}">
							<td colspan="3" style="height: 40px;">작성자만 볼 수 있는 댓글입니다.</td>
						</c:if>
						
						<c:if test="${comments.comments.show eq 'N' }">
						<td id="commentsNickinTable">
						${comments.member }
							<c:if test="${detailBoard.user_no eq comments.comments.user_no }">
							<i class="fas fa-sun"></i>
							</c:if>
						</td>
						<td id="commentsContentinTable_${comments.comments.comments_no }" class="CCT">${comments.comments.content }</td>
							<c:if test="${loginMember.userno eq comments.comments.user_no }">
							<td style="width:20%">
								<form action="<%=request.getContextPath() %>/board/comments/update" method="get" style="display: inline;">
								<button type="button" id="CommentsUpdateinTable">수정</button>
								<input type="hidden" name="comments_no" id="updateComments_no" value="${comments.comments.comments_no }" />
								<input type="hidden" name="newComments" />
								</form>
								<form action="<%=request.getContextPath() %>/board/comments/delete" method="get" style="display: inline;">
								<button type="button" id="CommentsDeleteinTable">삭제</button>
								<input type="hidden" name="comments_no" id="deleteComments_no" value="${comments.comments.comments_no }" />
								</form>
							</td>
							</c:if>
							
							<c:if test="${loginMember.userno ne comments.comments.user_no }">
							<td id="CommentsReportinTable">
								<form action="<%=request.getContextPath() %>/board/report" method="get">
								<input type="hidden" name="postno" id="postno" value="${detailBoard.post_no }" />
								<input type="hidden" name="comments_no" value="${comments.comments.comments_no }" />
								<button>신고</button>
								</form>
							</td>
							</c:if>
					
						</c:if>
						
					</tr>		
			</c:forEach>
				</table>
		</c:if>
		
		
	
</div>


<%--footer--%>
<c:import url="../layout/footer.jsp"/>
</body>
</html>
