import React, { useEffect, useState } from 'react';
import './style.scss';
interface IItem {
  title: string;
  subTitle?: string;
  bgImg: string;
}
interface Iprops {
  // 滑动数据
  items: IItem[],
  // 样式名 多处使用时，可分别设置最外层盒子样式避免样式污染。可传多个并用空格分开
  className?: string,
  // 是否自动切换 默认值：true
  autoPlay?: boolean;
  // 自动切换的时间间隔 单位：毫秒 默认值：3000
  delay?: number;
  // 切换速度，即 slider自动滑动开始到结束的时间 单位: 毫秒，默认值: 300
  speed?: number;
}
function Carousel({ items = [], className = '', autoPlay = true, delay = 3000, speed = 300 }: Iprops) {
  const [activeIndex, setActiveIndex] = useState(0);
  // 自动运行
  useEffect(() => {
    if (autoPlay) {
      let timer = setInterval(() => {
        setActiveIndex(activeIndex < items.length - 1 ? activeIndex + 1 : 0);
      }, delay)
      return () => clearInterval(timer);
    }
  }, [activeIndex, autoPlay, delay, items.length])

  // 点击切换
  function changeActiveIndex(i: number) {
    setActiveIndex(i);
  }

  return <div className={`carousel-wrap ${className}`}>
    {/*  style={{
      width: `${items.length * 100}vw`,
      transitionDuration: `${speed}ms`,
      transform: `translate3d(-${activeIndex / 3 * 100}%, 0, 0)`
    }} */}
    <div className="carousel-list">
      {items.map((item: IItem, index: number) => (
        <div className={['carousel-item', index === activeIndex ? 'active' : '', index === activeIndex - 1 || index === activeIndex + items.length - 1 ? 'prev' : ''].join(' ')} key={index} style={{ transitionDuration: `${speed}ms` }}>
          <div className="carousel-item-inner">
            <h2 className="title">{item.title}</h2>
            {item.subTitle && <p className="sub-title">{item.subTitle}</p>}
          </div>
        </div>
      ))}
    </div>
    <div className="carousel-dots">
      <ul className="dot-list">
        {items.map((item, index) => (
          <li key={index} className={activeIndex === index ? 'dot-item active' : 'dot-item'} onClick={() => changeActiveIndex(index)}>
            <i className="dot-progress" style={activeIndex === index && autoPlay ? { transitionDuration: `${delay}ms` } : {}}></i>
          </li>
        ))}
      </ul>
    </div>
  </div >
}

export default Carousel
