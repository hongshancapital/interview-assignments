import React, { FC, useState, useEffect, useCallback } from "react";
import IndicatorItem from "../IndicatorItem";
import { CarouselWrapper, LoopContainer, LoopItem, Indicator } from "./style";

export interface CarouselProps {
  duration?: number; // 停留时间 ms
  slideSpeed?: number; // 滑动时间 ms
  slideItems: React.ReactElement[];
}

let timer: NodeJS.Timeout | null = null;

const Carousel: FC<CarouselProps> = ({
  duration = 5000,
  slideSpeed = 200,
  slideItems,
}) => {
  const total = slideItems.length;
  const [currIndex, setCurrIndex] = useState(0);

  const clear = useCallback(() => {
    timer && clearInterval(timer);
  }, [])

  const autoPlay = useCallback(() => {
    timer = setInterval(() => {
      setCurrIndex((prev) => (prev + 1) % total);
    }, duration);
  }, [total, duration]);

  useEffect(() => {
    autoPlay();
    return () => {
      clear();
    };
  }, [autoPlay, clear]);

  const handleClk = useCallback(
    (index: number) => {
      setCurrIndex(index);
      clear();
      autoPlay();
    },
    [autoPlay, clear],
  );

  return (
    <CarouselWrapper>
      <LoopContainer className="loop-container" total={total} slideSpeed={slideSpeed} current={currIndex}>
        {slideItems.map((item, index) => {
          return <LoopItem key={index} total={total}>{item}</LoopItem>;
        })}
      </LoopContainer>
      <Indicator>
        {slideItems.map((item, index) => {
          return <IndicatorItem key={index} active={index === currIndex} duration={duration} onClick={() => handleClk(index)} />
        })}
      </Indicator>
    </CarouselWrapper>
  );
};

export default Carousel;
