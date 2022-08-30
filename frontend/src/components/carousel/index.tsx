import React, { FC, ReactNode, useCallback, useMemo, useState } from "react";
import Indicator from "src/components/indicator";
import "./index.scss";

export interface Props {
  animationDuration: string;
  animationMoveDuration: string;
  children: ReactNode[];
}

const Carousel: FC<Props> = props => {
  const { children, animationDuration, animationMoveDuration } = props;

  const [activeIndex, setActiveIndex] = useState<number>(0);

  const onAnimationEnd = useCallback(() => setActiveIndex(index => (index + 1) % children.length), [children.length]);

  const moveLeft = useMemo(() => ({
    transform: `translateX(-${activeIndex * 100}%)`,
    transitionDuration: animationMoveDuration,
  }), [activeIndex, animationMoveDuration]);

  const memoIndicators = useMemo(() => {
    const indicatorProps = {
      activeIndex,
      animationDuration,
      animationMoveDuration,
      onAnimationEnd,
    }
    const ary = new Array(children.length).fill(true);
    return ary.map((item, index) => (
      <Indicator
        key={index}
        currentIndex={index}
        {...indicatorProps}
      />))
  }, [activeIndex, animationDuration, animationMoveDuration, onAnimationEnd, children.length]);

  return (<div className="carousel-outer" data-testid="carousel-outer">
    <div className="carousel-inner" data-testid="carousel-inner" style={moveLeft}>
      {children}
    </div>
    <div className="indicator-lines">
      {memoIndicators}
    </div>
  </div>)
}

export default Carousel;
