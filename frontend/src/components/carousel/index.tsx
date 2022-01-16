import React, { useState, useEffect } from 'react';
import './index.css';

interface Src {
  src: string, // 图片
  id: string, // 唯一ID值
  title: string, // 标题---HTML
  content: string, // 内容---HTML
  backgroundColor?: string, // 图片背景色
  theme?: string, // 主题：black、white等可扩展
}

interface Args {
  srcs: Array<Src>, // 背景图数组
  delay: number, // 图片切换时间
  dur: number, // 当前图片停留时间
};

export default function Carousel(context: Args) {

  const [index, setIndex]: [number, Function] = useState(0);

  const onSwipe = ()=>{
    if (index>=context.srcs.length-1) {
      setIndex(0)
    } else {
      setIndex(index+1);
    }
  };

  useEffect(()=>{
    const timer = setInterval(onSwipe, (context.dur)*1000);
    return ()=>clearInterval(timer);
  }, [context, index]);

  return (
    <div className='com-carousel w-per-100 h-per-100'>
      <ul 
        className='carousel-container h-per-100' 
        style={{width: `${100*context.srcs.length}%`, marginLeft: `${-index*100}%`, transitionDuration: `${context.delay}s`}}
      >
        {
          context.srcs.map((src: Src)=>(
            <li
              className={`container-item theme-${src.theme||''}`}
              key={src.id}
              style={{backgroundImage: `url(${src.src})`, backgroundColor: src.backgroundColor}}
            >
              <h3 className='item-title' dangerouslySetInnerHTML={{__html: src.title}}></h3>
              <section className='item-content' dangerouslySetInnerHTML={{__html: src.content}}></section>
            </li>
          ))
        }
      </ul>
      <ul
        className='carousel-bars'
      >
        {
          context.srcs.map((src: Src, idx: number)=>(
            <li 
              key={src.id}
              className={`bars-item ${idx===index?"active":""}`}
              style={{animationDuration: `${context.dur}s`}}
            ></li>
          ))
        }
      </ul>
    </div>
  )
}