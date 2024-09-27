import React from "react";
import Slider from './slider';
export interface CarouselProps {
  autoplay: Boolean; //是否自动播放
  dots: Boolean; //	是否显示指示点	
  dotPosition: 'top'|'bottom'|'left'|'right'; //	指示点的位置，可选 top bottom left right
  children: React.ReactNode;
  needArrows: Boolean;// 是否展示箭头
}
const Carousel = (props: Partial<CarouselProps>) => {
  return (<Slider {...props} />)
}

export default Carousel;