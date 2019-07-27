<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My ads</title>
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
<blockquote>
    <h6 align="right">
        <form>
            <span class="glyphicon glyphicon-user"></span>&ensp;<c:out value="${activeUser.login} "></c:out>
            , you are on the page of your ads
        </form>
        <form action='${pageContext.servletContext.contextPath}/ads' method="get">
            <input type='hidden' name='action' value='showAds'/>
            <button type="submit" class="btn-info">
                <span class="glyphicon glyphicon-list"></span> Show all ads
            </button>
        </form>
        <c:if test="${activeUser.role=='admin' || activeUser.role=='user'}">
            <form action='${pageContext.servletContext.contextPath}/ads' method="get">
                <input type='hidden' name='action' value='goToCreateAd'/>
                <input type='hidden' name='id' value='${activeUser.id}'/>
                <button type="submit" class="btn-primary">
                    <span class="glyphicon glyphicon-plus-sign"></span> Create new ad
                </button>
            </form>
            <form action='${pageContext.servletContext.contextPath}/signIn' method="get">
                <button type="submit" class="btn-danger">
                    <span class="glyphicon glyphicon-log-out"></span> Exit
                </button>
            </form>
            <br/><br/><br/><br/>
        </c:if>
        <c:if test="${activeUser.role=='guest'}">
            <form action='${pageContext.servletContext.contextPath}/signIn' method="get">
                <button type="submit" class="btn-info">
                    <span class="glyphicon glyphicon-log-in"></span> Sign in / Sign up
                </button>
            </form>
        </c:if>
    </h6>
</blockquote>

<c:if test="${activeUser.role=='admin' || activeUser.role=='user'}">
    <div class="container">
        <h4><span class="glyphicon glyphicon-list"></span>&ensp;List of my ads:</h4>
        <table class="table" id='table'>
            <tbody>
            <tr>
                <th width="5%">Status</th>
                <th width="3%">ID</th>
                <th width="9%">Date</th>
                <th width="12%">Car</th>
                <th width="23%">Description</th>
                <th width="20%">Photo</th>
                <th width="5%"></th>
                <th width="3%"></th>
            </tr>
            <c:forEach items="${ads}" var="ad">
                <tr>
                    <td>
                        <div class="form-check">
                            <c:if test="${ad.closed==true}">
                                <font color="red">Closed</font>
                            </c:if>
                            <c:if test="${ad.closed==false}">
                                <font color="green">Actual</font>
                            </c:if>
                        </div>
                    </td>
                    <td><c:out value="${ad.id}"/></td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${ad.created}"/></td>
                    <td>
                        <p>
                            <strong>Model:</strong><c:out value=" ${ad.car.name}"/>
                        </p>
                        <p>
                            <strong>Year:</strong><c:out value=" ${ad.car.year.value}"/>
                        </p>
                        <p>
                            <strong>Engine:</strong><c:out value=" ${ad.car.engine.name}"/>
                        </p>
                        <p>
                            <strong>Color:</strong><c:out value=" ${ad.car.color.name}"/>
                        </p>
                        <p>
                            <strong>Body:</strong><c:out value=" ${ad.car.body.name}"/>
                        </p>
                    </td>
                    <td><c:out value="${ad.description}"/></td>
                    <td>
                        <img width="250" src="
                        <c:forEach var="picture" items="${pictures}" varStatus="pictureCount">
                            <c:if test="${picture.key==ad.id}">
                                <c:if test="${picture.value!=null}">
                                    data:image/png;base64,${picture.value}
                                </c:if>
                                <c:if test="${picture.value==null}">
                                    https://i.ya-webdesign.com/images/and-svg-car-8.png
                                </c:if>
                            </c:if>
                         </c:forEach>
                    "/>
                    </td>

                    <c:if test="${activeUser.role=='admin' || (activeUser.role=='user' && activeUser.id==ad.user.id)}">
                        <td>
                            <form action='${pageContext.servletContext.contextPath}/ads' method='get'>
                                <input type='hidden' name='action' value='goToUpdateAd'>
                                <input type='hidden' name='id' value='${ad.id}'/>
                                <button type="submit" class="btn-success">
                                    <span class="glyphicon glyphicon-pencil"></span> Edit
                                </button>
                            </form>
                        </td>
                        <td><form action='${pageContext.servletContext.contextPath}/ads' method='post'>
                            <input type='hidden' name='action' value='deleteFromMine'>
                            <input type='hidden' name='id' value='${ad.id}'/>
                            <button type="submit" class="btn-danger">
                                <span class="glyphicon glyphicon-remove"></span> Delete
                            </button>
                        </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table><br/>
    </div>
</c:if>
</body>
</html>
