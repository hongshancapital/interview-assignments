import React, { useEffect, useState } from 'react';
import './style.scss';

interface Iprops {
  // 切换的子元素 至少大于一项。考虑用这种形式是因为担心数据样式过多，例如可能第一屏中金额需要红色突出？并且像背景图这种样式不应该在组件中。组件重在功能最基础的样式，内容高度定义化交给使用者
  children: JSX.Element[],
  // 样式名 多处使用时，可分别设置最外层盒子样式避免样式污染。可传多个并用空格分开
  className?: string,
  // 是否自动切换 默认值：true
  autoPlay?: boolean;
  // 自动切换的时间间隔 单位：毫秒 默认值：3000
  delay?: number;
  // 切换速度，即 slider自动滑动开始到结束的时间 单位: 毫秒，默认值: 300
  speed?: number;
}
function Carousel({children, className = '', autoPlay = true, delay = 3000, speed = 300 }: Iprops) {
  const [activeIndex, setActiveIndex] = useState(0);
  // 自动运行
  useEffect(() => {
    if (autoPlay) {
      let timer = setInterval(() => {
        setActiveIndex(activeIndex < children.length -1  ? activeIndex + 1 : 0);
      }, delay)
      return () => clearInterval(timer);
    }
  }, [activeIndex, autoPlay, delay, children.length])

  // 点击切换
  function changeActiveIndex(i: number) {
    setActiveIndex(i);
  }

  return <div className={`carousel-wrap ${className}`}>
    <div className="carousel-list" style={{
      width: `${children.length * 100}vw`,
      transitionDuration: `${speed}ms`,
      transform: `translate3d(-${activeIndex / 3 * 100}%, 0, 0)`
    }}>
      {children}
    </div>
    <div className="carousel-dots">
      <ul className="dot-list">
        {children.map((item, index) => (
          <li key={index} className={activeIndex === index ? 'dot-item active' : 'dot-item'} onClick={() => changeActiveIndex(index)}>
            <i className="dot-progress" style={activeIndex === index && autoPlay ? { transitionDuration: `${delay}ms` } : {}}></i>
          </li>
        ))}
      </ul>
    </div>
  </div >
}

export default Carousel
