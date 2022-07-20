/*
 * 测试浏览器：Chrome
 * 版本: 100.0.4896.127（正式版本） (x86_64)
 */

import React, { FC, ReactElement, useState } from 'react';
import useInterval from '../../hooks/useInterval';
import useWindowResize from '../../hooks/useWindowResize';
import Repeat from '../Repeat';
import Dot from './Dot';
import './Carousel.scss';

interface CarouselProps {
  children: ReactElement | ReactElement[]
};

const Carousel: FC<CarouselProps> = (props: CarouselProps) => {
  // 无 children 时抛出异常
  if (!props.children) {
    throw new Error('Children cannot be empty.');
  }

  const children = Array.isArray(props.children) ? props.children : [props.children];

  // 当前选中项
  const [activeIndex, setActiveIndex] = useState(0);

  // 计数器，触发 interval 更新
  const [intervalCounter, setIntervalCounter] = useState(() => children.length > 1 ? 0 : -1); // 当只有一个 banner 时不加载 interval

  // 滚动样式
  const [boxAnimation, setBoxAnimation] = useState({});

  // 实时窗口宽度
  let { width: windowWidth } = useWindowResize();

  const handleDotClick = (i: number) => {
    // 阻止点击当前项
    if (i === activeIndex) return;
    // 切换选中项
    setActiveIndex(i);
    // 执行滚动
    animation(i);
    // 重置定时器
    setIntervalCounter(intervalCounter + 1);
  }

  let panels = children.map((child, i) => {
    return React.cloneElement(
      child, 
      {
        className: `carousel-panel ${child.props.className || ''}`, // 注入容器样式
        style: {
          ...child.props.style,
          width: windowWidth  // 注入宽度
        },
        key: i
      }
    )
  });

  let dots = children.length > 1 && (
    // 复制点击按钮
    <Repeat numTimes={children.length}>
      {(index: number) => (
        <Dot key={index} index={index} isMotion={activeIndex === index} onClick={handleDotClick} />
      )}
    </Repeat>
  );


  const animation = (i: number) => {
    setBoxAnimation({
      transform: `translateX(-${window.innerWidth * i}px)`,
      transition: 'transform 500ms ease 0s'
    })
  }

  const autoPlay = () => {
    const nextIndex = activeIndex === panels.length - 1 ? 0 : activeIndex + 1;
    setActiveIndex(nextIndex);
    animation(nextIndex);
  }
  
  useInterval(autoPlay, 3000, intervalCounter);

  return (
    <div className='carousel'>
      <div className='carousel-panels' style={{ width: windowWidth * panels.length, ...boxAnimation }}>
        {panels}
      </div>
      <ol className='carousel-dots'>
        {dots}
      </ol>
    </div>
  )
}

export default Carousel;
