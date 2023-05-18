import React, { useEffect, useRef, useState } from 'react';
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
  } = props;
  const [curIndex, setCurIndex] = useState(-1);
  const preIndex = useRef(curIndex);
  const { nextIndex, setNextIndex } = useActiveIndex({ duration, count: data.length });

  /**
   * 触发before回调
   */
  useEffect(() => {
    if (curIndex !== nextIndex) {
      curIndex !== -1 && beforeChange?.(curIndex, nextIndex);
      preIndex.current = curIndex;
      setCurIndex(nextIndex);
    }
  }, [curIndex, nextIndex, beforeChange]);

  /**
   * 触发after回调
   */
  useEffect(() => {
    preIndex.current !== -1 && afterChange?.(preIndex.current);
  }, [curIndex, afterChange]);

  return (
    <div className="container">
      <div className={`carousel active-${curIndex}`} data-testid="carousel">
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
      <Dots count={data.length} duration={duration} curIndex={curIndex} setCurIndex={setNextIndex} />
    </div>
  );
};

export default Carousel;
