import React, { useEffect, useImperativeHandle, useMemo, useRef } from 'react';

import { CarouselItem } from './CarouselItem';
import CarouselDots from './CarouselDots';

import useSwipe from './hooks/useSwipe';
import useVisibility from './hooks/useVisibility';
import useResize from './hooks/useResize';

export interface SwipeRef {
  next: () => void;
  prev: () => void;
  slideTo: (to: number, swiping?: boolean) => void;
}

export interface SwipeProps {
  onSlideChange?: (current: number) => void;
  timing?: number;
  duration?: number;
  initialSwipe?: number;
  loop?: boolean;
  showIndicators?: boolean;
  style?: React.CSSProperties;
  children: React.ReactNode;
}

const itemKey = 'width';

export const CarouselContainer = React.forwardRef<SwipeRef, SwipeProps>(
  (props, ref) => {
    const {
      initialSwipe = 0,
      duration = 500,
      timing = 3000,
      loop = true,
      onSlideChange,
      showIndicators = true
    } = props;
    const timer = useRef<NodeJS.Timeout | null>(null);
    const count = useMemo(() => React.Children.count(props.children), [
      props.children
    ]);

    const {
      size: itemSize,
      changeSize,
      root,
      setRefs,
      slideTo,
      next,
      prev,
      current,
      swipeRef,
      loopMove
    } = useSwipe({ deps: [count], count, duration, loop });

    const itemStyle = useMemo(() => ({ [itemKey]: itemSize }), [itemSize]);
    const wrappStyle = useMemo(() => ({ [itemKey]: itemSize * count }), [
      count,
      itemSize
    ]);

    const onPlay = () => {
      if (count <= 1) return;
      if (!timing) return;
      timer.current = setTimeout(() => {
        loopMove();
      }, timing);
    };

    const onPause = () => {
      timer.current && clearTimeout(timer.current);
      timer.current = null;
    };

    useEffect(() => {
      if (itemSize) {
        slideTo({ step: initialSwipe - current, swiping: true });
      }
    }, [itemSize, initialSwipe]);

    useEffect(() => {
      if (itemSize) {
        onPlay();
      }
      return () => {
        onPause();
      };
    }, [count, timing, current, itemSize]);

    useEffect(() => {
      onSlideChange && onSlideChange(current);
    }, [current]);

    const { hidden, setHidden } = useVisibility();
    useEffect(() => {
      hidden ? onPause() : onPlay();
    }, [hidden]);

    useResize(() => {
      onPause();
      changeSize();
      onPlay();
    });

    const handleSlideTo = (to: number, swiping?: boolean) => {
      onPause();
      slideTo({ step: to - current, swiping });
      onPlay();
    };

    useImperativeHandle(ref, () => {
      return {
        next() {
          onPause();
          next();
          onPlay();
        },
        prev() {
          onPause();
          prev();
          onPlay();
        },
        slideTo(to: number, swiping?: boolean) {
          handleSlideTo(to, swiping);
        }
      };
    });

    const handleMouseEnter = () => {
      setHidden(true);
    };

    const handleMouseLeave = () => {
      setHidden(false);
    };

    return (
      <div ref={root} style={props.style} className='carousel'>
        <div
          ref={swipeRef}
          style={wrappStyle}
          className='carousel-container'
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
        >
          {React.Children.map(props.children, (child, index) => {
            if (!React.isValidElement(child)) return null;
            if (child.type !== CarouselItem) return null;
            return React.cloneElement(child, {
              style: itemStyle,
              ref: setRefs(index)
            });
          })}
        </div>
        {showIndicators && (
          <CarouselDots
            current={current}
            count={count}
            timing={timing}
            hidden={hidden}
            slideTo={handleSlideTo}
          />
        )}
      </div>
    );
  }
);
