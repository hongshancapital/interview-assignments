import { url } from "inspector";
import React,{ FC, useEffect, useRef, useState } from "react";
import Airpods from '../../assets/airpods.png';
import Iphone from '../../assets/iphone.png';
import Tablet from '../../assets/tablet.png';
import './carousel.css';
  const config = [
    {image:Iphone, background: '#111111', title: 'xPhone', desc:'Lots to love. Less to spend. Starting at $399.', color: '#fff'},
    {image:Tablet, background: '#FAFAFA', title: 'Tablet', desc:'Just the right amount of everything.'},
    {image:Airpods, background: '#F1F1F3', title: 'Buy a Tablet or xPhone for college. Get arPods.', desc:''},
  ]

const  Carousel: FC = () => {
  const [cur,setCur] = useState<number>()
  const timer = useRef<any>(null)

  /** 定时器控制轮播 */
  const loop = () => {
    if (timer.current) clearInterval(timer.current)
    timer.current = setInterval(()=>{
      setCur(pre => pre === config.length - 1 ? 0 : (pre || 0) + 1)
    },3000)
  }

  /** 点击手动切换当前活动的item */
  const changeCur = (index: number) => {
    setCur(index)
    loop()
  }

  useEffect(()=>{
    setCur(0)
    loop()
    return () => {
      if (timer.current) clearInterval(timer.current)
    }
  }, [])

  return (
    <div className="carousel">
      <div className="carousel-container">
        {
          config.map((item,index)=> ( 
            <div className="carousel-item" style={{transform: `translateX(calc(${index - (cur || 0) } * 100%))`, color:'#000000',  backgroundImage: `url(${item.image})`}} key={index}>
              <div className="carousel-item-title" style={{color: `${item.color}`}}>{item.title}</div>
              <div className="carousel-item-desc" style={{color: `${item.color}`}}>{item.desc}</div>
            </div>
          ))
        }
        <div className="carousel-dots">
          {
            Array.from({length: config.length}).map((item,index) => <div className={`carousel-dots-item ${index === cur ? 'carousel-dots-item--active' : ''}`} onClick={()=>changeCur(index)}></div> )
          }
        </div>
      </div>
    </div>
  )
}

export default Carousel