import React, { useEffect, useRef, useMemo } from 'react';
import Dots from './dots';
import useActiveIndex from './useActiveIndex';
import './styles/carousel.scss';

interface CarouselProps {
  /**
   * 走马灯数据参数
   */
  // data: SliderDataProps[];
  /**
   * 自动切换时间，默认3000毫秒
   */
  duration?: number;
  /**
   * 切换之后的回调
   */
  afterChange?: (current: number) => void;
  /**
   * 切换之前的回调
   */
  beforeChange?: (from: number, to: number) => void;
}

/**
 * 走马灯
 * @param props 走马灯参数
 * @returns React.Node
 */
const Carousel: React.FC<CarouselProps> = (props) => {
  const { duration, beforeChange, afterChange } = props;
  const preIndex = useRef(-1);

  const { curIndex, setCurIndex } = useActiveIndex({ duration, count: React.Children.count(props.children) });

  useEffect(() => {
    beforeChange?.(preIndex.current, curIndex);
    if (preIndex.current !== -1) {
      afterChange?.(preIndex.current);
    }
    preIndex.current = curIndex;
  }, [curIndex, beforeChange, afterChange]);

  const sliderStyle = useMemo(() => {
    return {
      transform: `translateX(${-(curIndex * 100)}vw)`,
    };
  }, [curIndex]);

  return (
    <div className="container">
      <div className="carousel" style={sliderStyle}>
        {props.children}
      </div>
      <Dots
        count={React.Children.count(props.children)}
        duration={duration}
        curIndex={curIndex}
        setCurIndex={setCurIndex}
      />
    </div>
  );
};

export default Carousel;
