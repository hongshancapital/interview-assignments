/*
 * 测试浏览器：Chrome
 * 版本: 100.0.4896.127（正式版本） (x86_64)
 */

import React, { ReactElement, useState, useMemo } from 'react';
import useInterval from '../../hooks/useInterval';
import useWindowResize from '../../hooks/useWindowResize';
import Repeat from '../Repeat';
import Dot from './Dot';
import './Carousel.css';

interface CarouselProps {
  children: ReactElement | ReactElement[]
};

// todo: 组件测试
// todo: 抽取子组件？
// todo: 判断children边界
// todo: 处理页面窗口缩放后的计算
// todo: 手动切换

function Carousel(props: CarouselProps) {
  const children = props.children as ReactElement[];

  // 无内容时抛出异常
  if (!children) {
    throw new Error('Children cannot be empty.');
  }
  // 当前选中项
  const [activeIndex, setActiveIndex] = useState<number>(0);

  // 计数器，触发 interval 更新
  const [intervalIndex, setIntervalIndex] = useState<number>(0);

  // 滚动样式
  const [boxAnimation, setBoxAnimation] = useState<object>();

  let { width: windowWidth } = useWindowResize();


  // if (children.length === 1) {
  //   return children;
  // }

  const handleDotClick = (i: number) => {
    if (i === activeIndex) return;  // 点击当前项
    console.log('handleDotClick');
    setActiveIndex(i);
    animation(i);
    setIntervalIndex(intervalIndex + 1); // 重置 interval
  }

  let boxes: Array<React.ReactElement> = useMemo(() => {
    console.log('boxes', children);
    return children.map((o, i) => {
      return React.cloneElement(o, { style: { ...o.props.style, width: windowWidth }, key: i })
    });
  }, [children, windowWidth]);

  let dots = (
    <Repeat numTimes={children.length}>
      {(index: number) => (
        <Dot key={index} index={index} isMotion={activeIndex === index} onClick={handleDotClick} />
      )}
    </Repeat>
  );

  const animation = (i: number) => {
    setBoxAnimation({
      transform: `translate3d(-${window.innerWidth * i}px, 0px, 0px)`,
      transition: 'transform 500ms ease 0s'
    })
  }

  const autoPlay = () => {
    console.log('autoPlay');
    const newActiveIndex = activeIndex === boxes.length - 1 ? 0 : activeIndex + 1;
    setActiveIndex(newActiveIndex);
    animation(newActiveIndex)
  }
  
  useInterval(autoPlay, 3000, intervalIndex);

  return (
    <div className='carousel'>
      <div className='carousel-boxes' style={{ width: boxes.length ? windowWidth * 3 : windowWidth, ...boxAnimation }}>
        {boxes}
      </div>
      <ol className='carousel-dots'>
        {dots}
        {/* <Dots length={children.length} activeIndex={activeIndex} onClick={handleDotClick} /> */}
      </ol>
    </div>
  )
}

export default Carousel;
