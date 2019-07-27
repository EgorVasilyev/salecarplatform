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
        .table {
            table-layout: fixed;
        }
        td {
            word-wrap:break-word;
        }
        blockquote {
            background: url(https://www.axa.de/site/axa-de/get/params_E-35817624/8222246/geparkte-autos-in-reihe.jpg.pagespeed.ce.7CTHh14teL.jpg); /* Фоновый цвет и фоновый рисунок*/
            background-repeat: no-repeat;
            background-size: 100%;
            color: white;
        }
    </style>
</head>
<body>
<c:if test="${activeUser.role=='admin' || activeUser.role=='user'}">
    <blockquote>
        <h6 align="right">
            <form>
                <span class="glyphicon glyphicon-user"></span>&ensp;<c:out value="${activeUser.login} "></c:out>
                , you are on the page of ad updating
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
            <form action='${pageContext.servletContext.contextPath}/signIn' method="get">
                <button type="submit" class="btn-danger">
                    <span class="glyphicon glyphicon-log-out"></span> Exit
                </button>
            </form>
            <br/><br/><br/><br/>
        </h6>
    </blockquote>
    <div class="container">
        <form class="form-horizontal" action='${pageContext.servletContext.contextPath}/ads' method='post'
              enctype="multipart/form-data">
            <input type='hidden' name='action' value='update'/>
            <input type='hidden' name='userId' value='${activeUser.id}'/>
            <input type='hidden' name='adId' value='${ad.id}'/>
            <div class="form-group">
                <label class="col-sm-2 control-label">Status:</label>
                <div class="col-sm-2">
                    <select class="form-control" name="status">
                        <option value="false">Actual</option>
                        <option value="true">Closed</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Model:</label>
                <div class="col-sm-6">
                    <input class="form-control" type='text' placeholder='input model' name='model' id='model'
                           value="${ad.car.name}">
                </div>
            </div>
            <div class="form-group">
                <td>
                    <label class="col-sm-2 control-label">Year of car:</label>
                    <div class="col-sm-2">
                        <select class="form-control" id="year" name="year" onchange="showAdditionalField(this)">
                            <option value=""></option>
                            <c:forEach items="${years}" var="year">
                                <option value="${year.value}"
                                        <c:if test="${ad.car.year.value == year.value}">
                                            selected
                                        </c:if>>
                                        ${year.value}
                                </option>
                            </c:forEach>
                            <option value="other...">other...</option>
                        </select>
                    </div>
                </td>
                <td>
                    <div id="yearField" class="col-sm-2">
                    </div>
                </td>
            </div>
            <div class="form-group">
                <td>
                    <label class="col-sm-2 control-label">Color of car:</label>
                    <div class="col-sm-2">
                        <select class="form-control" id="color" name="color" onchange="showAdditionalField(this)">
                            <option value=""></option>
                            <c:forEach items="${colors}" var="color">
                                <option value="${color.name}"
                                        <c:if test="${ad.car.color.name == color.name}">
                                            selected
                                        </c:if>>
                                        ${color.name}
                                </option>
                            </c:forEach>
                            <option value="other...">other...</option>
                        </select>
                    </div>
                </td>
                <td>
                    <div id="colorField" class="col-sm-2">
                    </div>
                </td>
            </div>
            <div class="form-group">
                <td>
                    <label class="col-sm-2 control-label">Engine of car:</label>
                    <div class="col-sm-2">
                        <select class="form-control" id="engine" name="engine" onchange="showAdditionalField(this)">
                            <option value=""></option>
                            <c:forEach items="${engines}" var="engine">
                                <option value="${engine.name}"
                                        <c:if test="${ad.car.engine.name == engine.name}">
                                            selected
                                        </c:if>>
                                        ${engine.name}
                                </option>
                            </c:forEach>
                            <option value="other...">other...</option>
                        </select>
                    </div>
                </td>
                <td>
                    <div id="engineField" class="col-sm-2">
                    </div>
                </td>
            </div>
            <div class="form-group">
                <td>
                    <label class="col-sm-2 control-label">Body of car:</label>
                    <div class="col-sm-2">
                        <select class="form-control" id="body" name="body" onchange="showAdditionalField(this)">
                            <option value=""></option>
                            <c:forEach items="${bodies}" var="body">
                                <option value="${body.name}"
                                        <c:if test="${ad.car.body.name == body.name}">
                                            selected
                                        </c:if>>
                                        ${body.name}
                                </option>
                            </c:forEach>
                            <option value="other...">other...</option>
                        </select>
                    </div>
                </td>
                <td>
                    <div id="bodyField" class="col-sm-2">

                    </div>
                </td>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Description:</label>
                <div class="col-sm-6">
                    <textarea id="description" name="description">${ad.description}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Load new photo:</label>
                <div class="col-sm-6">
                    <input name="file" type="file">
                </div>
            </div>
            <c:if test="${picture!=null}">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Old photo:</label>
                    <div class="col-sm-6">
                        <img width="250" src="data:image/png;base64,${picture}"/>
                    </div>
                </div>
            </c:if>
            <div class="form-group">
                <h5 align="center">
                    <button onclick='return checkAd();' type="submit" class="btn-primary">
                        <span class="glyphicon glyphicon-check"></span>  Update
                    </button><br/>
                </h5>
            </div>
        </form>
    </div>
</c:if>
<c:if test="${activeUser.role=='guest'}">
    <div class="jumbotron text-center">
        <h2><strong>The guest can not update ad! Please, sign in or sing up.</strong></h2><br/>
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
