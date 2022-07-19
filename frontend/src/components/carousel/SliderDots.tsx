import React, { Children, useMemo } from 'react';

interface ISliderDotsProps {
  onDotClick(index: number): void;
  dotsTheme: 'light' | 'dark';
  items: React.ReactNode;
  currentIndex: number;
  duration?: number;
}

const SliderDots: React.FC<ISliderDotsProps> = (props) => {
  const { dotsTheme, items, onDotClick, currentIndex, duration } = props;

  const isDotActive = (index: number, currentIndex: number, length: number) => {
    return (
      index === currentIndex ||
      (index === 0 && currentIndex > length - 1) ||
      (index === length - 1 && currentIndex < 0)
    );
  };

  const itemStyle = useMemo(() => {
    if (duration) {
      return {
        animationDuration: `${duration/1000}s`
      };
    }
  }, [duration]);

  return (
    <ul className={`slider__dots slider__dots-theme-${dotsTheme}`}>
      {Children.map(items, (_, index) => {
        const isActive = isDotActive(index, currentIndex, (items as any).length);
        return (
          <li
            key={index}
            className={`slider__dots-item ${isActive && 'slider__dots-item-active'}`}
            onClick={() => onDotClick(index)}>
            <div className='slider__dots-item-inner' style={isActive ? itemStyle : undefined} />
          </li>
        );
      })}
    </ul>
  );
};

export default SliderDots;
