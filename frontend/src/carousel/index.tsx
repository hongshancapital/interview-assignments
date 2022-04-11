import React, { useState, useRef } from 'react';
import Dots from './dots';
import './carousel.css';

interface CarouselDataProps {
  /**
   * title文字，字符串数组，每个元素换行显示
   */
  title?: string[];
  /**
   * 描述文字，字符串数组，每个元素换行显示
   */
  desc?: string[];
  /**
   * 图片
   */
  img: string;
  /**
   * title和desc文字颜色
   */
  color: string;
}

interface CarouselProps {
  /**
   * 走马灯数据参数
   */
  datas: CarouselDataProps[];
  /**
   * 自动切换时间，默认3000毫秒
   */
  duration?: number;
}

/**
 * 走马灯
 * @param props 走马灯参数
 * @returns React.Node
 */
const Carousel: React.FC<CarouselProps> = (props) => {
  const { datas, duration } = props;
  const [carouselStyle, setCarouselStyle] = useState({ width: `${datas.length * 100}vw`, transform: 'translateX(0px)' });
  const activeIndex = useRef(0);

  const setActive = (index: number) => {
    activeIndex.current = index;
    setCarouselStyle({
      ...carouselStyle,
      transform: `translateX(${-(index * 100)}vw)`,
    });
  };

  return (
    <div className='container'>
      <div className='carousel' style={carouselStyle} data-testid='carousel' data-index={activeIndex.current}>
        {datas.map((item, index) => <div className='carousel-item' key={index} style={{ backgroundImage: `url(${item.img})` }}>
          <div className='carousel-item-text' style={{ color: item.color }}>
            {Array.isArray(item.title) && item.title.length > 0 ? item.title.map((t, tindex) => <h1 key={tindex}>{t}</h1>): null}
            {Array.isArray(item.desc) && item.desc.length > 0 ? item.desc.map((d, dindex) => <span key={dindex}>{d}</span>): null}
          </div>
        </div>)}
      </div>
      <Dots count={datas.length} duration={duration} changeImg={setActive} />
    </div>
  );
}

export default Carousel;
