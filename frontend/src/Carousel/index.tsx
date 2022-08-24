
import React ,{useEffect,useState}from 'react'
import './style.css'
import {CarouselInter} from '../shared'

export default function Carousel(props:CarouselInter) {
  const {items}=props;
  let Bannerdistance:number=2000;  // 轮播时长
  let duration:number=500; // 动画时长
   const [stepIndex,setStepIndex]=useState(0);
    useEffect(()=>{
      let count:number=0;
      const timer= setInterval(() => {
        count=++count%items.length;
        setStepIndex(count);
       }, Bannerdistance);
            return ()=>{
              clearInterval(timer);
            }
    },[duration,Bannerdistance,items.length])
    return (<div className="carousel">
            {/* banner */}
            <div className="carousel_banner">
              <div className="carousel_banner_wrap" style={
                    {
                      transform:`translateX(-${stepIndex*100}%)`,
                      transitionDuration:`${duration}ms`
                    }
                  }>
                    {
                      items.map((item)=>{
                        return  <div key={item.key} className="carousel_banner_li" style={{color:item.color}}>
                              {/* 图片 */}
                              <img className="carousel_banner_li_img"src={item.img} alt="" />
                              {/* 主标题 */}
                              <div className="carousel_banner_li_wrap">
                                <div className="carousel_banner_li_title">{item.title}</div>
                                {/* 副标题 */}
                                <div className="carousel_banner_li_subtitle">
                                  {item.subtitle}
                                </div>
                              </div>
                            </div>
                      })
                    }
              </div>
            </div>
            {/* 步骤条 */}
            <div className="carousel_step">
              {
                  items.map((item,index)=>{
                  return  <div key={item.key} className={ `carouel_step_item  ${index===stepIndex ? 'active':''}`}></div>
              })
              }
            </div>
        </div>)
}