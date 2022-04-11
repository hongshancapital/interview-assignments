import React, { useEffect, useRef, useState } from 'react';
import Dots from './dots';
import './index.sass'

export type CarouselEffect = 'scrollx' | 'fade';
export type DotPosition = 'top' | 'bottom' | 'left' | 'right';

export interface CarouselProps {
  effect?: CarouselEffect;
  style?: React.CSSProperties;
  intervalTime?: number;
  autoplay?: boolean;
  dotPosition?: DotPosition;
  children?: React.ReactNode;
}


function CasouselContent(props: any): JSX.Element {
  const {
    className,
    children
  } = props;
  const ref = useRef<HTMLDivElement>(null);

  return (
    <div className={className} ref={ref}>
      {children}
    </div>
  );
}


function Casousel(props: CarouselProps): JSX.Element {
  const {
    autoplay = true,
    intervalTime = 3000,
  } = props
  const [dotsPosition, setDotsPosition] = useState<DotPosition>('bottom');
  const [activeSlick, setActiveSlick] = useState(0);
  const [transformStyle, setTransformStyle] = useState<string>('translateX(0)');

  const childrenArray = React.Children.toArray(props.children);
  const { length: len } = childrenArray;

  useEffect(() => {
    let transformStyle = ``;
    if(activeSlick === 0) {
      transformStyle = `translateX(0)`;
    } else {
      transformStyle = `translateX(${-100 * activeSlick}vw)`;
    }
    setTransformStyle(transformStyle);

    let timer = setTimeout(() => {}) as NodeJS.Timeout;
    if(autoplay) {
      timer = setTimeout(() => {
        setActiveSlick((activeSlick + 1) % len);
      }, intervalTime);
    }

    return () => {
      clearTimeout(timer);
    }
  }, [activeSlick]);

  return (
    <>
      <div className='carousel-main' 
        style={{
          width: `${100 * len}%`,
          transform: transformStyle
        }}
        >
        {childrenArray.length > 0 ? childrenArray.map((item, index) => {
          const isActive = index === activeSlick;
          
          return <CasouselContent 
            key={index}
            isActive={isActive}
            className={`carousel-item ${isActive ? 'active' : ''}`}
            >
            {item}
          </CasouselContent>
        }) : null}

      </div>
      <Dots 
        onClick={(index: number) => {
          setActiveSlick(index);
        }}
        activeSlick={activeSlick}
        dotsPosition={dotsPosition}
        dotsLength={len}
        interval={intervalTime}
      />
    </>
  )
}

export default Casousel;
