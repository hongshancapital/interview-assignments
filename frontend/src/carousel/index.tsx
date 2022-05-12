import React, { useEffect, useRef } from 'react';
import Dots from './dots';
import useActiveIndex from './useActiveIndex';
import './styles/carousel.scss';

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
  /**
   * 背景色
   */
  bgColor: string;
}

interface CarouselProps {
  data: CarouselDataProps[];
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
    data,
    duration,
    beforeChange,
    afterChange,
    width = window.screen.width,
    height = window.screen.height,
  } = props;
  const preIndex = useRef(-1);
  const containeRef = useRef<HTMLDivElement>(null);

  const { curIndex, setCurIndex } = useActiveIndex({ duration, count: data.length });

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
    <div className="container" ref={containeRef} data-testid="container">
      <div className="carousel">
        {data?.map((d, index) => (
          <div key={index}>
            <div
              className="carousel-item"
              style={{
                backgroundColor: d.bgColor,
                color: d.color,
              }}
            >
              <div className="carousel-item-text">
                {d.title?.map((t, tindex) => (
                  <h1 key={tindex}>{t}</h1>
                ))}
                {d.desc?.map((d, dindex) => (
                  <p key={dindex}>{d}</p>
                ))}
              </div>
              <img src={d.img} alt="" />
            </div>
          </div>
        ))}
      </div>
      <Dots count={data.length} duration={duration} curIndex={curIndex} setCurIndex={setCurIndex} />
    </div>
  );
};

export default Carousel;
