import React from 'react';
import './styles/slider.css';

export interface SliderDataProps {
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

interface SliderProps {
  data: SliderDataProps[];
  curIndex: number;
}

/**
 * 滑动图片组件
 * @param props 参数
 * @returns 滑动图片组件
 */
function Slider(props: SliderProps) {
  const { data, curIndex } = props;

  const sliderStyle = {
    transform: `translateX(${-(curIndex * 100)}vw)`,
  }

  return (
    <div className='slider' style={sliderStyle}>
      {data.map((item, index) => <div className='slider-item' key={index} style={{ backgroundImage: `url(${item.img})` }}>
        <div className='slider-item-text' style={{ color: item.color }}>
          {Array.isArray(item.title) && item.title.length > 0 ? item.title.map((t, tindex) => <h1 key={tindex}>{t}</h1>): null}
          {Array.isArray(item.desc) && item.desc.length > 0 ? item.desc.map((d, dindex) => <span key={dindex}>{d}</span>): null}
        </div>
      </div>)}
    </div>
  );

}

export default Slider;
