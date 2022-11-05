import React, { useEffect, useMemo } from 'react';

interface SwipeDotsProps {
  current: number;
  count: number;
  timing: number;
  slideTo: (to: number, swiping?: boolean) => void;
}

const CarouselDots: React.FC<SwipeDotsProps> = ({
  current,
  count,
  timing,
  slideTo
}) => {
  useEffect(() => {
    const doms = document.querySelectorAll('.carousel-dot-active');
    doms.forEach((dom, index) => {
      if (index === current) {
        dom.setAttribute(
          'style',
          `width: 40px;transition: width linear ${timing / 1000}s;`
        );
      } else {
        dom.setAttribute('style', `width: 0px;transition: none;`);
      }
    });
  }, [current, count, timing]);

  const handleClickDot = (index: number) => {
    if (current === index) return;
    slideTo(index);
  };

  const renderDots = () => {
    return (
      <>
        {new Array(count).fill(null).map((_, index) => (
          <div
            className='carousel-dot'
            key={index}
            onClick={handleClickDot.bind(null, index)}
          >
            <span className='carousel-dot-active'></span>
          </div>
        ))}
      </>
    );
  };

  return <div className='carousel-dots'>{renderDots()}</div>;
};

export default CarouselDots;
