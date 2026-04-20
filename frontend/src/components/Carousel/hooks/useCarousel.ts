import { useCallback, useEffect, useRef, useState } from 'react';
import { CarouselProps } from '../Carousel';
import { calcTrackStyle, TrackStyle } from '../utils';

export interface UseCarouselProps extends CarouselProps {
  slidersCount: number;
}

export const useCarousel = ({
  slidersCount,
  duration = 3000,
  autoplay = true,
  speed = 1000,
  beforeChange,
  afterChange
}: UseCarouselProps) => {
  const [activeIndex, setActiveIndex] = useState(-1);

  const isAnimating = useRef(false);

  const autoplayTimer = useRef<NodeJS.Timer | null>(null);
  const animatingTimer = useRef<NodeJS.Timer | null>(null);

  const [trackStyle, setTrackStyle] = useState<TrackStyle>();

  const updateTrackStyle = useCallback(
    (index: number) => {
      setTrackStyle(
        calcTrackStyle({
          activeIndex: index,
          slidersCount,
          isAnimating: isAnimating.current,
          speed
        })
      );
    },
    [slidersCount, speed]
  );

  const translateItem = useCallback(
    (index: number) => {
      const nextIndex = index >= slidersCount ? 0 : index;
      setActiveIndex(nextIndex);
      updateTrackStyle(nextIndex);
    },
    [slidersCount, updateTrackStyle]
  );

  const translateHandler = useCallback(
    (index: number) => {
      /**
       * When the animation is playing, set the value of isAnimating to true,
       * and set it to false after playing.
       */
      isAnimating.current = true;
      translateItem(index);
      beforeChange && beforeChange(activeIndex, index);

      animatingTimer.current = setTimeout(() => {
        isAnimating.current = false;
        translateItem(index);
        afterChange && afterChange(index);
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
      if (index === activeIndex || isAnimating.current) return;

      // restart autoplay of translate animation
      startPlay();

      translateHandler(index);
    },
    [activeIndex, startPlay, translateHandler]
  );

  // initialize Carousel
  useEffect(() => {
    startPlay();
    if (activeIndex === -1) translateItem(0);
  }, [activeIndex, startPlay, translateItem]);

  return {
    activeIndex,
    isAnimating: isAnimating.current,
    trackStyle,
    slideTo,
    pausePlay,
    startPlay
  };
};
