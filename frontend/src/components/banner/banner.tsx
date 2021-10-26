import React, { useState, useEffect, FC, ReactElement } from 'react'
import {IBannerImage, IDefaultConfig, IProps, IStyle} from "./typing/banner";
import BannerController from "./bannerController";
import './banner.css'



const defaultConfig:IDefaultConfig = {
  stayTime: 3,
  rollTime: 1
}



const Banner:FC<IProps> = (props): ReactElement => {
  const arr = props.data;
  const config = Object.assign(defaultConfig, props.config)
  const [current, setCurrent] = useState<number>(-1);
  function autoPlay():void {
    if(current === arr.length - 1){
      setCurrent(0)
    } else {
      setCurrent(count => count+1)
    }
  }

  useEffect( ()=>{
    let timeout = current === -1 ? 0 : config.stayTime * 1000
    const timer = setInterval( ()=>{
      autoPlay()
    }, timeout)
    return () => clearTimeout(timer)
  })

  const imgList = props.data.map( (item:IBannerImage) =>{
    let styleObj = {
      color: item.titles.color,
      backgroundColor: item.backgroundColor
    }
    return (
      <li key={item.des as string} style={styleObj as React.CSSProperties}>
        <div className="big-title">{item.titles.big}</div>
        <div className="small-title">{item.titles.small}</div>
        <img alt={item.des as string} src={item.image as string}/>
      </li>
    )
  })

  const imgBoxStyle = {
    width: props.data.length * 100  + 'vw',
    left: '-' + (current * 100) +'vw',
    transition: `left ${config.rollTime}s`
  };

 return(
   <div className="banner">
     <div className="img-box">
       <ul style={imgBoxStyle}>{imgList}</ul>
     </div>
     <BannerController
       data={props.data}
       current={current}
       stayTime={config.stayTime}
     />
   </div>
 )
}
export default Banner