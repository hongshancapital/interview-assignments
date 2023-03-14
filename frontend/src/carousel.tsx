import React, { useState, useEffect } from 'react';

type CarouselProps = {
  images: string[],
  captions?: string[],
  interval?: number
};

const Carousel: React.FC<CarouselProps> = ({ images, captions = [], interval = 5000 }) => {
  const [activeIndex, setActiveIndex] = useState(0);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setActiveIndex(prevActiveIndex => (prevActiveIndex + 1) % images.length);
    }, interval);
    return () => clearInterval(intervalId);
  }, [images.length, interval]);

  const handleDotClick = (index: number) => {
    setActiveIndex(index);
  };

  return (
    <div>
      {images.map((image, index) => (
        <img
          key={image}
          src={image}
          alt={captions[index]}
          style={{
            display: activeIndex === index ? 'block' : 'none',
            position: 'absolute',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            width:'100%'
          }}
        />
      ))}
      <div style={{ position: 'absolute', bottom: '10px', left: '50%', transform: 'translateX(-50%)' }}>
        {images.map((_, index) => (
          <span
            key={index}
            style={{
              display: 'inline-block',
              height: '10px',
              width: '10px',
              borderRadius: '50%',
              margin: '0 5px',
              cursor: 'pointer',
              backgroundColor: activeIndex === index ? '#fff' : '#aaa'
            }}
            onClick={() => handleDotClick(index)}
          />
        ))}
      </div>
    </div>
  );
};

export default Carousel;
