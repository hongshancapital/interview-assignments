/* eslint-disable react-hooks/exhaustive-deps */
import React, { useState, useEffect } from 'react';
import { SettingType } from './types';
import './index.css';

function Carousel(props: SettingType) {
  const { content, intervalTime } = props;
  const [currentIndex, setCurrentIndex] = useState(0);
  const [process, setProcess] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      start();
    }, intervalTime);
    return () => clearInterval(timer);
  }, [currentIndex]);

  const start = () => {
    if (currentIndex < content.length - 1) {
      setCurrentIndex(currentIndex + 1);
    } else {
      setCurrentIndex(0);
    }
  };

  const computeProcess = () => {
    if (process < 40) {
      setProcess(process + 0.28);
    } else {
      setProcess(0);
    }
  };

  requestAnimationFrame(computeProcess);

  return (
    <div className="carousel-container">
      <div
        className="carousel-container-sliderbox"
        style={{ left: `${-1 * currentIndex * 100}%` }}
      >
        {content &&
          content.map((ContentItems, index: number) => {
            return (
              <div key={index} className="carousel-container-sliderbo-items">
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
                  <span style={{ width: `${process}px` }}></span>
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
