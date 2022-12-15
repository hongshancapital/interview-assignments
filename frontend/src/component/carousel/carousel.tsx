import React, { ReactNode } from "react";
import { usePage } from '../../hooks';

import './index.css';

type ICarouselProps = {
  children?: Array<ReactNode>;
  duration?: number
};

const Carousel: React.FC<ICarouselProps> = (props: ICarouselProps) => {
  const { children = [], duration = 3000 } = props
  const count = children?.length || 0;
  const { pageIndex, nextPage } = usePage(0, count);

  //进度条样式
  const progressStyle = {
    animationName: "slide-animation",
    animationDuration: `${duration / 1000}s`,
  };

  return (
    <div className='carousel-wrapper'>
      <div
        className="carousel-container"
        style={{
          position: 'absolute',
          width: count * 100 + '%',
          transform: `translateX(${-pageIndex * 100}%)`,
        }}
      >
        {children}
      </div>
      <div className="carousel-indicators">
        {children.map((item: any, index: number) => {
          return (
            <div
              className="carousel-indicator"
              key={index}
              data-testid="carousel-indicator"
            >
              <div
                className="carousel-indicator-inside"
                style={pageIndex === index ? progressStyle : {}}
                onAnimationEnd={() => nextPage()}
              ></div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default Carousel;