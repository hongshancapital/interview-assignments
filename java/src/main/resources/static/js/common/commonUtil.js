
/** 字符串开头判断 **/
String.prototype.startWith = function(s){
    if(s==null||s==""||this.length==0||s.length>this.length)
        return false;
    if(this.substr(0,s.length)==s)
        return true;
    else
        return false;
    return true;
}
/** 字符串结尾判断 **/
String.prototype.endWith = function(s){
    if(s==null||s==""||this.length==0||s.length>this.length)
        return false;
    if(this.substring(this.length-s.length)==s)
        return true;
    else
        return false;
    return true;
}

/** 相对路径的页面跳转 **/
function hrefBlankTurn(url) {
    var aHrefObj = $("#hiddenWindowChange");

    if($("#hiddenWindowChange").length <= 0){
        var aDom = '<a href="' + url + '" style="display: none;" target="_blank"><span id="hiddenWindowChange"></span></a>';

        $("body:first").append($(aDom));
    }else{
        aHrefObj.parent().attr('href', url);
    }
    $("#hiddenWindowChange").click();
}

/** 获取 json 对象中的数据 **/
function getJsonValue(jsonObj, keyPath, defValue) {
    var tmpJson = jsonObj;

    if($.isArray(keyPath)){
        for(var i=0; i<keyPath.length; i++){
            var oneKey = keyPath[i];

            if(oneKey in tmpJson){
                tmpJson = tmpJson[oneKey];
            }else{
                tmpJson = null;
                break;
            }
        }
    }else{
        tmpJson = tmpJson[keyPath];
    }
    if(!tmpJson){
        tmpJson = defValue;
    }

    return tmpJson;
}
function addUrlParams(obj,url){
    if(!$.isEmptyObject(obj)) {
        url = url +'?';
        jQuery.each(obj, function (i, val) {
            url = url + i + '=' + encodeURI(val) + '&';
        });
        url = url.substr(0, url.length - 1);
    }
    return url;
}

//日期格式化  var time2 = new Date().format("yyyy-MM-dd hh:mm:ss");
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    //处理年份
    var reYear = /(y+)/;
    var resultYear = reYear.exec(fmt);
    if (resultYear)
    {
        var yearformatPart = resultYear[0];//匹配到的格式化字符
        var yearVal = (this.getFullYear() + "").substr(4 - yearformatPart.length);
        fmt = fmt.replace(yearformatPart, yearVal);
    }
    for (var k in o) {
        var re = new RegExp("(" + k + ")");
        var re = re.exec(fmt);
        if (re) {
            var Val = "" + o[k];//本次需要替换的数据
            var formatPart = re[0];//匹配到的格式化字符
            var replaceVal = (formatPart.length == 1) ? (Val) : (("00" + Val).substr(Val.length));
            fmt = fmt.replace(formatPart, replaceVal);
        }
    }
    return fmt;
}

/** 将 from 数据组成 json 对象 **/
function getFormData(formDom) {

    var unindexed_array = $(formDom).serializeArray();
    var formJson = {};

    $.map(unindexed_array, function (n, i) {
        formJson[n['name']] = n['value'];
    });

    return formJson;
}

/** 对象非空判断 **/
function isNotBlank(text) {
    var flag = false;

    if(text !== null && text !== undefined && text !== ''){
        flag = true;
    }

    return flag;
}

/** 对象非空判断 **/
function isBlank(text) {
    var flag = true;

    if(text !== null && text !== undefined && text !== ''){
        flag = false;
    }

    return flag;
}


function readAsDataURL(file){
    //检验是否为图像文件
    var files = file.files[0];
    if(!/image\/\w+/.test(files.type)){
        bootbox.alert("请正确选择图片");
        return false;
    }
    if(files.size>2*1024*1024){
        bootbox.alert("文件不能超过2M");
        return false;
    }
    var that = $(file);

    var reader = new FileReader();
    //将文件以Data URL形式读入页面
    reader.readAsDataURL(files);
    reader.onload=function(e){
        var height = 0;
        that.siblings().hide();
        that.parents('.uploadBox').css({
            background:"url("+this.result+")",
            backgroundSize:'cover'
        })
    }
}




/** 只支持一层的数组或者对象 **/
function deleteEmptyProperty(object){
    alert("该方法已过时，请使用 clearJsonEmptyKey() 代替");

    return;

    for (var i in object) {
        var value = object[i];
        if (typeof value === 'object') {
            if (Array.isArray(value)) {
                if (value.length == 0) {
                    delete object[i];

                    continue;
                }
            }
            deleteEmptyProperty(value);
            if (isEmpty(value)) {

                delete object[i];

            }
        } else {
            if (value === '' || value === null || value === undefined) {
                delete object[i];

            } else {

            }
        }
    }
}

/** ============================================== **/
/** json 是否为空 **/
function isJsonEmpty(object) {
    var isEmpty = true;

    if (object) {
        for (var name in object) {
            isEmpty = false;
            break;
        }
    }
    return isEmpty;
}

/** 清空json 的空key **/
function clearJsonObjectEmptyKey(jsonObj) {
    var jsonResult = {};

    for(var key in jsonObj){

        var value = jsonObj[key];

        if(isNotBlank(value)){
            jsonResult[key] = value;
        }
    }
    return jsonResult;
}

/**
 * formdata 添加数据
 */
function appendFormData(formDataObj, key, value){
    if(isNotBlank(value)){
        formDataObj.append(key, value);
    }
}

