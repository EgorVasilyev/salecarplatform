<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Show users</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        <%@include file="/WEB-INF/js/functions.js"%>
    </script>
</head>
<body>
<blockquote>
    <h6 align="right">
        <c:if test="${activeUser.role=='admin'}">
            <form>
                <span class="glyphicon glyphicon-user"></span>&ensp;Hello, <c:out value="${activeUser.login}"></c:out>
            </form>
            <form action='${pageContext.servletContext.contextPath}/users' method="get">
                <input type='hidden' name='action' value='goUpdateUser'/>
                <input type='hidden' name='id' value='${activeUser.id}'/>
                <button type="submit" class="btn-primary">
                    <span class="glyphicon glyphicon-edit"></span> My profile
                </button>
            </form>
            <form action='${pageContext.servletContext.contextPath}/ads' method="get">
                <input type='hidden' name='action' value='goToCreateAd'/>
                <input type='hidden' name='id' value='${activeUser.id}'/>
                <button type="submit" class="btn-primary">
                    <span class="glyphicon glyphicon-plus-sign"></span> Create new ad
                </button>
            </form>
            <form action='${pageContext.servletContext.contextPath}/ads' method="get">
                <input type='hidden' name='action' value='goToUserAds'/>
                <input type='hidden' name='userId' value='${activeUser.id}'/>
                <button type="submit" class="btn-info">
                    <span class="glyphicon glyphicon-list"></span> My ads
                </button>
            </form>
            <form action='${pageContext.servletContext.contextPath}/ads' method="get">
                <input type='hidden' name='action' value='showAds'/>
                <button type="submit" class="btn-info">
                    <span class="glyphicon glyphicon-list"></span> Show all ads
                </button>
            </form>
            <form action='${pageContext.servletContext.contextPath}/signIn' method="get">
                <button type="submit" class="btn-danger">
                    <span class="glyphicon glyphicon-log-out"></span> Exit
                </button>
            </form>
        </c:if>
    </h6>
</blockquote>

<c:if test="${activeUser.role!='admin'}">
    <div class="jumbotron text-center">
        <h2><strong>Only admin can see all users!</strong></h2><br/>
        <blockquote>
            <h5>
                <form action='${pageContext.servletContext.contextPath}/ads' method="get">
                    <input type='hidden' name='action' value='showAds'/>
                    <button type="submit" class="btn-info">
                        <span class="glyphicon glyphicon-list"></span> Show all ads
                    </button>
                </form>
                <c:if test="${activeUser.role=='user'}">
                    <form action='${pageContext.servletContext.contextPath}/signIn' method="get">
                        <button type="submit" class="btn-danger">
                            <span class="glyphicon glyphicon-log-out"></span> Exit
                        </button>
                    </form>
                </c:if>
                <c:if test="${activeUser.role=='guest'}">
                    <form action='${pageContext.servletContext.contextPath}/signIn' method="get">
                        <button type="submit" class="btn-info">
                            <span class="glyphicon glyphicon-log-in"></span> Sign in / Sign up
                        </button>
                    </form>
                </c:if>
            </h5>
        </blockquote>
    </div>
</c:if>
<div class="container">
    <c:if test="${activeUser.role=='admin'}">
        <h3><span class="glyphicon glyphicon-list"></span>&ensp;List of users:</h3>
        <table class="table" id='table'>
            <thead>
            <tr>
                <td width="10%">ID</td>
                <td width="20%">Login</td>
                <td width="20%">Phone</td>
                <td width="20%">Role</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.login}"/></td>
                    <td><c:out value="${user.phone}"/></td>
                    <td><c:out value="${user.role}"/></td>
                    <c:if test="${user.login!='admin' && user.login!='guest'}">
                    <td><form action='${pageContext.servletContext.contextPath}/users' method='get'>
                        <input type='hidden' name='action' value='goUpdateUser'/>
                        <input type='hidden' name='id' value='${user.id}'/>
                        <button type="submit" class="btn-success">
                            <span class="glyphicon glyphicon-pencil"></span> Edit
                        </button>
                    </form></td>
                    <td><form action='${pageContext.servletContext.contextPath}/users' method='post'>
                        <input type='hidden' name='action' value='deleteFromAll'>
                        <input type='hidden' name='id' value='${user.id}'/>
                        <button type="submit" class="btn-danger">
                            <span class="glyphicon glyphicon-remove"></span> Delete user and all his ads
                        </button>
                    </form>
                    </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table><br/>
    </c:if>
</div>
</body>
</html>
