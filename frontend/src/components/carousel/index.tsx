import React from "react";
import { slideItem } from "./defination";
import Slide from "./slide";
import Indicator from "./indicator";
import "./index.css";

type carouselProps = {
  items: slideItem[];
};

const slideTransDuration = 500;
const slideShowDuration = 3000;

function Carousel(props: carouselProps) {
  // 当前图片索引
  const [slideIndex, setSlideIndex] = React.useState(0);
  const nextSlideIndex = (slideIndex + 1) % props.items.length;

  // 因为第一次展示没有转场，计时器需要修正下时间
  const [adjustTime, setAdjustTime] = React.useState(slideTransDuration);
  const carouselTimer = React.useRef(0);
  if (!carouselTimer.current) {
    // 防止执行多次
    carouselTimer.current = window.setTimeout(() => {
      carouselTimer.current = 0;
      setSlideIndex(nextSlideIndex);
      setAdjustTime(0);
    }, slideShowDuration - adjustTime);
  }

  const distance = -(window.innerWidth * slideIndex);
  return <div className="carousel">
    <div className="carousel-wrapper"
      style={{
        transitionDuration: slideTransDuration + 'ms',
        transform: 'translate3d(' + distance + 'px, 0px, 0px)',
      }}>
      {
        props.items.map((item, index) => <Slide key={index} sildeData={item}></Slide>)
      }
    </div>
    <div className="carousel-indicators">
      {
        props.items.map((item, index) => <Indicator key={index} indicatorData={{
          active: index === slideIndex,
          duration: slideShowDuration - adjustTime,
          adjust: adjustTime,
        }}></Indicator>)
      }
    </div>
  </div>;
}

export default Carousel;
