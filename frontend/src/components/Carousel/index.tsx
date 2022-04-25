/*
 * @Author: 马俊鸿 zxmajunhong@forxmail.com
 * @Date: 2022-04-24 19:59:22
 * @LastEditTime: 2022-04-25 16:30:08
 * @Description: 轮播组件
 */

import React, {  useEffect, useState } from 'react';
import './index.css';

/** 循环处理 */
const useInterval = (callback: Function, interval: number) => {
  useEffect(() => {
    const i = setInterval(() => { callback(); }, interval);
    return () => clearInterval(i);
  })
}

/** 设置轮播滚动变化 */
const useActive = (total: number, speed = 3000) => {
  const [active, setActive] = useState(0);
  useInterval(() => {
      setActive(active === total - 1 ? 0 : active + 1);
  }, speed);
  return active;
}

const Carousel: React.FC<TCarouselProps> = ({datas, speed}) => {
  const active = useActive(datas.length, speed);

  return (
    <div className='carousel'>
      {datas.length > 0 && (
      <div className='carousel__container' style={{
        width: `${datas.length * 100}%`,
        transform: `translateX(-${100*active/datas.length}%)`
      }}>
        {datas.map((it, i) => {
          return (
            <div className='carousel__item'
            style={{
              backgroundImage: `url(${it.bg})`,
              width: `${100 / datas.length}%`
            }}
            key={i}>
              <p className="carousel__item-title" style={{color: it.color || '#000'}}>{it.title}</p>
              <p className="carousel__item-subtitle" style={{color: it.color || '#000'}}>{it.subTitle}</p>
            </div>
          )
        })}
      </div>
      )}
    </div>
  )
}

export default Carousel;