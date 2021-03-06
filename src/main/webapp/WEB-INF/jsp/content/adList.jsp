<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
		<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/all.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pop.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.page.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery.page.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/content/adList.js"></script>
	</head>
	<body style="background: #e1e9eb;">
		<form action="${pageContext.request.contextPath}/ad/search" id="mainForm" method="post">
			<input type="hidden" id="id" name="id"/>
			<input type="hidden" id="message" value="${pageCode.msg}"/>
			<input type="hidden" id="basePath" value="${pageContext.request.contextPath}"/>
			<input type="hidden" name="page.currentPage" id="currentPage" value="1"/>
			<div class="right">
				<div class="current">当前位置：<a href="#">内容管理</a> &gt; 广告管理</div>
				<div class="rightCont">
					<p class="g_title fix">广告列表</p>
					<table class="tab1" >
						<tbody>
							<tr>
								<td align="right" width="80">标题：</td>
								<td>
									<input name="title" id="title" value="" class="allInput" type="text"/>
								</td>
	                            <td style="text-align: right;" width="150">
	                            	<input class="tabSub" value="查询" onclick="search();"  type="button"/>&nbsp;&nbsp;&nbsp;&nbsp;

	                            		<input class="tabSub" value="添加" onclick="location.href='${pageContext.request.contextPath}/ad/addInit'" type="button"/>

	                            </td>
	       					</tr>
						</tbody>
					</table>
					<div class="zixun fix">
						<table class="tab2" width="100%" id="tab">
							<tbody>
								<tr>
								    <th>序号</th>
								    <th>标题</th>
								    <th>链接地址</th>
								    <th>操作</th>
								</tr>
								<c:forEach items="${list}" var="item" varStatus="s">
									<tr>
										<td>${s.index + 1}</td>
										<td>${item.title}</td>
										<td>${item.link}</td>
										<td>

												<a href="javascript:void(0);" onclick="modifyInit('${item.id}')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;


												<a href="javascript:void(0);" onclick="remove('${item.id}')">删除</a>

										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
						<!-- 分页 -->
						<div  id="page">

						</div>
					</div>
				</div>
			</div>
		</form>
		<script type="text/javascript">
            $(function(){
                $("#page").Page({
                    totalPages: ${pageinfo.pages},//分页总数
                    liNums: ${pageinfo.pageSize},//分页的数字按钮数(建议取奇数)
                    activeClass: 'activP', //active 类样式定义
                    callBack : function(page){
                        $.ajax({
                            type: 'get',
                            url: "${pageContext.request.contextPath}/ad/getadlist/"+page,
                            dataType: "json",
                            success: function (data) {
                                var tbody=window.document.getElementById("tab");
                                <!-- 清空表格-->
                                $("#tab   tr:not(:first)").html("");
                                var str = "";
                                for (var i in data){

                                    str+="<tr>"+
                                        "<td>"+parseInt(parseInt(i)+parseInt(1))+"</td>"+
                                    	"<td>"+data[i].title+"</td>"+
                                        " <td>"+data[i].link+"</td>"+
                                        "<td>"+
                                        "<a href=javascript:void(0);onclick=modifyInit("+data[i].id+")>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
                                		"<a href=javascript:void(0);onclick=remove("+data[i].id+")>删除</a>"+
                                        "</td>"+
										"</tr>"
								}
                                tbody.innerHTML += str;
                            },
							error:function (data) {
								
                            }
                        });
                    }
                });
            })
//
		</script>
	</body>

</html>