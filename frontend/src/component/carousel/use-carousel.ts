import React, {useEffect, useMemo, useRef, useState} from 'react';
import { CarouselProps, Position } from './index';
import { useRect } from './use-rect';

export function useCarousel(props:CarouselProps) {
  const {
    className,
    style,
    duration = 3000,
    children,
    defaultIndex = 0,
    animationDuration = 500,
    onChange,
    dotPosition = 'bottom',
    dotOffset = 30,
    dotStyle,
    vertical = false,
  } = props;
  const carouselsRef = useRef<HTMLDivElement | null>(null);
  const timer = useRef<NodeJS.Timeout | null>(null);
  const [current, setCurrent] = useState(defaultIndex);
  const carouselLength = useMemo(() => React.Children.count(children), [children]);
  const { size , wrapperRef, setCurrentRect } = useRect<HTMLDivElement>([carouselLength, vertical]);
  const sizeKey = vertical ? 'height' : 'width';
  const transformKey = vertical ? 'Y' : 'X';
  const childrenSize = size[sizeKey] * carouselLength;
  const carouselAnimateStyle: React.CSSProperties = {
    transition: `transform ${animationDuration}ms`,
    transform: `translate${transformKey}(-${size[sizeKey] * current}px)`,
  }
  const dotWrapperStyle: React.CSSProperties = {
    position: 'absolute',
    ...getPosition(dotPosition, dotOffset)
  }

  const clearTimer = () => {
    if (timer.current) {
      clearTimeout(timer.current)
      timer.current = null
    }
  }

  const loop = () => {
    timer.current = setTimeout(() => {
      setCurrent(prev => {
        let before = prev;
        let after = prev >= carouselLength - 1 ? 0 : prev + 1;
        onChange?.(after, before)
        return prev >= carouselLength - 1 ? 0 : prev + 1
      });
      loop()
    }, duration)
  };

  const renderChildren = () => {
    if (!children) return null;
    return React.Children.map(children, child => {
      if (!React.isValidElement(child)) return null;
      return React.cloneElement(child, {
        defaultStyle: {
          height: '100%',
          [sizeKey]: size[sizeKey],
          flexShrink: 0
        }
      })
    })
  }

  const listenerResize = () => {
    setCurrentRect();
  }

  useEffect(() => {
    window.addEventListener('resize', listenerResize);
    return () => {
      window.removeEventListener('resize', listenerResize);
    }
    // eslint-disable-next-line
  }, [])

  return {
    className,
    style,
    carouselsRef,
    duration,
    current,
    carouselLength,
    size,
    sizeKey,
    wrapperRef,
    childrenSize,
    clearTimer,
    loop,
    renderChildren,
    carouselAnimateStyle,
    dotPosition,
    dotWrapperStyle,
    dotStyle,
    vertical,
  }
}

function getPosition(position: Position, offset: number | string): React.CSSProperties {
  switch (position) {
    case 'top':
      return {
        left: 0,
        right: 0,
        top: offset,
      }
    case 'bottom':
      return {
        bottom: offset,
        left: 0,
        right: 0,
      }
    case 'left':
      return {
        top: 0,
        bottom: 0,
        left: offset,
      }
    case 'right':
      return {
        top: 0,
        bottom: 0,
        right: offset,
      }
  }
}
