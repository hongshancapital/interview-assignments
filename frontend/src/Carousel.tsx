import React, { useState, useEffect } from 'react';
import './Carousel.css';

interface slideElement  {
    imgUrl: string
    text: string
}
type CarouselProps = {
  slides: slideElement[];
  autoPlay?: boolean;
  autoPlayDelay?: number;
};

const Carousel:React.FC<CarouselProps> = ({ slides, autoPlay = true, autoPlayDelay = 2000 }: CarouselProps) => {
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  const [progress, setProgress] = useState<number>(0);
  useEffect(() => {
    let timer: NodeJS.Timeout | null = null;
    if (autoPlay) {
      timer = setInterval(() => {
        setCurrentIndex((currentIndex + 1) % slides.length);
        setProgress(100);
      }, autoPlayDelay);
    }
    return () => {
      if (timer) {
        clearInterval(timer);
      }
    };
  }, [autoPlay, autoPlayDelay, currentIndex, slides.length]);

  return (
    <div className="carousel-container">
      <div className="slides-container" style={{ transform: `translateX(-${currentIndex * 100}%)` }}>
        {slides.map((slide, index) => (
          <div key={index} className="slide">
            <div className='slide-text'>
                {slide.text &&  slide.text.split("|")[0] && <p>{slide.text.split("|")[0]}</p>}
                {slide.text && slide.text.split("|")[1] &&  <p>{slide.text.split("|")[1]}</p>}
                {slide.text && slide.text.split("|")[2] && <p>{slide.text.split("|")[2]}</p>}
            </div>
            <img src={slide.imgUrl} alt={`Slide ${index}`} />
          </div>
        ))}
      </div>
      <div className="progress-container" >
        {slides.map((_, index) => (
          <div
            key={index}
            role="progressbar"
            className={`progress ${index === currentIndex ? 'active' : ''}`}
            onClick={() => setCurrentIndex(index)}
            >
                <div className='bar' style={{width: `${index === currentIndex? `${progress}%`: 0}` }}/>
            </div>
            ))}
      </div>
    </div>
  );
};

export default Carousel;
