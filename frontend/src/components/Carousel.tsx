import React from 'react';
import '../App.css';
import '../styles.css';
import Slide from '../models/slide';

interface CarouselProps {
  slides: Slide[];
  pauseTime: number;
  transTime: number;
}

function Carousel({ slides, pauseTime, transTime }: CarouselProps) {
  const controlBars = [];

  for (let i = 0; i < slides.length; i++) {
    controlBars.push(
      <div className="bar" key={i}>
        <div className="progress"></div>
      </div>
    );
  }

  return (
    <div>
      <div
        className="carousel"
        style={{
          width: `${slides.length * 100}vw`
        }}
      >
        {slides.map((slide) => (
          <div
            className="slide"
            key={slide.id}
            style={{
              animationDuration: `${slides.length * (pauseTime + transTime)}s`
            }}
          >
            <div className="title">{slide.title}</div>
            <div className="text">{slide.text}</div>
            <img src={slide.imageUrl} alt={slide.title}></img>
          </div>
        ))}
      </div>
      <div className="control">{controlBars}</div>
    </div>
  );
}

export default Carousel;
