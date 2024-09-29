<%--
  Created by IntelliJ IDEA.
  User: han-yong-u
  Date: 2024. 8. 22.
  Time: 오전 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<html>
<head>
<meta charset="UTF-8">
    <title>Title</title>
<script type="text/javascript">
    var websock;
    $(function() {
        $('#message').keypress(function (event){
            var keycode = event.keyCode? event.keyCode : event.which;
            if(keycode == 13)
                send();
            event.stopPropagation();
        });

        $('#enterBtn').click(function() {
            connect();
        });

        $('#exitBtn').click(function() {
            disconnect();
        });

        $('#sendBtn').click(function() {
            send();
        });

    });

    function send() {
        var nickName = $('#nickName').val();
        var msg = $('#message').val();
        websock.send('msg: ' + nickName + ' => ' + msg);
        $('#message').val('');
    }

    function connect() {
        websock = new WebSocket("ws://localhost:8080/ws");
        websock.onopen = onOpen;
        websock.onmessage = onMessage;
    }

    function disconnect() {
        websock.close();
    }

    function onOpen(event) {
        appendMessage("연결되었습니다.");
    }

    function onClose(event) {
        appendMessage("연결이 종료되었습니다.");
    }

    function onMessage(event) {
        var data = event.data;
        appendMessage(data);
    }

    function appendMessage(msg) {
        $('#chatMessageArea').append(msg+"<br>");
        var chatAreaheight = $('#chatArea').height();
        var maxscroll = $('#chatMessageArea').height() - chatAreaheight;
        $('#chatArea').scrollTop(maxscroll);
    }
</script>
</head>
<body>
    <div class="container">
        별명 : <input type="text" id="nickName">
        <input type="button" value="입장" id="enterBtn" class="btn btn-success">
        <input type="button" value="퇴장" id="exitBtn" class="btn btn-danger">

        <h2 class="text-primary">대화영역</h2>
        <input type="text" id="message" required="required">
        <input type="button" id="sendBtn" value="전송" class="btn btn-info">
        <div id="chatArea">
            <div id="chatMessageArea"></div>
        </div>

    </div>
</body>
</html>
