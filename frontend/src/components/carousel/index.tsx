import React, { useState, useEffect, useCallback } from 'react';
import { SettingType } from './types';
import './index.scss';

function Carousel(props: SettingType) {
  const { content, intervalTime } = props;
  const [currentIndex, setCurrentIndex] = useState(0);
  const [process, setProcess] = useState(0);
  const [isTransition, setTransition] = useState('none');
  const start = useCallback(() => {
    return setInterval(() => {
      if (currentIndex < content.length - 1) {
        setCurrentIndex(currentIndex + 1);
      } else {
        setCurrentIndex(0);
      }
    }, intervalTime);
  }, [content.length, currentIndex, intervalTime]);

  useEffect(() => {
    const timer = start();
    return () => clearInterval(timer);
  }, [currentIndex, start]);

  useEffect(() => {
    setTransition('width 2s');
    setProcess(40);
    return () => {
      setTransition('none');
      setProcess(0);
    };
  }, [currentIndex]);

  return (
    <div className="carousel-container">
      <div
        className="carousel-container-sliderbox"
        style={{ left: `${-1 * currentIndex * 100}%` }}
      >
        {content &&
          content.map((ContentItems, index: number) => {
            return (
              <div key={index} className="carousel-container-sliderbox-items">
                {ContentItems}
              </div>
            );
          })}
      </div>
      <div className="carousel-container-navigator">
        <ul>
          {new Array(content.length).fill(1).map((i, index) => {
            return (
              <li key={index}>
                {currentIndex === index ? (
                  <span
                    style={{ transition: isTransition, width: `${process}px` }}
                  ></span>
                ) : (
                  <span />
                )}
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
}

export default Carousel;
