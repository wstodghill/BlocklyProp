<%--
    Document   : login
    Created on : 24-mei-2015, 18:41:02
    Author     : Michel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/include.jsp"%>

<html>
    <head>
        <link type="text/css" rel="stylesheet" href="<url:getCdnUrl url="/style.css"/>" />
        <link rel="stylesheet" href="<url:getCdnUrl url="/lib/bootstrap/core/css/bootstrap.min.css"/>" />
        <link rel="stylesheet" href="<url:getCdnUrl url="/lib/bootstrap/plugins/bootstrap-table.min.css"/>" />
        <script src="<url:getCdnUrl url="/lib/jquery-1.11.3.min.js"/>" ></script>
        <script src="<url:getCdnUrl url="/lib/bootstrap/core/js/bootstrap.min.js"/>"></script>
        <script src="<url:getCdnUrl url="/authenticate.js"/>" ></script>
    </head>
    <body>

        <%@ include file="/WEB-INF/includes/pageparts/menu.jsp"%>

        <div class="container">
            <%
                String html = (String) request.getAttribute("html");
                if (html != null) {
            %>
            <%= html%>
            <% } else {%>
            <h2><fmt:message key="html.content_missing" /></h2>
            <% }%>

            <div class="row">
                <div class="col-md-4">
                    <h2><fmt:message key="home.latest_projects.title" /></h2>
                    <ul class="latest-projects"></ul>
                </div>

                <div class="col-md-4">
                    <h2><fmt:message key="home.c_project.title" /></h2>
                    <a class="editor-new-link editor-c-link editor-icon" href="<url:getUrl url="/projectcreate.jsp?lang=PROPC"/>" data-href="<url:getUrl url="/editor/blocklyc.jsp"/>"><fmt:message key="home.c_project.newlink" /></a>
                </div>

                <div class="col-md-4">
                    <h2><fmt:message key="home.spin_project.title" /></h2>
                    <a class="editor-new-link editor-spin-link editor-icon" href="<url:getUrl url="/projectcreate.jsp?lang=SPIN"/>" data-href="<url:getUrl url="/editor/blocklyspin.jsp"/>"><fmt:message key="home.spin_project.newlink" /></a>
                </div>
            </div>
        </div>

        <%@ include file="/WEB-INF/includes/pageparts/project-login-dialog.jsp"%>
        <script src="<url:getCdnUrl url="/latest.js"/>" ></script>

        <%@ include file="/WEB-INF/includes/pageparts/footer.jsp"%>
    </body>
</html>
