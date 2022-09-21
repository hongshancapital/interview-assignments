import React, { useState } from 'react';
import './carousel.css';

import FullscreenBanner from './FullscreenBanner';
import ProgressBar from './ProgressBar';
import useAnimation from './hooks/useAnimation';

import carouselData from '../../mockData/carousel';
const delay = 3000;

function Carousel() {

  const [currentIndex, setCurrentIndex] = useState(0);

  // 位置
  // const moveto = (moveIndex: number) => {
  //   setCurrentIndex(moveIndex);
  // }

  const resetInterval = useAnimation(() => {
    const moveIndex = currentIndex === carouselData.length - 1 ? 0 : currentIndex + 1;
    // moveto(moveIndex);
    setCurrentIndex(moveIndex);
  }, delay);

  // 点击进度条需要重新开始定时器
  const clickProgressBar = (index: number) => {
    resetInterval();
    setCurrentIndex(index);
  }

  return (
    <div className='carousel'>
      <FullscreenBanner
        carouselData={carouselData}
        currentIndex={currentIndex}
        delay={delay}
      />
      <ProgressBar
        carouselData={carouselData}
        currentIndex={currentIndex}
        clickProgressBar={clickProgressBar}
        delay={delay}
      />
    </div>
  )
}

export default Carousel;