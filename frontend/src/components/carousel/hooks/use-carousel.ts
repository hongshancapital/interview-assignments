import { useRef, useState } from "react";
import { CarouselItemRef } from "../carousel-item";

type UseCarouselParams = {
  count: number;
  duration: number;
  loop: boolean;
  interval: number;
};

const useCarousel = (params: UseCarouselParams) => {
  const [activeIdx, setActiveIdx] = useState(0);
  const carouselContentRef = useRef<HTMLDivElement>(null);
  const [containerWidth, setContainerWidth] = useState(0);
  const carouselListRef = useRef<Array<CarouselItemRef>>([]);
  const timer = useRef<NodeJS.Timeout>();
  const { count, duration, loop, interval } = params;
  const carouseContent = carouselContentRef.current;

  const setDomOffset = ({
    dom,
    offset,
    duration = 0,
    animate = false,
  }: {
    dom: HTMLDivElement | null;
    offset: number;
    duration?: number;
    animate?: boolean;
  }) => {
    if (!dom) {
      return;
    }

    dom.style.transition = `all ${animate ? duration : 0}ms`;
    dom.style.transform = `translateX(${offset}px)`;
  };

  const setCarouselListRefByIdx = ({
    value,
    idx,
  }: {
    value: CarouselItemRef;
    idx: number;
  }) => {
    carouselListRef.current[idx] = value;
  };

  const scrollTo = ({
    relativeIdx = 0,
  }: {
    relativeIdx: number;
    animate?: boolean;
  }) => {
    if (count <= 1 || !containerWidth || !carouseContent) return;
    let nextIdx = Math.min(Math.max(activeIdx + relativeIdx, 0), count);

    if (nextIdx === count) {
      nextIdx = loop ? 0 : count - 1;
    }

    setDomOffset({
      dom: carouseContent,
      offset: -nextIdx * containerWidth,
      animate: true,
      duration,
    });

    setActiveIdx(nextIdx);
  };

  const scrollNext = () => {
    setDomOffset({
      dom: carouseContent,
      offset: -activeIdx * containerWidth,
      animate: true,
      duration,
    });
    scrollTo({ relativeIdx: 1 });
  };

  const scrollPrev = () => {
    setDomOffset({
      dom: carouseContent,
      offset: -activeIdx * containerWidth,
      animate: true,
      duration,
    });
    scrollTo({ relativeIdx: -1 });
  };

  const innerLoop = () => {
    scrollNext();
    timer.current = setTimeout(() => {
      scrollNext();
    }, interval);
  };

  const stopLoop = () => {
    if (timer.current) {
      clearTimeout(timer.current);
    }
  };

  const startLoop = () => {
    timer.current = setTimeout(innerLoop, interval);
  };

  return {
    setContainerWidth,
    scrollNext,
    scrollPrev,
    scrollTo,
    carouselContentRef,
    containerWidth,
    setCarouselListRefByIdx,
    startLoop,
    stopLoop,
    activeIdx,
  };
};

export default useCarousel;
