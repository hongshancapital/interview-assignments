import React, { ReactNode } from "react";
import { usePage } from '../../hooks';

import './index.css';

type ICarouselProps = {
  children?: Array<ReactNode>;
  duration?: number
};

const Carousel: React.FC<ICarouselProps> = (props: ICarouselProps) => {
  const { children = [], duration = 3000 } = props;
  const count = children?.length || 0;
  const { pageIndex, nextPage, jumpPage } = usePage(0, count);

  // -- 进度条样式 --
  const progressStyle = {
    animationName: "slide-animation",
    animationDuration: `${duration / 1000}s`,
  };

  return (
    <div className='carousel-wrapper' data-testid="carousel-wrapper">
      <div
        className="carousel-container"
        data-testid="carousel-container"
        style={{
          transform: `translateX(${-pageIndex * 100}%)`,
          transitionDuration: `${0.7}s`,
        }}
      >
        {children}
      </div>
      <div className="carousel-indicator-group">
        {children.map((item, index: number) => {
          return (
            <div
              className="carousel-indicator"
              data-testid="carousel-indicator"
              key={index}
              onClick={() => {
                jumpPage(index);
              }}
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