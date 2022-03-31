import React, {
  FC,
  isValidElement,
  useState,
  useEffect,
  useCallback,
  useRef,
} from 'react';
import classnames from 'classnames';

import {
  CarouselProps,
  CarouselItemProps,
  InternalCarouselItemProps,
} from './interface';
import { ITEM_DISPLAY_NAME, itemPrefixCls } from './carousel-item';
import { warning, isFunc, isDev } from './utils';

const prefixCls = 'pr-carousel';

const Carousel: FC<CarouselProps> = (props) => {
  const {
    className,
    style,
    autoplay = true,
    indicator,
    interval = 3000,
    direction = 'horizontal',
    effect = 'fade',
    onChange,
    children,
  } = props;
  const [curIndex, setCurIndex] = useState(0);
  const [slidesLength, setSlidesLength] = useState(0);
  const carouselRef = useRef(null);
  const wrapperRef = useRef<HTMLUListElement>(null);
  const intervalRef = useRef<number | null>(null);
  const cacheNextFunc = useRef<(() => void) | null>(null);

  const isValidEffect = ['slide', 'fade', 'card'].includes(effect);
  const classNames = classnames(prefixCls, className);
  const sliderClasses = classnames(`${prefixCls}-wrapper`, {
    [`${prefixCls}-${effect}`]: isValidEffect,
  });

  const handleEffectAnimation = (idx: number) => {
    const wrapperElem = wrapperRef.current;
    if (wrapperElem) {
      const wrapperWidth = wrapperElem.clientWidth || 0;
      const wrapperHeight = wrapperElem.clientHeight || 0;
      if (direction === 'horizontal') {
        wrapperElem.style.transform = `translateX(-${wrapperWidth * idx}px)`;
      } else if (direction === 'vertical') {
        wrapperElem.style.transform = `translateY(-${wrapperHeight * idx}px)`;
      }
    }
  };

  const handleSlideTo = (idx: number) => {
    setCurIndex(idx);

    if (effect === 'slide') {
      handleEffectAnimation(idx);
    }

    if (onChange && isFunc(onChange)) {
      onChange(idx);
    }
  };

  const handleNext = () => {
    // NOTE: 1 % 0 = NaN
    if (slidesLength <= 0) return;

    const nextIdx = (slidesLength + curIndex + 1) % slidesLength;
    handleSlideTo(nextIdx);
  };

  const handlePrev = () => {
    if (slidesLength <= 0) return;

    const prevIdx = (slidesLength + curIndex - 1) % slidesLength;
    handleSlideTo(prevIdx);
  };

  const handleStop = useCallback(() => {
    if (intervalRef.current) {
      window.clearInterval(intervalRef.current);
      intervalRef.current = null;
    }
  }, []);

  const handleStart = useCallback(() => {
    handleStop();
    intervalRef.current = window.setInterval(() => {
      cacheNextFunc.current && cacheNextFunc.current();
    }, interval);
  }, []);

  const handleMouseEnter = useCallback(() => {
    if (autoplay) {
      handleStop();
    }
  }, []);

  const handleMouseLeave = useCallback(() => {
    if (autoplay) {
      handleStart();
    }
  }, []);

  useEffect(() => {
    cacheNextFunc.current = handleNext;

    return () => {
      cacheNextFunc.current = null;
    };
  });

  useEffect(() => {
    const carouselElem = carouselRef.current;
    if (carouselElem) {
      const slides = Array.from(
        (carouselElem as HTMLDivElement).querySelectorAll(`.${itemPrefixCls}`)
      );

      setSlidesLength(slides.length);
    }

    if (autoplay) {
      handleStart();
    }

    return () => {
      handleStop();
    };
  }, []);

  const renderIndicator = () => {
    if (isValidElement(indicator)) {
      return React.cloneElement(indicator, {
        slidesLength,
        activeIndex: curIndex,
        slideTo: handleSlideTo,
      });
    }

    return null;
  };

  const renderChildren = () => {
    return React.Children.map(children, (child, index) => {
      const childElem = child as React.FunctionComponentElement<
        CarouselItemProps & InternalCarouselItemProps
      >;
      const { displayName } = childElem.type;

      if (displayName === ITEM_DISPLAY_NAME) {
        return React.cloneElement(childElem, {
          key: index.toString(),
          index,
          activeIndex: curIndex,
        });
      }

      if (isDev) {
        warning('Carousel has a child that is not a Carousel.Item component');
      }
    });
  };

  return (
    <div
      className={classNames}
      style={style}
      ref={carouselRef}
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
    >
      <ul className={sliderClasses} ref={wrapperRef}>
        {renderChildren()}
      </ul>
      {renderIndicator()}
    </div>
  );
};

export default Carousel;
