<%--
    Document   : login
    Created on : 24-mei-2015, 18:41:02
    Author     : Michel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/include.jsp"%>

<html><!-- manifest=node.manifest> -->
    <head>
        <meta charset="utf-8">
        <meta name="base" content="<url:getUrl url="/"/>">
        <title>BlocklyProp</title>
        <script type="text/javascript" src="<url:getCdnUrl url="/lib/jquery-1.11.3.min.js"/>"></script>
        <script type="text/javascript" src="<url:getCdnUrl url="/lib/sha256.min.js"/>" ></script>
        <script type="text/javascript" src="<url:getCdnUrl url="/authenticate.js"/>" ></script>
        <script type="text/javascript" src="<url:getCdnUrl url="/lib/term.js"/>"></script>
        <script type="text/javascript" src="<url:getCdnUrl url="/ace/ace.js"/>"></script>
        <script type="text/javascript" src="<url:getCdnUrl url="/detect.js"/>"></script>
        <script type="text/javascript" src="<url:getCdnUrl url="/blocklypropclient.js"/>"></script>
        <script type="text/javascript" src="<url:getCdnUrl url="/blocklyc.js"/>"></script>
        <script type="text/javascript" src="<url:getCdnUrl url="/utils.js"/>"></script>
        <script type="text/javascript" src="<url:getCdnUrl url="/editor.js"/>"></script>
        <link href="<url:getCdnUrl url="/lib/bootstrap/core/css/bootstrap.min.css"/>" rel="stylesheet">
        <link href="<url:getCdnUrl url="/style-editor.css"/>" rel="stylesheet" type="text/css" />
    </head>
    <body data-challenge="<authentication:challenge />" data-timestamp="<authentication:timestamp />" >
        <div id="editor">
            <table id="content_table">
                <tr>
                    <td>
                        <jsp:include page="/WEB-INF/includes/pageparts/editor-menu.jsp">
                            <jsp:param name="editor_lang" value="c" />
                            <jsp:param name="demo" value="false" />
                        </jsp:include>
                    </td>
                </tr>
                <tr>
                    <td id="content">
                        <div id="content_blocks">
                            <iframe name="content_blocks" src="<url:getUrl url="/frame/framec.jsp"/>"></iframe>
                        </div>
                        <div id="content_propc">
                            <div id="code-propc"></div>
                        </div>
                        <div id="content_xml">
                            <div id="code-xml"></div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>



        <div class="modal fade" id="compile-dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="compile-dialog-title">Modal title</h4>
                    </div>
                    <div class="modal-body">
                        <label for="compile-console">Result</label>
                        <textarea class="form-control" rows="15" id="compile-console"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <div class="modal fade" id="console-dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="console-dialog-title">Console</h4>
                    </div>
                    <div class="modal-body">
                        <div id="serial_console" class="console"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <%@ include file="/WEB-INF/includes/pageparts/clientdownload.jsp"%>

        <script src="<url:getCdnUrl url="/lib/bootstrap/core/js/bootstrap.min.js"/>"></script>
        <script src="<url:getCdnUrl url="/lib/bootstrap/plugins/bootbox.min.js"/>"></script>
        <script>
            var type = 'PROPC';
        </script>
    </body>
</html>
