import React, {useState, useEffect, useRef} from "react";
import classNames from "classnames";
import Timer from "../../lib/timer";
import CarouselSlice from "../CarouselSlice";
import CarouselIndicator from "../CarouselIndicator";
import {getNextIndex, getPreviousIndex, isAdjacentSlice} from "./util";
import "./index.scss";

/**
 * The carousel component
 * All of the carousel slices can be customized arbitrarily and rendered as Carousel component's children
 * @param {Object} props {
 *   switchInterval: 3000,  // the carousel switching interval (unit: ms)
 *   pauseOnHover: true,  // whether pause switching when mouse is hover (default: true)
 *   lazyRenderEnabled: true,  // whether lazy render slices (default: true)
 *   className: "",  // the class name of the carousel (use to overwrite default styles)
 * }
 */
function Carousel(props: CarouselProps): JSX.Element {
  let {
    children,
    switchInterval = DEFAULT_SWITCHING_INTERVAL,
    pauseOnHover = true,
    className: customClassName,
  } = props;
  // validate props and normalize it
  switchInterval = Math.max(MIN_SWITCHING_INTERVAL, switchInterval) || DEFAULT_SWITCHING_INTERVAL;
  const carouselProps = {
    ...props,
    switchInterval,
  };
  const slices = children == null ? [] : Array.isArray(children) ? children : [children];
  const sliceCount = slices.length;

  const switchingState = useCarouselSwitching(switchInterval, sliceCount);
  const {activeIndex, timerRef} = switchingState;
  // pause switching when mouse hover and resume switching when mouse leave
  const pauseState = useSwitchingPause(pauseOnHover, timerRef);
  const carouselState = {
    ...switchingState,
    ...pauseState,
  };

  return (
    <div className={classNames("carousel-container", customClassName)}>
      {renderCarouselSlices(slices, carouselProps, carouselState, activeIndex)}
      {renderSliceIndicators(slices, carouselProps, carouselState, activeIndex)}
    </div>
  );
}

/**
 * render the carousel slices
 * @param {JSX.Element[]} slices  the carousel slices to be rendered
 * @param {CarouselProps} carouselProps  the carousel props
 * @param {CarouselState} carouselState  the carousel state
 * @param {number} [activeIndex]  the index of the current carousel slice
 * @returns {JSX.Element}  the carousel slice elements
 */
function renderCarouselSlices(slices: JSX.Element[], carouselProps: CarouselProps, {pause, resume}: CarouselState, activeIndex?: number): JSX.Element {
  let {lazyRenderEnabled = true} = carouselProps;
  // calculate the left position of the next slice
  const position = activeIndex == null ? 0 : activeIndex * -100;
  const sliceCount = slices.length;
  return (
    <div
      className="carousel-slices"
      style={{left: `${position}%`}}
      onMouseEnter={pause}
      onMouseLeave={resume}
    >
      {slices.map((slice, sliceIndex) => {
        if (!slice) {
          return null;
        }
        return (
          <CarouselSlice
            lazy={lazyRenderEnabled && !isAdjacentSlice(sliceIndex, sliceCount, activeIndex)}
            key={`CarouselSlice${sliceIndex}`}
          >
            {slice}
          </CarouselSlice>
        );
      })}
    </div>
  );
}

/**
 * render the carousel slice indicators
 * @param {JSX.Element[]} slices  the carousel slices to be rendered
 * @param {CarouselProps} carouselProps  the carousel props
 * @param {CarouselState} carouselState  the carousel state
 * @param {number} [activeIndex]  the index of the current carousel slice
 * @returns {JSX.Element}  the carousel slice indicator elements
 */
function renderSliceIndicators(slices: JSX.Element[], carouselProps: CarouselProps, {paused}: CarouselState, activeIndex?: number): JSX.Element {
  const {switchInterval} = carouselProps;
  return (
    <div className="carousel-indicators">
      {slices.map((slice, indicatorIndex) => {
        if (!slice) {
          return null;
        }
        let {props: sliceProps, type: elementType} = slice;
        return (
          <CarouselIndicator
            actived={activeIndex === indicatorIndex && !paused}
            switchInterval={switchInterval}
            className={typeof elementType === "string" ? sliceProps["data-indicatorclass"] : sliceProps.indicatorClassName}
            key={`CarouselIndicator${indicatorIndex}`}
          />
        );
      })}
    </div>
  );
}

/**
 * the hook for carousel switching
 * @param {number} interval  the carousel switching interval
 * @param {number} sliceCount  the carousel slice count
 * @returns {CarouselSwitchingState}  the carousel switching state info
 */
function useCarouselSwitching(interval: number, sliceCount: number): CarouselSwitchingState {
  const [activeIndex, setActiveIndex] = useState<number>();
  const timerRef = useRef<Timer>();
  // switch to the specific slice
  const switchTo = (sliceIndex?: number): void => {
    setActiveIndex(sliceIndex == null ? 0 : Math.min(Math.max(sliceIndex, 0), sliceCount - 1));
  };
  // switch to the next slice
  const switchNext = (): void => {
    setActiveIndex((currentIndex?: number) => {
      return getNextIndex(sliceCount, currentIndex);
    });
  };
  // switch to the previous slice
  const switchPrevious = (): void => {
    setActiveIndex((currentIndex?: number) => {
      return getPreviousIndex(sliceCount, currentIndex);
    });
  };
  useEffect(() => {
    const timer = timerRef.current = new Timer({
      interval,
      onTick() {
        if (sliceCount < 2) {
          return;
        }
        setActiveIndex((currentIndex) => {
          return currentIndex == null ? 0 : (currentIndex + 1) % sliceCount;
        });
      },
    });
    return () => {
      timer.stop();
    };
  }, [interval, sliceCount]);
  return {
    activeIndex,
    timerRef,
    switchTo,
    switchNext,
    switchPrevious,
  };
}

/**
 * the hook for carousel switching pause
 * @param {boolean} pauseOnHover  whether pause switching when mouse is hover
 * @param {React.MutableRefObject<Timer>} timerRef  the timer
 * @returns {CarouselPauseState}  the carousel switching pause state info
 */
function useSwitchingPause(pauseOnHover: boolean, timerRef: React.MutableRefObject<Timer>): CarouselPauseState {
  const [paused, setPaused] = useState<boolean>(false);
  // pause switching when mouse hover on carousel slice
  const pause = () => {
    if (!pauseOnHover) {
      return;
    }
    setPaused(true);
    const timer = timerRef.current;
    if (timer) {
      timer.stop();
    }
  };
  // resume switching when mouse leave from carousel slice
  const resume = () => {
    if (!pauseOnHover) {
      return;
    }
    setPaused(false);
    const timer = timerRef.current;
    if (timer) {
      timer.start();
    }
  };
  return {
    paused,
    pause,
    resume,
  };
}

// the default switching interval (ms)
const DEFAULT_SWITCHING_INTERVAL = 3000;
// the minimal switching interval (ms)
const MIN_SWITCHING_INTERVAL = 500;

export default Carousel;
