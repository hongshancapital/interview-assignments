package com.example.urltrans_zhangtao.controller;

import com.example.urltrans_zhangtao.entity.LUrl_entity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@Api(tags = "LongToShort",description = "长短URL转换功能")
@RequestMapping(value = "/LTS")
public class LongToShort {
    public LUrl_entity[] LUrl_save=new LUrl_entity[999];

    //Long trans short interface start：---
    //Long to short method
    @ApiOperation(value = "长URL转短URL处理接口",notes = "长URL转短URL处理接口")
    @ApiImplicitParam(name = "LUrl",value = "长URL",required = true,dataType = "String")
    @RequestMapping(value = "/LTS_Str_g",method = RequestMethod.POST)
    @ResponseBody
    public LUrl_entity LTS_Str_g(String LUrl) throws NoSuchAlgorithmException {
        //set default value
        String toShort="error";
        //compute
        toShort=LtoS_exec(LUrl);
        //save
        LtoS_save(LUrl,toShort);
        //return entity
        LUrl_entity Lr=new LUrl_entity();
        Lr.setLUrl(LUrl.toString());
        Lr.setLUrl_trans(toShort);
        return Lr;
    }
    //Long to short compute
    public static String LtoS_exec(String LUrl) throws NoSuchAlgorithmException {
        String r_short="error";
        //compute
        String x2=URLDecoder.decode(LUrl);
        String x1=x2;
        System.out.println(x1.length());
        Boolean xtsp=true;
        if(x1.contains("https")) {
            x1 = x1.substring(8, x1.length());
        }
        else if(x1.contains("http")){
            x1 = x1.substring(7, x1.length());
            xtsp=false;
        }
        //System.out.println("x1="+x1);
        String charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int url_hash=x1.hashCode();
        //System.out.println("hash_code:"+url_hash);
        MessageDigest mdInst=MessageDigest.getInstance("MD5");
        mdInst.update(x1.getBytes(StandardCharsets.UTF_8));
        byte xmd5[]=mdInst.digest();
        int xmd5_l= xmd5.length;
        //System.out.println("x1_md5："+xmd5);
        //System.out.println("x1_md5-length："+xmd5.length);
        String url_s="";
        int xt=0,xh=0;
        char xc;
        StringBuffer xtemp=new StringBuffer();
        for(int i=0;i<7;i++){
            //System.out.println("start:"+i*xmd5_l/7);
            //System.out.println("end:"+xmd5_l+7);
            url_s=charset.substring(i*xmd5_l/7,xmd5_l+7);
            //System.out.println(url_s);
            xt=getRandom(0,url_s.length());
            //System.out.println("url_s的长度："+url_s.length());
            //System.out.println("随机数："+xt);
            if(url_s.length()<xt){
                xt=getRandom(0,url_s.length()-1);
                xc = url_s.charAt(xt);
            }else {
                xc = url_s.charAt(xt);
            }
            xtemp.append(xc);
        }
        r_short=xtemp.toString();
        return r_short;
    }
    //save
    public void LtoS_save(String LUrl,String LUrl_trans){
        for (int li=0;li<999;li++){
            if(null==LUrl_save[li]){
                LUrl_save[li]=new LUrl_entity();
                LUrl_save[li].setLUrl(LUrl);
                LUrl_save[li].setLUrl_trans(LUrl_trans);
                System.out.println(li + "LUrl:" + LUrl_save[li].getLUrl());
                System.out.println(li + "LUrl_trans:" +LUrl_save[li].getLUrl_trans());
                li=999;
            }
        }
    }
    //Random compute
    public static int getRandom(int start,int end){
        return (int)(Math.random()*(end-start));
    }
    //Long trans short interface end：！！！
    //Short trans Long interface start：
    @ApiOperation(value = "短URL转长URL处理接口",notes = "短URL转长URL处理接口")
    @ApiImplicitParam(name = "SUrl",value = "短URL",required = true,dataType = "String")
    @RequestMapping(value = "/STL_Str_g",method = RequestMethod.POST)
    @ResponseBody
    public LUrl_entity STL_Str_g(String SUrl) {
        System.out.println("Before:" + SUrl.toString());
        String toLong="error";
        for (int li=0;li<999;li++) {
            if (null != LUrl_save[li] && null != LUrl_save[li].getLUrl() && "" != LUrl_save[li].getLUrl()&&LUrl_save[li].getLUrl_trans().equals(SUrl)) {
                toLong=LUrl_save[li].getLUrl();
                System.out.println(li + "LUrl:" + LUrl_save[li].getLUrl());
                System.out.println(li + "LUrl_trans:" + LUrl_save[li].getLUrl_trans());
                li=999;
            }
        }
        //return entity
        LUrl_entity Lr=new LUrl_entity();
        Lr.setLUrl(toLong);
        Lr.setLUrl_trans(SUrl.toString());
        return Lr;
    }
    //
    //PC method start
    @ApiOperation(value = "长URL转短URL的PC端调用方法",notes = "长URL转短URL的PC端调用方法")
    @ApiImplicitParam(name = "LUrl",value = "长URL",required = true,dataType = "String")
    @RequestMapping(value = "/pcView_long",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView pcView_long(String LUrl) throws NoSuchAlgorithmException {
        LUrl_entity pcView_ret=LTS_Str_g(LUrl);
        ModelAndView LUrl_m=new ModelAndView("idx");
        LUrl_m.addObject("LUrl",pcView_ret.getLUrl());
        LUrl_m.addObject("LUrl_trans",pcView_ret.getLUrl_trans());
        return LUrl_m;
    }
    @ApiOperation(value = "短URL转长URL的PC端调用方法",notes = "短URL转长URL的PC端调用方法")
    @ApiImplicitParam(name = "SUrl",value = "短URL",required = true,dataType = "String")
    @RequestMapping(value = "/pcView_short",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView pcView_short(String SUrl){
        LUrl_entity pcView_ret=STL_Str_g(SUrl);
        ModelAndView LUrl_m=new ModelAndView("idx");
        LUrl_m.addObject("SUrl_LUrl",pcView_ret.getLUrl());
        return LUrl_m;
    }
    //pc method end

}
