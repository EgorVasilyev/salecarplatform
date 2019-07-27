<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Show ads</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        .table {
            table-layout: fixed;
        }
        td {
            word-wrap:break-word;
        }
        .container-my {
            max-width: 95%;
            position: relative;
            margin-left: auto;
            margin-right: auto;
            padding-right: 15px;
            padding-left: 15px;
        }
        blockquote {
            background: url(https://www.axa.de/site/axa-de/get/params_E-35817624/8222246/geparkte-autos-in-reihe.jpg.pagespeed.ce.7CTHh14teL.jpg); /* Фоновый цвет и фоновый рисунок*/
            background-repeat: no-repeat;
            background-size: 100%;
            color: white;
        }
    </style>
    <script type="text/javascript">
        <%@include file="/WEB-INF/js/functions.js"%>
    </script>
</head>
<body>
<blockquote>
    <h6 align="right">
        <form>
            <h5><span class="glyphicon glyphicon-user"></span>&ensp;<strong>Hello, ${activeUser.login}</strong></h5>
        </form>
        <c:if test="${activeUser.role=='admin' || activeUser.role=='user'}">
            <c:if test="${activeUser.role=='admin'}">
                <form action='${pageContext.servletContext.contextPath}/users' method="get">
                    <input type='hidden' name='action' value='showUsers'/>
                    <button type="submit" class="btn-primary">
                        <span class="glyphicon glyphicon-align-justify"></span> Show all users
                    </button>
                </form>
            </c:if>
            <form action='${pageContext.servletContext.contextPath}/users' method="get">
                <input type='hidden' name='action' value='goUpdateUser'/>
                <input type='hidden' name='id' value='${activeUser.id}'/>
                <button type="submit" class="btn-primary">
                    <span class="glyphicon glyphicon-edit"></span> Edit my profile
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
            <br/><br/><br/><br/><br/><br/><br/><br/><br/>
        </c:if>
    </h6>
</blockquote>

<div class="container-my">
    <form id="refreshAds" action='${pageContext.servletContext.contextPath}/ads' method="get">
        <table>
            <tbody>
            <tr>
                <td>
                    <div class="form-group">
                        <input type='hidden' name='action' value='showAds'>
                        <input type='hidden' name='actual' id='actual' value="" >
                        <input type='hidden' name='currentDay' id='currentDay' value="" >
                        <input type='hidden' name='withPhoto' id='withPhoto' value="" >
                        <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#filter">
                            <span class="glyphicon glyphicon-filter"></span> Show / hide filters</button>
                    </div>
                </td>
                <td>
                    <div id="filter" class="form-group collapse">
                        <input type="checkbox" class="form-check-input" id="checkboxActual"
                        <c:if test="${checkedActual == true}"> checked</c:if>
                               onchange="setValueYesNo(this, $('#actual'));">
                        Actual
                        <script>setValueYesNo($('#checkboxActual'), $('#actual'));</script>
                        <input type="checkbox" class="form-check-input" id="checkboxCurrentDay"
                        <c:if test="${checkedCurrentDay == true}"> checked</c:if>
                               onchange="setValueYesNo(this, $('#currentDay'));">
                        Last day
                        <script>setValueYesNo($('#checkboxCurrentDay'), $('#currentDay'));</script>
                        <input type="checkbox" class="form-check-input" id="checkboxWithPhoto"
                        <c:if test="${checkedWithPhoto == true}"> checked</c:if>
                               onchange="setValueYesNo(this, $('#withPhoto'));">
                        With photo
                        <script>setValueYesNo($('#checkboxWithPhoto'), $('#withPhoto'));</script>
                        <div class="col-sm-3">
                            <input class="form-control" type='text' placeholder='Search by model'
                                   name='byName' id='byName' value="${model}">
                        </div>
                        <button type="submit" class="btn btn-success">
                            <span class="glyphicon glyphicon-ok"></span> Apply filters
                        </button>
                        <button type="submit" class="btn btn-danger" onclick="clearFilters()">
                            <span class="glyphicon glyphicon-trash"></span> Clear filters
                        </button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
    <h4><span class="glyphicon glyphicon-list"></span>&ensp;List of ads:</h4>
    <table class="table" id='table'>
        <tbody id="tBody">
        <tr>
            <th width="7%">Status</th>
            <th width="5%">ID</th>
            <th width="10%">Date</th>
            <th width="15%">Car</th>
            <th width="20%">Description</th>
            <th width="20%">Photo</th>
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
                        <strong>Model:<br/></strong><c:out value=" ${ad.car.name}"/>
                    </p>
                    <p>
                        <strong>Year:<br/></strong><c:out value=" ${ad.car.year.value}"/>
                    </p>
                    <p>
                        <strong>Engine:<br/></strong><c:out value=" ${ad.car.engine.name}"/>
                    </p>
                    <p>
                        <strong>Color:<br/></strong><c:out value=" ${ad.car.color.name}"/>
                    </p>
                    <p>
                        <strong>Body:<br/></strong><c:out value=" ${ad.car.body.name}"/>
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
                <td align="center">
                    <c:if test="${activeUser.role=='admin' || (activeUser.role=='user' && activeUser.id==ad.user.id)}">
                        <form action='${pageContext.servletContext.contextPath}/ads' method='get'>
                            <input type='hidden' name='action' value='goToUpdateAd'>
                            <input type='hidden' name='id' value='${ad.id}'/>
                            <button type="submit" class="btn btn-success">
                                <span class="glyphicon glyphicon-pencil"></span> Edit
                            </button>
                        </form>
                        <form action='${pageContext.servletContext.contextPath}/ads' method='post'>
                            <input type='hidden' name='action' value='deleteFromAll'>
                            <input type='hidden' name='id' value='${ad.id}'/>
                            <button type="submit" class="btn btn-danger">
                                <span class="glyphicon glyphicon-remove"></span> Delete
                            </button>
                        </form>
                    </c:if>
                    <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#contact${ad.id}">
                        <span class="glyphicon glyphicon-user"></span> Show the contact</button>
                    <div id="contact${ad.id}" class="collapse">
                        <br/><strong>Login: </strong><c:out value="${ad.user.login}"/><br/>
                        <br/><strong>Phone: </strong><c:out value="${ad.user.phone}"/>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
