/**
 * @description: 轮播图
 */
import React, { useEffect, useState } from "react";

import ProgressBars from "./progress-bar";
import { DataItem, THEME_TYPE } from './index.d';
import Icon from './icon';
import './carousel.css';

/**
 * @description interval hook
 * @param callback 
 * @param interval 
 */
function useInterval(callback:Function, interval: number){
  useEffect(() => {
    const prev=new Date().getTime();
    const id=setInterval(()=>{
      callback(new Date().getTime()-prev); // 当前时间差
    },interval);
    return ()=>clearInterval(id)
  },[])
}

/**
 * @description slide hook
 * @param length: number 轮播元素的个数
 * @param speed: number 轮播切换时间间隔
 */
function useSlide(length:number, speed:number = 2000) {
  const [index, setIndex]=useState(0);
  useInterval((gap:number)=>{
    setIndex(()=>Math.floor(gap/speed)%length); // 当前轮播第几张
  }, speed)
  return index;
}

/**
 * @description 轮播图组件：支持自定义轮播切换速度，进度显示
 * @param data 轮播内容
 * @param speed 轮播切换速度
 * @returns 
 */
export default function Carousel({data, speed}: { data:DataItem[], speed:number}={speed:2000,data:[]}){
  const slideLen=data.length;
  const index=useSlide(slideLen, speed);
  const itemStyle={width: `${100/slideLen}%`};
  return (
    <div className="carousel-wrapper">
      <div className="inner" style={{
        width: `${slideLen*100}%`,
        transform: `translateX(-${index/slideLen*100}%)`
      }}>
        {data.map(item=><SlideItem data={item} key={item.id} style={itemStyle}/>)}
      </div>
      <ProgressBars speed={speed} length={slideLen} activeIndex={index}/>
    </div>
  )
}

/**
 * @description SlideItem组件
 * @param data slide item的内容
 * @param style 样式
 * @returns 
 */
export function SlideItem({data, style}: {data:DataItem, style: React.CSSProperties}){
  const themeStyle=getThemeStyle(data.themeType);
  return (
    <div className="carousel-item" style={{backgroundColor: themeStyle.bgColor, ...style}}>
      <div className="text-wrapper" style={{color: themeStyle.color}}>
        {data.title.map(item=><div className="title" key={item.toString()}>{item}</div>)}
        {data.description?.map(item=><div className="description" key={item.toString()}>{item}</div>)}
      </div>
      <div className="img-wrapper">
        <Icon name={data.icon} />
      </div>
    </div>
  )
}

/**
 * @description 获取slide主题（方便复用Item）
 * @param themeType 
 * @returns 
 */
function getThemeStyle(themeType:THEME_TYPE){
  switch(themeType){
    case THEME_TYPE.BLACK:
      return {bgColor:'#111', color:'#fff'};
    case THEME_TYPE.WHITE:
      return {bgColor:'#fafafa', color:'#000'};
    case THEME_TYPE.GREY:
      return {bgColor:'#f1f1f3', color:'#000'};
    default:
      return {bgColor:'#fafafa', color:'#000'};
  }
}