import React, { useState, useEffect, useRef, useMemo, ReactNode } from 'react';
import { setInterval } from 'timers';
import './style.css';

export type CarouselDataItem = {
 key: string | number;
 content: ReactNode | string;
 render?: (current: CarouselDataItem['key']) => ReactNode | string;
};

export interface CarouselProps {
 options: CarouselDataItem[];
 during?: number;
 easing?: 'linear' | 'ease' | 'ease-in' | 'ease-out' | 'ease-in-out';
}
let timer: ReturnType<typeof setInterval>;
export default function Carousel({
 options,
 during = 3000,
 easing = 'linear',
}: CarouselProps) {
 const [current, setCurrent] = useState(0);
 const timerCallback = useRef<() => void>();

 timerCallback.current = () => {
  console.log(current === options.length - 1 ? 0 : current + 1);
  setCurrent(current === options.length - 1 ? 0 : current + 1);
 };

 useEffect(() => {
  timer = setInterval(() => {
   if (timerCallback.current) {
    timerCallback.current();
   }
  }, during);
  return () => {
   clearInterval(timer);
  };
 }, [during]);

 const transitionStyle = useMemo(() => {
  return `transform 1s ${current === options.length - 1 ? 'unset' : easing}`;
 }, [options, easing, current]);

 const transformStyle = useMemo(() => {
  return options.length > 0 ? `translate3d(-${current * 100}vw,0,0)` : '';
 }, [current, options]);

 return (
  <div className='carousel-wrap'>
   <div className='carousel-options-wrap' style={{ transition: transitionStyle, transform: transformStyle }}>
    {options.map(({ key, content, render }, index) => {
     return (
      <div
       className='carousel-option'
       key={key}
       style={{ left: `${index * 100}vw` }}
      >
       {render ? render(key) : content}
      </div>
     );
    })}
   </div>
  </div>
 );
}
