import React,{ FC, useState, useEffect, useRef } from 'react';
import './carousel.css';
import { useWindowSize } from '../../hooks/useWindowSize';

interface CarouselProps {
  options?: {
    duration?: number;
    hasProgress?: boolean;
  };
}

const defaultOptions = {
  duration: 2500,
  hasProgress: true,
};

let activeIndex = 0; // current active index

const Carousel: FC<CarouselProps> = (props) => {
  const { children, options } = props;
  const carouselOptions = { ...defaultOptions, ...(options || {})};
  const boxRef = useRef(null);
  // state
  const [boxStyle, setBoxStyle] = useState({
    transform: `translateX(0px)`,
  });
  const [ progressStyle ] = useState({
    animation: `move ${carouselOptions.duration/1000}s linear`
  });
  const [ width ] = useWindowSize(); // 监听window窗口大小变化
  // effect
  useEffect(() => {
    const boxWidth = (boxRef?.current as unknown as HTMLDivElement)?.clientWidth;
    const timerId = setInterval(() => {
      activeIndex = activeIndex === 2 ? 0 : activeIndex + 1;
      setBoxStyle({
        ...boxStyle,
        transform: `translateX(-${boxWidth * activeIndex}px)`
      })
    }, carouselOptions.duration);
    return () => clearInterval(timerId);
  }, [width]);
  // render
  return <div className="carousel">
    <div className="carousel-box" ref={boxRef} style={boxStyle}>
      {children}  
    </div>
    {
      carouselOptions.hasProgress ? <div className="carousel-progress">
        {
          React.Children.map(children, (item, index) => <div key={index}>
            {
              activeIndex === index ? <span style={progressStyle}></span> : null
            }
          </div>)
        }
      </div> : null
    }
  </div>;
}

export default Carousel;