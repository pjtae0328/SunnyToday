<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="text-center">
	<ul class="pagination pagination-sm">
		
		<%-- 첫페이지로 이동 --%>
		<c:if test="${paging.curPage ne 1 }">
			<li><a href="<%=request.getContextPath() %>/admin/message">&larr;처음</a></li>
		</c:if>
		
		<%-- 이전페이징 리스트로 이동 --%>
		<c:choose>
			<c:when test="${paging.startPage ne 1 }">
				<li><a href="<%=request.getContextPath() %>/admin//message?curPage=${paging.startPage - paging.pageCount }">&laquo;</a></li>
			</c:when>
			
			<c:when test="${paging.startPage eq 1 }">
				<li><a>&laquo;</a></li>
			</c:when>
		</c:choose>
		
		<%-- 이전 페이지로 가기 --%>
		<c:if test="${paging.curPage > 1 }">
			<li><a href="<%=request.getContextPath() %>/admin/message?curPage=${paging.curPage - 1 }">&lt;</a></li>
		</c:if>
		
		<%-- 페이징 리스트 --%>
		<c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="i">
		<c:if test="${paging.curPage eq i }">
			<li class="active"><a href="<%=request.getContextPath() %>/admin/message?curPage=${i }">${i }</a></li>
		</c:if>
		<c:if test="${paging.curPage ne i }">
			<li><a href="<%=request.getContextPath() %>/admin/message?curPage=${i }">${i }</a></li>
		</c:if>
		</c:forEach>
		
		<%-- 다음 페이지로 가기 --%>
		<c:if test="${paging.curPage < paging.totalPage }">
			<li><a href="<%=request.getContextPath() %>/admin/message?curPage=${paging.curPage + 1 }">&gt;</a></li>
		</c:if>
		
		<%-- 다음페이징 리스트로 이동 --%>
		<c:choose>
			<c:when test="${paging.endPage ne paging.totalPage }">
				<li><a href="<%=request.getContextPath() %>/admin/message?curPage=${paging.startPage + paging.pageCount }">&raquo;</a></li>
			</c:when>
			
			<c:when test="${paging.endPage eq paging.totalPage }">
				<li><a>&raquo;</a></li>
			</c:when>
		</c:choose>
		
		<%-- 끝페이지로 이동 --%>
		<c:if test="${paging.curPage ne paging.totalPage }">
			<li><a href="<%=request.getContextPath() %>/admin/message?curPage=${paging.totalPage }">끝&rarr;</a></li>
		</c:if>
	</ul>
</div>