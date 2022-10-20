import React, { useEffect, useRef, useState } from 'react';
import Dots from './dots';
import './index.sass'

export type CarouselEffect = 'scrollx' | 'fade';

export interface CarouselProps {
  effect?: CarouselEffect;
  style?: React.CSSProperties;
  intervalTime?: number;
  speed: number;
  autoplay?: boolean;
  children?: React.ReactNode;
}


function CasouselContent(props: any): JSX.Element {
  const {
    className,
    children,
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
    speed = 500,
  } = props
  const [activeSlick, setActiveSlick] = useState(0);
  const [transformStyle, setTransformStyle] = useState<string>('translateX(0)');
  const ref = useRef<HTMLDivElement>(null);

  const childrenArray = React.Children.toArray(props.children);
  const { length: len } = childrenArray;


  function handleWheel(e: any): void {
    e.stopPropagation();
    e.preventDefault();
  }


  useEffect(() => {
    if(ref && ref.current) {
      ref.current.addEventListener('wheel', handleWheel, { passive: false });
    }

    return () => {
      if(ref  && ref.current) {
        ref.current.removeEventListener('wheel', handleWheel);
      }
    }
  }, [])

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
      <div
        ref={ref} 
        className='carousel-main' 
        style={{
          width: `${100 * len}vw`,
          transform: transformStyle,
          transition: `all ${speed}ms ease-in-out`
        }}
        >
        {childrenArray.length > 0 ? childrenArray.map((item, index) => {
          const isActive = index === activeSlick;
          
          return <CasouselContent 
            key={index}
            isActive={isActive}
            className={`carousel-item ${isActive ? 'active' : ''}`}
            onClick={() => {
              setActiveSlick(index);
            }}
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
        dotsLength={len}
        interval={intervalTime}
      />
    </>
  )
}

export default Casousel;
