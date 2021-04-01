<%@ page language="java" import="java.util.*, net.jhorstmann.i18n.I18N"
    contentType="text/javascript" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core_1_1" prefix="c" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

var asyncws = (function() {
    var url =
        location.protocol.replace(/http/, "ws") + "//"
            + location.host + "${ctx}/websock/asyncwsock.fn?session-id=${sessionId}";

    var local = {
        listeners: [ ],
        watchers: { }
    };

    local.onmessage = function(event) {
        var data = event.data;

        var message = JSON.parse(data);

        for(var index in local.listeners)
        {
            local.listeners[index](message.type, message.body);
        }

        var watcher = local.watchers[message.type];
        if (watcher != null)
        {
            watcher.callback(message.type, message.body);

            if (watcher.once)
            {
                delete local.watchers[message.type];
            }
        }
    };

    local.onclose = function() {
        local.connect();
    };

    local.connect = function() {
        local.sock = new WebSocket(url);

        local.sock.onmessage = local.onmessage;
        local.sock.onclose = local.onclose;
    };

    local.addListener = function(listener) {
        local.listeners.push(listener);
    };

    local.addWatcher = function(type, callback, once) {
        local.watchers[type] = {
            callback: callback,
            once: once
        };
    };

    local.connect();

    return local;

    /*var sock = new WebSocket(url);

    sock.onmessage = function(event) {
        var data = event.data;

        $this.find("#content").append(data).append("<br/>");
    };

    sock.onopen = function() {
        sock.send(JSON.stringify({
            command : "attach",
            value : channel
        }));
    };*/
})();
