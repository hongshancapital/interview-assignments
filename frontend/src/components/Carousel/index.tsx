import React, { useState } from 'react';
import {TimerBar} from '../TimerBar'
import './index.scss'

interface IdataItem {
    img: string;
    desc: JSX.Element;
}
interface ICarousel {
  // 轮播图数据（必传）
  items: IdataItem[];
}

const Carousel = (props: ICarousel) => {
  const { items } = props;
  const [translatex, setTranslatex] = useState('0')

  // 轮播下一个图片
  const nextImage = (index: number) => {
    setTranslatex(`${index * -100}vw`)
  }
  return (<div className='CarouselBox' >
    <div className='imgboxs' style={{transform: `translatex(${translatex})`}}>
        {items.map(item => {
            return  <div className={`imgbox ${item.img}`}>
                <div className={`img ${item.img}`}>
                    {item.desc}
                </div>
            </div>
        })}
    </div>
    <TimerBar className='tb' num={items.length} onTime={nextImage}/>
  </div>)
}

export { Carousel };