/** 清空json 的空数组 **/
function clearJsonArrayEmptyKey(jsonArray) {
    var resultArray = [];

    for(var i=0; i<jsonArray.length; i++){
        var jsonObj = jsonArray[i];

        var objClear = clearJsonObjectEmptyKey(jsonObj);

        if(!isJsonEmpty(objClear)){
            resultArray.push(objClear);
        }
    }
    return resultArray;
}

/** 清空json 数组或者对象 **/
function clearJsonEmptyKey(jsonObj) {
    var resultJson = jsonObj;
    if(Array.isArray(jsonObj)){
        resultJson = clearJsonArrayEmptyKey(jsonObj);
    }else{
        resultJson = clearJsonObjectEmptyKey(jsonObj);
    }
    return resultJson;
}

/** 根据中文排序 **/
function sortByChinese(dataArray, sortKeyName) {
    if(isNotBlank(sortKeyName)){
        /* 获取数组元素内需要比较的值 */
        function getValue (option) { // 参数： option 数组元素
            if (!sortKeyName) return option
            var data = option
            sortKeyName.split('.').filter(function (item) {
                data = data[item]
            });
            return data + ''
        }
        dataArray.sort(function (item1, item2) {
            return getValue(item1).localeCompare(getValue(item2), 'zh-CN');
        });
    }else{
        dataArray.sort(function (item1, item2) {
            return item1.localeCompare(item2, 'zh-CN');
        });
    }
}

//清空表单
function ClearForm(id) {
    var objId = document.getElementById(id);
    if (objId == undefined) {
        return;
    }
    for (var i = 0; i < objId.elements.length; i++) {
        if (objId.elements[i].type == "text") {
            objId.elements[i].value = "";
        }
        else if (objId.elements[i].type == "password") {
            objId.elements[i].value = "";
        }
        else if (objId.elements[i].type == "radio") {
            objId.elements[i].checked = false;
        }
        else if (objId.elements[i].type == "checkbox") {
            objId.elements[i].checked = false;
        }
        else if (objId.elements[i].type == "select-one") {
            objId.elements[i].options[0].selected = true;
        }
        else if (objId.elements[i].type == "select-multiple") {
            for (var j = 0; j < objId.elements[i].options.length; j++) {
                objId.elements[i].options[j].selected = false;
            }
        }
        else if (objId.elements[i].type == "textarea") {
            objId.elements[i].value = "";
        }
        //else if (objId.elements[i].type == "file") {
        // //objId.elements[i].select();
        // //document.selection.clear();
        // // for IE, Opera, Safari, Chrome
        // var file = objId.elements[i];
        // if (file.outerHTML) {
        // file.outerHTML = file.outerHTML;
        // } else {
        // file.value = ""; // FF(包括3.5)
        // }
        //}
    }
}

/**
 * 将json 数组转成 map 格式
 * ========== 示例：==========
 * 原始格式：[{code: 1, name: "名称1"}, {code: 2, name: "名称2"}, {code: 4, name: "名称4"}]
 * 传递参数： "code"
 * 返回格式：{"1": {code: 1, name: "名称1"}, "2": {code: 2, name: "名称2"}, "3": {code: 4, name: "名称4"}}
 */
function jsonArrayConvertMap(dictionaryArray, indexKey) {

    var result = {};

    for(var i=0; i<dictionaryArray.length; i++){

        var oneObj = dictionaryArray[i];

        var indexValue = oneObj[indexKey];

        result[indexValue + ""] = oneObj;
    }
    return result;
}

/** IP转成整型 **/
function ipToNumber(ip) {
    var num = 0;
    ip = ip.split(".");
    num = Number(ip[0]) * 256 * 256 * 256 + Number(ip[1]) * 256 * 256 + Number(ip[2]) * 256 + Number(ip[3]);
    num = num >>> 0;
    return num;
}

/** 整型解析为IP地址 **/
function numberToIp(num) {
    var str;
    var tt = new Array();
    tt[0] = (num >>> 24) >>> 0;
    tt[1] = ((num << 8) >>> 24) >>> 0;
    tt[2] = (num << 16) >>> 24;
    tt[3] = (num << 24) >>> 24;
    str = String(tt[0]) + "." + String(tt[1]) + "." + String(tt[2]) + "." + String(tt[3]);
    return str;
}
/** url 传递 json 数据时的编码 **/
function encodeUrlJsonData(jsonData){
    var jsonStr = JSON.stringify(jsonData);

    //必须二次编码
    jsonStr = encodeURIComponent(jsonStr);
    jsonStr = encodeURIComponent(jsonStr);

    return jsonStr;
}
/** url 传递 json 数据时的解码 **/
function decodeUrlJsonData(jsonDataStr){
    var paramStr = decodeURIComponent(jsonDataStr);

    var paramJson = JSON.parse(paramStr);

    return paramJson;
}

/** 获取地址根 **/
function getUrlRoot(){
    var pageUrl = window.location.href;
    var host = window.location.host;

    var urlArray = pageUrl.split("//");

    var urlRoot = urlArray[0] + "//" + host;

    return urlRoot;
}

$(document).ready(function(){
    /** jquery code config **/
    $.ajaxSetup({
        statusCode: {
            401: function(){
                bootbox.alert("您还未登录，请登录",function(){
                    window.location.href = base_path + '/page/login.html';
                });
            },
            403: function(){
                bootbox.alert("没有权限，请联系管理员");
            }
        },
        cache:false//禁用IE缓存
    });
})
