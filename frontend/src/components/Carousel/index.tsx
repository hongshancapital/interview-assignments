import React, {  useEffect, useState } from 'react';
import './index.css';
interface CarouselProps {
      datas: {
        title: string; // 大标题
        subTitle: string; // 子标题
        bg: string | any; // 背景图片
        color?: string; // 文字颜色
      }[];
      speed?: number; // 切换速度 毫秒为单位 默认为3000
  }
/** 循环处理 定时循环*/
const useInterval = (callback: Function, interval: number) => {
  useEffect(() => {
    const i = setInterval(() => { callback(); }, interval);
    return () => clearInterval(i);
  })
}

/** 设置轮播滚动变化 */
const useActive = (total: number, speed: number) => {
  const [active, setActive] = useState(-1);
  useInterval(() => {
      setActive(active === total - 1 ? 0 : active + 1);
  }, speed);
  return active;
}

const Carousel: React.FC<CarouselProps> = ({datas, speed = 3000}) => {
  const active = useActive(datas.length, speed);
  return (
    <div className='carousel'>
      {datas.length > 0 && (
      <div className='carousel__container' style={{
        width: `${datas.length * 100}%`,
        transform: `translateX(-${100*active/datas.length}%)`
      }}>
        {datas.map((item, index) => {
          return (
            <div className='carousel__item'
            style={{
              backgroundImage: `url(${item.bg})`,
              width: `${100 / datas.length}%`
            }}
            key={index}>
              <p className="carousel__item-title" style={{color: item.color || '#000'}}>{item.title}</p>
              <p className="carousel__item-subtitle" style={{color: item.color || '#000'}}>{item.subTitle}</p>
            </div>
          )
        })}
      </div>
      )}
       <div className="carousel__tag-container">
        {datas.map((_item, _index) => {
          return (
          <div className="carousel__tag-item" key={_index}>
            <div className="carousel__tag-item-state" style={active === _index ? {
              transform: 'translateX(0)',
              transitionDuration: `${speed}ms`
            } : {}}></div>
          </div>
          )
        })}
      </div>
    </div>
  )
}

export default Carousel; 