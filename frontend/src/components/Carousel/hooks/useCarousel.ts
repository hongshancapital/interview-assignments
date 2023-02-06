import { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import { CarouselProps } from '../Carousel';
import { calcTrackStyle } from '../utils';

export interface UseCarouselProps extends CarouselProps {
  slidersCount: number;
  sliderWidth: number;
}

export const useCarousel = ({
  slidersCount,
  sliderWidth,
  duration = 3000,
  autoplay = true,
  speed = 1000,
  beforeChange,
  afterChange
}: UseCarouselProps) => {
  const [activeIndex, setActiveIndex] = useState(0);

  const [isAnimating, setIsAnimating] = useState(false);

  const autoplayTimer = useRef<NodeJS.Timer | null>(null);
  const animatingTimer = useRef<NodeJS.Timer | null>(null);

  const trackStyle = useMemo(
    () =>
      calcTrackStyle({
        activeIndex,
        sliderWidth,
        slidersCount,
        isAnimating,
        speed
      }),
    [activeIndex, isAnimating, sliderWidth, slidersCount, speed]
  );

  const translateItem = useCallback(
    (index: number) => {
      setActiveIndex(index >= slidersCount ? 0 : index);
    },
    [slidersCount, setActiveIndex]
  );

  const translateHandler = useCallback(
    (index: number) => {
      translateItem(index);

      /**
       * When the animation is playing, set the value of isAnimating to true,
       * and set it to false after playing.
       */
      setIsAnimating(true);
      beforeChange && beforeChange(activeIndex, index);

      animatingTimer.current = setTimeout(() => {
        setIsAnimating(false);
        afterChange && afterChange(activeIndex);
        animatingTimer.current = null;
      }, speed);
    },
    [activeIndex, afterChange, beforeChange, speed, translateItem]
  );

  const pausePlay = () => {
    if (autoplayTimer.current) {
      clearInterval(autoplayTimer.current);
      autoplayTimer.current = null;
    }
  };

  const startPlay = useCallback(() => {
    if (autoplayTimer.current) {
      clearInterval(autoplayTimer.current);
    }

    if (autoplay) {
      autoplayTimer.current = setInterval(() => {
        translateHandler(activeIndex + 1);
      }, duration);
    }
  }, [activeIndex, autoplay, duration, translateHandler]);

  const slideTo = useCallback(
    (index: number) => {
      if (index === activeIndex || isAnimating) return;

      // restart autoplay of translate animation
      startPlay();

      translateHandler(index);
    },
    [activeIndex, isAnimating, startPlay, translateHandler]
  );

  // initialize Carousel
  useEffect(() => {
    startPlay();
  }, [startPlay]);

  return {
    activeIndex,
    isAnimating,
    trackStyle,
    slideTo,
    pausePlay,
    startPlay
  };
};
