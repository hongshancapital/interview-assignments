import React, { useState, useEffect } from "react";
import imageInfo from "../../common/images";
import SWITCHTIME from "../../common/time";
import CarouselInfo from "./carouselInfo/CarouselInfo";
import Indicator from "./indicator/Indicator";
import "./Carousel.css";


const Carousel = () => {
  const [activeIndex, setActiveIndex] = useState(0); // 对应索引

  useEffect(() => {
    const timer = setInterval(() => {
      onUpdateIndex(activeIndex + 1);
    }, SWITCHTIME);

    return () => timer && clearInterval(timer);
  });

  //更新索引
  const onUpdateIndex = (newIndex: number) => {
    if (newIndex < 0) {
      newIndex = imageInfo.length-1;
    }
    if (newIndex >= imageInfo.length) {
      newIndex = 0;
    }
    setActiveIndex(newIndex);
    replayAnimations();
  };

  //动画
  const replayAnimations = () => {
    document.getAnimations().forEach((animation) => {
      animation.cancel();
      animation.play();
    });
  };

  return (
    <div className='container'>
      <CarouselInfo activeIndex={activeIndex}/>
      <Indicator activeIndex={activeIndex} onUpdateIndex={onUpdateIndex} />
    </div>
  );
};

export default Carousel;

