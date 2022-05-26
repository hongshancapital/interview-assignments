import React, { useState } from 'react';
import './index.css';

export interface CarouselProps {
  children: React.ReactNode
}

function Carousel({ children }: CarouselProps) {
  // 当前索引
  const [ current, setCurrent ] = useState<number>(0);

  const newChildren = React.Children.toArray(children);

  const handleAnimationEnd = () => {
    setCurrent(current === newChildren.length - 1 ? 0 : current + 1);
  }

  // 平移位置
  const wrapperStyle = {
    transform: `translateX(-${current * 100}%)`
  }

  return <div className="carousel">
    <div className="wrapper" style={wrapperStyle}>
      {
        newChildren.map((child, index) => <div key={index} className="item">{child}</div>)
      }
    </div>
    {
      newChildren.length > 1 && <div className="dots">
        {
          newChildren.map((_, index) => 
            <div key={index} className={index === current ? 'cur' : ''}>
                <div className="progress" onAnimationEnd={handleAnimationEnd}></div>
            </div>
          )  
        }
      </div>
    }
  </div>;
}

export default Carousel;