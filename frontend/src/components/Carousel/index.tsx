import React, { cloneElement, ReactElement, useEffect, useState } from "react";
import "./index.css";
import { Indicator } from "./Indicator";

enum CarouselItemState {
  previous = -100,
  current = 0,
  next = 100,
}

interface CarouselProps {
  children: ReactElement[];
  durationForEachItem?: number;
  themeList?: ("light" | "dark")[];
}

const Carousel = ({
  children,
  durationForEachItem = 3,
  themeList,
}: CarouselProps) => {
  const [stateList, setStateList] = useState<CarouselItemState[]>([
    CarouselItemState.current,
    CarouselItemState.next,
    CarouselItemState.previous,
  ]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [progress, setProgress] = useState(0);

  const toNextState = (state: CarouselItemState): CarouselItemState => {
    switch (state) {
      case CarouselItemState.next:
        return CarouselItemState.current;
      case CarouselItemState.current:
        return CarouselItemState.previous;
      case CarouselItemState.previous:
        return CarouselItemState.next;
      default:
        return state;
    }
  };

  useEffect(() => {
    let startTime: number;

    const callback: FrameRequestCallback = timestamp => {
      if (!startTime) {
        startTime = timestamp;
      }

      const elapsedSeconds = (timestamp - startTime) / 1000;
      setProgress(elapsedSeconds / durationForEachItem);

      if (elapsedSeconds >= durationForEachItem) {
        startTime = timestamp;
        setProgress(0);
        setCurrentIndex(index => (index + 1) % children.length);
        setStateList(oldStateList =>
          oldStateList.map(state => toNextState(state))
        );
      }

      requestAnimationFrame(callback);
    };
    const id = requestAnimationFrame(callback);

    return () => cancelAnimationFrame(id);
  }, []);

  const clonedChildren = children?.map((child, index) =>
    cloneElement(child, { state: stateList[index] })
  );

  return (
    <div className="carousel-container">
      {clonedChildren}
      <Indicator
        style={{
          position: "absolute",
          zIndex: 10,
          left: 0,
          right: 0,
          bottom: 60,
          margin: "auto",
          width: 32 * children.length + 8 * (children.length - 1),
        }}
        count={children.length}
        index={currentIndex}
        progress={progress}
        theme={themeList?.[currentIndex] || "light"}
      />
    </div>
  );
};

interface CarouselItemProps {
  children: ReactElement;
  state?: CarouselItemState;
}
const CarouselItem = ({
  children,
  state = CarouselItemState.current,
}: CarouselItemProps) => {
  /**
   * There are 2 animation/transition stages associated with one state:
   *
   * 1. The entering stage
   * 2. The leaving stage
   *
   * Everytime the component receives a new state, it means it is transitioning from
   * the previous state to the current received state (the ENTER stage for the state it is transitioning to)
   *
   * previous ---> CURRENT ---> next
   *           ^            ^
   *         enter        leave
   */

  const isEnteringCurrent = state === CarouselItemState.current;
  const isLeavingCurrent = state === CarouselItemState.previous;
  const isBeingRepositioned = state === CarouselItemState.next; // could also be named isEnteringNext

  const zIndex = isEnteringCurrent || isLeavingCurrent ? 2 : -1;
  const transitionDuration = isBeingRepositioned ? "0s" : "0.4s";

  return (
    <div
      className="carousel-item-container"
      style={{
        transform: `translateX(${state}%)`,
        transitionDuration,
        zIndex,
      }}
    >
      {children}
    </div>
  );
};

export { Carousel, CarouselItem };
