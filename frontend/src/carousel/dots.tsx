import React, { useEffect, useRef, useState } from 'react';
import './carousel.css';

interface DotsProps {
  count: number;
  duration?: number;
  changeImg: (index: number) => void;
}

const getDots = (dotCount: number, duration: number, activeIndex: number = -1) => {
  const dots: React.ReactNode[] = [];
  for (let i = 0; i < dotCount; i++) {
    dots.push(<div key={i} className='dotBox'><div className={['dotCenter', activeIndex === i ? 'active' : ''].join(' ')} style={activeIndex === i ? {transition: `width ${duration}ms`} : undefined}></div></div>);
  }
  return dots;
};

const Dots = (props: DotsProps) => {
  const { count, duration = 3000, changeImg } = props;
  const [dotComponents, setDotComponents] = useState<React.ReactNode[]>(getDots(count, duration));
  const activeIndex = useRef(0);

  useEffect(() => {
    setDotComponents(getDots(count, duration, activeIndex.current));
    changeImg(activeIndex.current);
    const time = setInterval(() => {
      activeIndex.current = activeIndex.current === count - 1 ? 0 : activeIndex.current + 1;
      changeImg(activeIndex.current);
      setDotComponents(getDots(count, duration, activeIndex.current));
    }, duration);

    return () => clearTimeout(time);
  }, [count, duration]);

  return <div className='dotContainer'>{dotComponents.map(dot => dot)}</div>;
};

export default Dots;
