<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
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
${activeUser=null}
<c:if test="${error!=''}">
    <div style="background-color: red" >
        <c:out value="${error}">
        </c:out>
    </div>
</c:if>
<div class="jumbotron text-center">
    <h2><strong>Welcome to car sale platform!</strong></h2><br/>
    <h4>Choose your way:</h4>
</div>
<div class="container">
    <table width="100%" align="center">
        <thead>
        <tr>
            <td width="33%" align="center">
                <button type="button" class="btn btn-success" data-toggle="collapse" data-target="#singIn">
                    <span class="glyphicon glyphicon-log-in"></span> Sign in, if you are registered</button>
            </td>
            <td width="33%" align="center">
                <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#singUp">
                    <span class="glyphicon glyphicon-new-window"></span> Sign up, if you are not registered</button>
            </td>
            <td width="33%" align="center">
                <button type="submit" class="btn btn-danger" data-toggle="collapse" data-target="#demo">
                    <span class="glyphicon glyphicon-user"></span> Demonstration. Visiting as:
                </button>
            </td>
        </tr>
        </thead>

        <tbody>
        <tr>
            <td align="center">
                <br/>
                <form id="singIn" class="collapse" action='${pageContext.servletContext.contextPath}/signIn' method='post'>
                    <input type='hidden' name='action' value="signIn">
                    <div class="form-group">
                        Login&ensp;<span class="glyphicon glyphicon-user"></span>
                        <input class="form-control" type='text' placeholder='input login' name='login' id="login"><br/>
                    </div>
                    <div class="form-group">
                        Password&ensp;<span class="glyphicon glyphicon-eye-close"></span>
                        <input class="form-control" type='password' placeholder='input password' name='password' id="password"><br/>
                    </div>
                    <div class="form-group" align="center">
                        <button type="submit" class="btn btn-success" onclick='return checkAuthentication();' >
                            <span class="glyphicon glyphicon-log-in"></span> Enter
                        </button>
                    </div>
                </form>
            </td>
            <td align="center">
                <br/>
                <form id="singUp" class="collapse"
                      action='${pageContext.servletContext.contextPath}/signIn' method='post'>
                    <input type='hidden' name='action' value="signUp">
                    <div class="form-group">
                        Login&ensp;<span class="glyphicon glyphicon-user"></span>
                        <input class="form-control" type='text' placeholder='input login' name='newLogin' id="newLogin"><br/>
                    </div>
                    <div class="form-group">
                        Password&ensp;<span class="glyphicon glyphicon-eye-close"></span>
                        <input class="form-control" type='password' placeholder='input password' name='newPassword' id="newPassword"><br/>
                    </div>
                    <div class="form-group">
                        Phone&ensp;<span class="glyphicon glyphicon-earphone"></span>
                        <input class="form-control" type='text' placeholder='input phone' name='newPhone' id="newPhone"><br/>
                    </div>
                    <div class="form-group" align="center">
                        <button type="submit" class="btn btn-primary" onclick='return checkNewUser();' >
                            <span class="glyphicon glyphicon-new-window"></span> Create
                        </button>
                    </div>
                </form>
            </td>
            <td align="center" style="vertical-align:top;" id="demo" class="collapse">
                <br/><br/>
                <table>
                    <tbody>
                    <td>
                        <form action='${pageContext.servletContext.contextPath}/signIn' method='post'>
                            <input type='hidden' name='action' value="signIn">
                            <input type='hidden' name='login' value="guest">
                            <input type='hidden' name='password' value="guest">
                            <button type="submit" class="btn btn-danger">
                                a guest
                            </button>
                        </form>
                    </td>
                    <td>
                        <form action='${pageContext.servletContext.contextPath}/signIn' method='post'>
                            <input type='hidden' name='action' value="signIn">
                            <input type='hidden' name='login' value="user">
                            <input type='hidden' name='password' value="user">
                            <button type="submit" class="btn btn-success">
                                an user
                            </button>
                        </form>
                    </td>
                    <td>
                        <form action='${pageContext.servletContext.contextPath}/signIn' method='post'>
                            <input type='hidden' name='action' value="signIn">
                            <input type='hidden' name='login' value="admin">
                            <input type='hidden' name='password' value="admin">
                            <button type="submit" class="btn btn-primary">
                                an admin
                            </button>
                        </form>
                    </td>
                    </tbody>
                </table>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>
