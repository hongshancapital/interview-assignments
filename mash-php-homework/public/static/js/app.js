console.log('技术开发: 马帅');
console.log('邮箱: 402738923@qq.com');
console.log('手机号: 15890143123');
$(document).foundation()
/*菜单下行线 使用方式 div增加.hover-underline-menu 以及属性 data-menu-underline-from-center*/
$("[data-menu-underline-from-center] a").addClass("underline-from-center");
/**查询切换 */
$('.search').click(function () {
    $('#search').toggle();
})

  
/**a链接删除 */
$('button.href-delete').on('click',function(e){
    var href = $(this).attr('data-href');
    var disabled = $(this).prop('disabled'); 
    get_href_delete(e,href,disabled);
})
/**a链接编辑 */
$('button.href-edit').on('click',function(e){
    var href = $(this).attr('data-href');
    var disabled = $(this).prop('disabled'); 
    if(disabled) return false;  
    layer.load(2); 
    location.href = href ;
})
/**审核 a */
$('button.ajax-shenhe').on('click',function(){    
    var id = $(this).attr('data-id');
    var href = $(this).attr('data-href');
    var disabled = $(this).attr('disabled');
    var title = $(this).attr('data-title');
    if(!title){
        title = '是否确认审核？';
    }
    if(disabled) return false;
    layer.confirm(title,{'title':title},function(){
        layer.load(2);
        $.post(href,{id:id},function(data){
            if(data.code == 0){
                layer.msg(data.msg,{time:2000,icon:1},function(){
                    location.reload();
                })
            }else{
                layer.msg(data.msg,{time:2000,icon:5})
            } 
            layer.closeAll('loading');          
        })        
    })
})

/**layer open 打开iframe窗口 */
$('.layer-open').click(function(){
    layer_open($(this).attr('data-title'),$(this).attr('data-href'));
})
$('.layer-open-return').click(function(){
    var flag = $(this).hasClass('multiplu'); //是否需要返回多个字段    
    layer_open_return($(this).attr('data-title'),$(this).attr('data-href'),flag);
})


//下拉客户
$('#customer').change(function(){
    var val = $(this).val();
    $('#list-customer option').each(function(){
        if($(this).val() == val){ 
            $('#customer').val($(this).attr('data-id'));
        }
    })
})







/*************************FUNCTIONS**********************/
function stopDefault(e) {
    if (e && e.preventDefault) e.preventDefault();
    else
        window.event.returnValue = false;
} 
function get_href_delete(e,href,dd)
{
    stopDefault(e);
    if(dd) return false;       
    layer.confirm('是否确认删除?',{'title':'删除确认'},function(){
        layer.load(2);
        location.href = href ;
    })
}

/***layer open iframe窗口 */
function layer_open(title,url)
{
    layer.open({
        type: 2,
        title: title,
        shadeClose: true,
        shade: 0.8,
        area: ['90%', '80%'],
        //content: "{{ path('admin.product.card_create',{'id':"+id+"}) }}" //iframe的url
        content: url, //iframe的url
        
    });
}
//如果flag为true 则需要post提交，否则 get
function layer_open_return(title,url,flag)
{ 
    layer.open({
        type: 2,
        title: title,
        shadeClose: true,
        shade: 0.8,
        area: ['70%', '80%'],
        //content: "{{ path('admin.product.card_create',{'id':"+id+"}) }}" //iframe的url
        content: url, //iframe的url
        btn: ['确定','关闭'],
        yes: function(index,layero){
            //当点击‘确定’按钮的时候，获取弹出层返回的值
            var body = layer.getChildFrame('body', index);            
            if(!flag){
                var value = body.find('#choose_id').val() ; //此处需要约定input or textarea的id
                //var res = window["layui-layer-iframe" + index].callbackdata(value); //调用下面的函数callbackdata
                //打印返回的值，看是否有我们想返回的值。
                //console.log(res);
                //请求            
                var url = window.location.href ;
                url = url.split('?')[0] + '?id='+value ;
                location.href = url ;    
            }else{
                //需要提示当前form的隐藏域，然后post提交
                //此处修改为返回给父html
                //var body = layer.getChildFrame('body', index);
                /* var index_parent = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                var body = layer.getChildFrame('body',index_parent);
                console.log(body); */
                 //layero为当前的DOM对象，去找打开的iframe转成DOM对象调用子页面其中的function (callbackdata)               
                var value = body.find('#choose_id').val() ; 
                var res = $(layero).find("iframe")[0].contentWindow.callbackdata(value);
                $('tbody').append(res);
            }        
            //最后关闭弹出层
            layer.close(index);
        },
        /** 调用后设置子页面的值
        success: function(layero, index){
            var body = layer.getChildFrame('body', index);            
            body.find('input[name=order]').val('Hi，我是从父页来的')
          }
        */
    });
}
var callbackdata = function (value) {
    var data = '';
    value = value.split(',');
    var tr = '';    
    console.log(value);
   /*  $.each(value,function(k,id){ alert(id);
        $.post("/public/product",{id:id},function(data){
            tr += '<tr><td>'+(k+1)+'</td><td style="width:15%"><input type="text" name="product[]"  class="margin-0 product" value="'+v.data.id+'"></td>';
            tr += '<td>'+v.data.name+'</td><td>'+v.data.spec+'</td><td>'+v.data.unit+'</td> <td style="width:10%"><input type="number" name="num[]"  class="margin-0 num"></td>';
            tr += '<td style="width:10%"><input type="number" name="price[]" class="margin-0 price" value=""></td><td class="money"></td>';
            tr += '<td><button class="button small hollow alert inline-delete" type="button"><i class="fi-trash"></i> 删除</button></td> </tr> ';
        },'json')
    }) */
    /* $.post("/public/product",{id:value},function(data){
        $.each(data.data,function(k,v){ console.log(v);
            tr += '<tr><td>'+(k+1)+'</td><td style="width:15%"><input type="text" name="product[]"  class="margin-0 product" value="'+v.id+'"></td>';
            tr += '<td>'+v.name+'</td><td>'+v.spec+'</td><td>'+v.unit+'</td> <td style="width:10%"><input type="number" name="num[]"  class="margin-0 num"></td>';
            tr += '<td style="width:10%"><input type="number" name="price[]" class="margin-0 price" value=""></td><td class="money"></td>';
            tr += '<td><button class="button small hollow alert inline-delete" type="button"><i class="fi-trash"></i> 删除</button></td> </tr> ';
        })
    },'json') */
    $.ajax({
        type: "post",
        url: "/public/product",
        data: { 'id': value },
        async: false,
        success: function (data) {
            $.each(data.data, function (k, v) {
                console.log(v);
                tr += '<tr><td>' + (k + 1) + '</td><td style="width:15%"><input type="text" name="product[]"  class="margin-0 product" value="' + v.id + '"></td>';
                tr += '<td>' + v.name + '</td><td>' + v.spec + '</td><td>' + v.unit + '</td> <td style="width:10%"><input type="number" name="num[]"  class="margin-0 num"></td>';
                tr += '<td style="width:10%"><input type="number" name="price[]" class="margin-0 price" value=""></td><td class="money"></td>';
                tr += '<td><button class="button small hollow alert inline-delete" type="button"><i class="fi-trash"></i> 删除</button></td> </tr> ';
            })
        }
    }); 
    
    return tr;
}




