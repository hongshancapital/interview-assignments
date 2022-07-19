import React from 'react';
import Dots from './dots';
import useActiveIndex from './useActiveIndex';
import Slider, { SliderDataProps } from './slider'; 
import './styles/carousel.css';

interface CarouselProps {
  /**
   * 走马灯数据参数
   */
  data: SliderDataProps[];
  /**
   * 自动切换时间，默认3000毫秒
   */
  duration?: number;
  /**
   * 从第几个开始，默认是第一个。
   */
  startIndex?: number;
}

/**
 * 走马灯
 * @param props 走马灯参数
 * @returns React.Node
 */
const Carousel: React.FC<CarouselProps> = (props) => {
  const { data, duration, startIndex } = props;

  const curIndex = useActiveIndex({ defaultIndex: startIndex, duration: duration, count: data.length});

  return (
    <div className='container'>
      <Slider data={data} curIndex={curIndex} />
      <Dots count={data.length} duration={duration} curIndex={curIndex} />
    </div>
  );
}

export default Carousel;
