//rem动态计算布局方法
(function (win, doc) {
    var fresh = function () {
        var html = doc.documentElement;//获取当前文档元素的宽度
        var w = html.clientWidth;//获取当前屏幕的宽度
        html.style.fontSize = (w / 10) + "px";//设定html的fontSize,即rem
        // console.log(html.style.fontSize=(w/10)+"px");
    }
//判断监听页面是否加载完毕
    if (document.readyState === "complete") {
        fresh();
    } else {
        //监听页面加载完毕，且完毕后执行fresh()
        document.addEventListener("DOMContentLoaded", fresh, false);
    }
    win.addEventListener("resize", fresh, false);




})(window, document);
