import React from 'react';
import { prefix } from '../util';

export interface CarouselNaviProps {}

// TODO 导航控制功能
export const CarouselNavi: React.FC<CarouselNaviProps> = function (props) {
  return (
    <>
      <div className={prefix("carousel-navi carousel-navi__next")}>right &gt; </div>
      <div className={prefix("carousel-navi carousel-navi__prev")}> &lt; left</div>
    </>
  );
};
