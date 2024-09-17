/**

 @Name：layuiAdmin 主入口
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */
 
layui.extend({
  setter: 'config' //配置文件
  ,admin: 'lib/admin' //核心模块
  ,miniPage: "miniPage"
  ,authtree: "authtree"
  ,view: 'lib/view' //核心模块
}).define(['setter', 'admin'], function(exports){
  var setter = layui.setter
  ,element = layui.element
  ,admin = layui.admin
  ,tabsPage = admin.tabsPage
  ,view = layui.view
  
  //根据路由渲染页面
  ,renderPage = function(){
    var router = layui.router()
    ,path = router.path
    ,pathURL = admin.correctRouter(router.path.join('/'))

    
    
    //默认读取主页
    if(!path.length) path = [''];
    
    //如果最后一项为空字符，则读取默认文件
    if(path[path.length - 1] === ''){
      path[path.length - 1] = setter.entry;
    }
    
    /*
    layui.config({
      base: setter.base + 'controller/'
    });
    */
    
    //重置状态
    var reset = function(type){
      //renderPage.haveInit && layer.closeAll();
      if(renderPage.haveInit){
        $('.layui-layer').each(function(){
          var othis = $(this),
          index = othis.attr('times');
          if(!othis.hasClass('layui-layim')){
            layer.close(index);
          }
        });
      }
      renderPage.haveInit = true;
      
      $(APP_BODY).scrollTop(0);
      delete tabsPage.type; //重置页面标签的来源类型
    };
    
    //如果路由来自于 tab 切换，则不重新请求视图
    if(tabsPage.type === 'tab'){
      //切换到非主页、或者切换到主页且主页必须有内容。方可阻止请求
      if(pathURL !== '/' || (pathURL === '/' && admin.tabsBody().html())){
        admin.tabsBodyChange(tabsPage.index);
        return reset(tabsPage.type);
      }
    }
    
    //请求视图渲染
    view().render(path.join('/')).then(function(res){
      
      //遍历页签选项卡
      var matchTo
      ,tabs = $('#LAY_app_tabsheader>li');
      
      tabs.each(function(index){
        var li = $(this)
        ,layid = li.attr('lay-id');
        
        if(layid === pathURL){
          matchTo = true;
          tabsPage.index = index;
        }
      });
      
      //如果未在选项卡中匹配到，则追加选项卡
      if(setter.pageTabs && pathURL !== '/'){
        if(!matchTo){
          $(APP_BODY).append('<div class="layadmin-tabsbody-item layui-show"></div>');
          tabsPage.index = tabs.length;
          element.tabAdd(FILTER_TAB_TBAS, {
            title: '<span>'+ (res.title || '新标签页') +'</span>'
            ,id: pathURL
            ,attr: router.href
          });
        }
      }
      
      this.container = admin.tabsBody(tabsPage.index);
      setter.pageTabs || this.container.scrollTop(0); //如果不开启标签页，则跳转时重置滚动条
      
      //定位当前tabs
      element.tabChange(FILTER_TAB_TBAS, pathURL);
      admin.tabsBodyChange(tabsPage.index);
      
    }).done(function(){
      layui.use('common', layui.cache.callback.common);
      $win.on('resize', layui.data.resize);
      
      element.render('breadcrumb', 'breadcrumb');
      
      //容器 scroll 事件，剔除吸附层
      admin.tabsBody(tabsPage.index).on('scroll', function(){
        var othis = $(this)
        ,elemDate = $('.layui-laydate')
        ,layerOpen = $('.layui-layer')[0];

        //关闭 layDate
        if(elemDate[0]){
          elemDate.each(function(){
            var thisElemDate = $(this);
            thisElemDate.hasClass('layui-laydate-static') || thisElemDate.remove();
          });
          othis.find('input').blur();
        }
        
        //关闭 Tips 层
        layerOpen && layer.closeAll('tips');
      });
    });
    
    reset();
  }
  
  //入口页面
  ,entryPage = function(fn){
    var router = layui.router()
    ,container = view(setter.container)
    ,pathURL = admin.correctRouter(router.path.join('/'))
    ,isIndPage;
    
    //检查是否属于独立页面
    layui.each(setter.indPage, function(index, item){
      if(pathURL === item){
        return isIndPage = true;
      }
    });
    
    //将模块根路径设置为 controller 目录
    layui.config({
      base: setter.base + 'controller/'
    });
    
    //独立页面
    if(isIndPage || pathURL === '/user/login'){ //此处单独判断登入页，是为了兼容旧版（即未在 config.js 配置 indPage 的情况）
      container.render(router.path.join('/')).done(function(){
        admin.pageType = 'alone';
      });
    } else { //后台框架页面
      
      //强制拦截未登入
      if(setter.interceptor){
        var local = layui.data(setter.tableName);
        if(!local[setter.request.tokenName]){
          return location.hash = '/user/login/redirect='+ encodeURIComponent(pathURL); //跳转到登入页
        }
      }
      
      //渲染后台结构
      if(admin.pageType === 'console') { //后台主体页
        renderPage();
      } else { //初始控制台结构
        container.render('layout').done(function(){
          renderPage();
          layui.element.render();
          
          if(admin.screen() < 2){
            admin.sideFlexible();
          }
          admin.pageType = 'console';
        }); 
      }
      
    }
  }
  
  ,APP_BODY = '#LAY_app_body', FILTER_TAB_TBAS = 'layadmin-layout-tabs'
  ,$ = layui.$, $win = $(window);
  
  //初始主体结构
  layui.link(
    setter.base + 'style/admin.css?v='+ (admin.v + '-1')
    ,function(){
      entryPage()
    }
    ,'layuiAdmin'
  );
  
  //监听Hash改变
  window.onhashchange = function(){
    entryPage();
     //执行 {setter.MOD_NAME}.hash 下的事件
    layui.event.call(this, setter.MOD_NAME, 'hash({*})', layui.router());
  };
  
  //扩展 lib 目录下的其它模块
  layui.each(setter.extend, function(index, item){
    var mods = {};
    mods[item] = '{/}' + setter.base + 'lib/extend/' + item;
    layui.extend(mods);
  });

  //对外输出
  exports('index', {
    render: renderPage
  });
});
