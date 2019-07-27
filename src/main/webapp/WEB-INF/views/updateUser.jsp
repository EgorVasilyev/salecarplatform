<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update ad</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        <%@include file="/WEB-INF/js/functions.js"%>
    </script>
    <style>
        blockquote {
            background: url(https://www.axa.de/site/axa-de/get/params_E-35817624/8222246/geparkte-autos-in-reihe.jpg.pagespeed.ce.7CTHh14teL.jpg); /* Фоновый цвет и фоновый рисунок*/
            background-repeat: no-repeat;
            background-size: 100%;
            color: white;
        }
    </style>
</head>
<body>
<c:if test="${activeUser.role=='admin' || (activeUser.role=='user' && activeUser.id == user.id)}">
    <blockquote>
        <h6 align="right">
            <form>
                <span class="glyphicon glyphicon-user"></span>&ensp;<c:out value="${activeUser.login} "></c:out>
                , you are on the page of user profile updating
            </form>
            <form action='${pageContext.servletContext.contextPath}/ads' method="get">
                <input type='hidden' name='action' value='showAds'/>
                <button type="submit" class="btn-info">
                    <span class="glyphicon glyphicon-list"></span> Show all ads
                </button>
            </form>
            <form action='${pageContext.servletContext.contextPath}/ads' method="get">
                <input type='hidden' name='action' value='goToUserAds'/>
                <input type='hidden' name='userId' value='${activeUser.id}'/>
                <button type="submit" class="btn-info">
                    <span class="glyphicon glyphicon-list"></span> Show my ads
                </button>
            </form>
            <c:if test="${activeUser.role=='user'}">
                <div class="form-group">
                    <form action='${pageContext.servletContext.contextPath}/users' method='post'>
                        <input type='hidden' name='action' value='deleteFromProfile'>
                        <input type='hidden' name='id' value='${user.id}'/>
                        <button type="submit" class="btn-danger">
                            <span class="glyphicon glyphicon-remove"></span> Delete my profile and all my ads
                        </button>
                    </form>
                </div>
            </c:if>
            <form action='${pageContext.servletContext.contextPath}/signIn' method="get">
                <button type="submit" class="btn-danger">
                    <span class="glyphicon glyphicon-log-out"></span> Exit
                </button>
            </form>
        </h6>
    </blockquote>
    <div class="container">
        <form class="form-horizontal" action='${pageContext.servletContext.contextPath}/users' method='post'>
            <input type='hidden' name='action' value='update'/>
            <input type='hidden' name='id' value='${user.id}'/>
            <div class="form-group">
                <label class="col-sm-2 control-label">Password:</label>
                <div class="col-sm-4">
                    <input class="form-control" type='password' placeholder='input password' name='password' id='password'
                           value="${user.password}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Phone:</label>
                <div class="col-sm-4">
                    <input class="form-control" type='text' placeholder='input phone' name='phone' id='phone'
                           value="${user.phone}">
                </div>
            </div>
            <c:if test="${activeUser.role=='admin'}">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Role:</label>
                    <div class="col-sm-4">
                        <select class="form-control" name="role">
                            <option value="admin">Admin</option>
                            <option value="user">User</option>
                        </select>
                    </div>
                </div>
            </c:if>
            <c:if test="${activeUser.role=='user'}">
                <input type='hidden' name='role' value='${user.role}'/>
            </c:if>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-4">
                    <button onclick='return checkUser();' type="submit" class="btn-primary">
                        <span class="glyphicon glyphicon-check"></span>  Update
                    </button><br/>
                </div>
            </div>
        </form>
    </div>
</c:if>
<c:if test="${activeUser.role=='guest'}">
    <div class="jumbotron text-center">
        <h2><strong>The guest can not update profile! Please, sign in or sing up.</strong></h2><br/>
        <blockquote>
            <h5>
                <form action='${pageContext.servletContext.contextPath}/signIn' method="get">
                    <button type="submit" class="btn-info">
                        <span class="glyphicon glyphicon-log-in"></span> Sign in / Sign up
                    </button>
                </form>
            </h5>
        </blockquote>
    </div>
</c:if>
</body>
</html>
