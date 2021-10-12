var base_path = "../..";

var base_api = base_path + "/";

function createShortUrl(){
    var fullUrl = $("#urlInput").val();

    var patt = /^http[s]{0,1}:\/\/([\w.]+\/?)\S*/;

    if(!patt.test(fullUrl)){
        alert("请输入正确的url地址！");
        return;
    }

    if(fullUrl == ""){
        alert("原始网址不能为空！");
    }else {

        var reqData = {};
        reqData["fullUrl"] = fullUrl;

        $.ajax({
            url: base_api + "/shortUrl/createShort",
            type: "post",
            data: reqData,
            dataType: "json",
            complete: function(request, textStatus){
                //doSomething
            },
            success: function(resultJson, textStatus){
                var status = resultJson['status'];
                var msg = resultJson['msg'];

                if(status == 1){//保存成功
                    console.log(resultJson["content"])

                    var shortUrl = resultJson["content"].shortUrl;

                    var urlRoot = getUrlRoot();

                    var fullShortUrl = urlRoot + "/" + shortUrl;

                    var ahrefDom = "短连接地址：<a href='/" + shortUrl + "'>" + fullShortUrl + "</a>";

                    $("#hrefDiv").html(ahrefDom);

                    $("#qucodeDiv").html("");

                    new QRCode(document.getElementById("qucodeDiv"), fullShortUrl);

                }else{
                    alert(msg);
                }
            }
        });
    }

}


$(document).ready(function(){



});
