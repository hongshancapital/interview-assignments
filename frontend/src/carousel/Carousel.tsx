import React, { useEffect, useState } from 'react';
import './carousel.css';

export interface CarouselItem {
  bgImgUrl?: string;
  title: string;
  text?: string;
  fontColor?: string;
}

interface CarouselProp {
  items: Array<CarouselItem>
}

export function Carousel(props: CarouselProp) {
  const [activeId, setActiveId] = useState(-1);
  const items = props.items;

  useEffect(() => {
    // Init the activeId
    setActiveId(0);
  }, []);

  useEffect(() => {
    const interval = setInterval(() => {
      setActiveId(activeId === items.length - 1 ? 0 : activeId + 1);
    }, 3000);
    return () => {
      clearInterval(interval);
    }
  });

  function getPageIndicators() {
    let i = 0;
    const indicators = [];
    while (i < items.length) {
      indicators.push(
        <div className={`indicator ${activeId === i ? 'active' : ''}`} key={i}>
          <div className="progress"></div>
        </div>
      );
      i++;
    }
    return indicators;
  }

  if (items.length) {
    return (
      <div className="carousel-container">
        <div className="carousel-item-wrapper" style={{transform: `translateX(-${Math.max(100 * activeId / items.length, 0)}%)`}}>
          {
            items.map((item, index) => {
              return <div key={`carousel-item-${index}`} className="carousel-item"
                          style={{
                            backgroundImage: `url(${item.bgImgUrl})`,
                            color: `${item.fontColor}`
                          }}
              >
                <span className="title">{item.title}</span>
                <span className="text">{item.text}</span>
              </div>;
            })
          }
        </div>
        <div className="page-indicator">{getPageIndicators()}</div>
      </div>
    );

  } else {
    return <div className="carousel-container"></div>
  }
}
