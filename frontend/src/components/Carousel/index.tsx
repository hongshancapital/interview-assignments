import React, { useState, useEffect, useRef, useMemo, ReactNode } from 'react';
import { setInterval } from 'timers';
import './style.css';

let timer: ReturnType<typeof setInterval>;
const INITIAL_STEP = 0;

export type CarouselDataItem = {
 key: string | number;
 content:
  | ReactNode
  | string
  | ((current: CarouselDataItem['key']) => ReactNode | string);
};

export interface CarouselProps {
 wrapClassName?: string | string[];
 options: CarouselDataItem[];
 during?: number;
 easing?: 'linear' | 'ease' | 'ease-in' | 'ease-out' | 'ease-in-out';
}

export default function Carousel({
 wrapClassName,
 options,
 during = 3000,
 easing = 'ease-in',
}: CarouselProps) {
 const timerCallback = useRef<() => void>();
 const [current, setCurrent] = useState(INITIAL_STEP);

 timerCallback.current = () => {
  setCurrent(current === options.length - 1 ? INITIAL_STEP : current + 1);
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
  return `transform 0.4s ${easing}`;
 }, [easing]);

 const transformStyle = useMemo(() => {
  return options.length > 0 ? `translate3d(-${current * 100}vw,0,0)` : '';
 }, [current, options]);

 const progressAnimation = useMemo(() => {
  return `progressAct ${during / 1000}s linear 0s none`;
 }, [during]);

 return (
  <div
   className={`carousel-wrap ${
    wrapClassName
     ? typeof wrapClassName === 'string'
       ? wrapClassName
       : wrapClassName.join(' ')
     : ''
   }`}
  >
   <div
    className='carousel-options-wrap'
    style={{ transition: transitionStyle, transform: transformStyle }}
   >
    {options.map(({ key, content }, index) => {
     return (
      <div
       className='carousel-option'
       key={key}
       style={{ left: `${index * 100}vw` }}
      >
       {typeof content === 'function' ? content(key) : content}
      </div>
     );
    })}
   </div>
   <div className='carousel-progress-wrap'>
    {options.map(({ key }, index) => {
     return (
      <div className='carousel-progress-outer' key={key}>
       <div
        className='carousel-progress-inner'
        style={current === index ? { animation: progressAnimation } : {}}
       />
      </div>
     );
    })}
   </div>
  </div>
 );
}
