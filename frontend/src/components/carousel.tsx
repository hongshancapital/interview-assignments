import React, { useEffect, useState } from "react";
import "./carousel.css";
interface CarouselInfo{
    img:string;
    title?:string|string[];
    subTitle?:string|string[];
    color?:string
}
interface CarouselProps{
    data:CarouselInfo[];
    delay?:number
}
const Carousel:React.FC<CarouselProps>=({data=[],delay=3000})=>{
    const [index,setIndex] =  useState<number>(0)
    useEffect(()=>{
        if(!data.length) return
        let timer = window.setTimeout(() => {
            setIndex(index >= data.length - 1 ? 0 : index + 1);
            timer = 0;
          }, delay);
          return () => {
            if (timer) {
              window.clearTimeout(timer);
            }
          }
    },[data,delay,index])
    console.log(data)
    return (
        <div className="carousel-box">
            {/* 图片轮播 */}
            <div className="carousel-ul" style={{ transform: `translate3d(-${index * 100}%, 0, 0)` }}>
                {
                    data.map((item,index)=>
                        <div className="carousel-li" key={index} style={{ backgroundImage: `url(${item.img})`}}>
                            <div className="carousel_text" style={{ color: item.color }}>
                                {
                                    (item.title instanceof Array ? item.title : [item.title]).map(title => (
                                        <div className="carousel_title">{title}</div>
                                    ))
                                    }
                                    {
                                    (item.subTitle instanceof Array ? item.subTitle : [item.subTitle]).map(subTitle => (
                                        <div className="carousel_subtitle">{subTitle}</div>
                                    ))
                                }
                            </div>
                        </div>
                    )
                }
            </div>
            {/* 按钮 */}
            <div className="carousel-bottom">
                {
                    data.map((item, i) =>
                        <div className={`carousel-bottom-line ${index === i ? 'current' : ''}`} key={i}
                        onClick={() => setIndex(i)}
                        >
                        {
                            index === i &&
                            <div className="carousel-progress" style={{ animationDuration: delay / 1000 + 's' }}></div>
                        }
                        </div>
                    )
                }
            </div>
        </div>
    )
}
export default Carousel

