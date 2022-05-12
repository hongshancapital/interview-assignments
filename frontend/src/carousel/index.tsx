import React, { useEffect, useRef } from 'react';
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
  width?: number;
  height?: number;
}

/**
 * 走马灯
 * @param props 走马灯参数
 * @returns React.Node
 */
const Carousel: React.FC<CarouselProps> = (props) => {
  const {
    duration,
    beforeChange,
    afterChange,
    width = window.screen.width,
    height = window.screen.height,
    children,
  } = props;
  const preIndex = useRef(-1);
  const containeRef = useRef<HTMLDivElement>(null);

  const { curIndex, setCurIndex } = useActiveIndex({ duration, count: React.Children.count(props.children) });

  useEffect(() => {
    beforeChange?.(preIndex.current, curIndex);
    if (preIndex.current !== -1) {
      afterChange?.(preIndex.current);
    }
    preIndex.current = curIndex;
  }, [curIndex, beforeChange, afterChange]);

  useEffect(() => {
    if (containeRef.current) {
      containeRef.current.style.setProperty('--width', `${width}px`);
      containeRef.current.style.setProperty('--height', `${height}px`);
      containeRef.current.style.setProperty('--curIndex', curIndex.toString());
    }
  }, [curIndex, width, height]);

  return (
    <div className="container" ref={containeRef}>
      <div className="carousel" data-testid="carousel">
        {React.Children.map(children, (child, index) => (
          <div key={index}>{child}</div>
        ))}
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
