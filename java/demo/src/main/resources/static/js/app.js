$(function(){
/*这段代码是固定的，必须要放到js中*/
      function setupWebViewJavascriptBridge(callback) {
	if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
	if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
	window.WVJBCallbacks = [callback];
	var WVJBIframe = document.createElement('iframe');
	WVJBIframe.style.display = 'none';
	WVJBIframe.src = 'https://__bridge_loaded__';
	document.documentElement.appendChild(WVJBIframe);
	setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)
}




	
	
	$(".share").on("click",function(){

    	pd(1);

    })
	$("#app").on("click",function(){
		// alert("1")
	    	pd(2);
	})
  
    	
  
function pd(a){
	var u = navigator.userAgent;
	if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {
	//安卓手机
	// alert("安卓手机");
    // 由于对象映射，所以调用test对象等于调用Android映射的对象
 
    	// test.hello({'key':$("#enimg")[0].src,'type':a});
    	test.share(a)


   
    
	} else if (u.indexOf('iPhone') > -1) {
	//苹果手机
	// alert("苹果手机");
	setupWebViewJavascriptBridge(function(bridge) {
		bridge.callHandler('share', {'type':a}, function responseCallback(responseData) {
			console.log("JS received response:", responseData);
		})
	})
	} else if (u.indexOf('Windows Phone') > -1) {
	//winphone手机
	alert("winphone手机");
	}
}

})
	