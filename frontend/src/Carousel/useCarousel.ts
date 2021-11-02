import { RefObject, useEffect, useState, useCallback } from "react";

interface Props {
  containerRef: RefObject<HTMLElement>;
  frames: JSX.Element[];
  options: Options;
}

interface Options {
  duration: number;
  dotColor?: string;
  interval: number;
}

type Direction = "current" | "prev" | "next" | number;

export default function useCarousel({ containerRef, frames, options }: Props) {
  const [current, setCurrent] = useState(0);
  const [transitionDuration, setTransitionDuration] = useState(0);
  const [offsetX, setOffsetX] = useState(0);
  const [isProcessTransit, setisProcessTransit] = useState(false);

  const normalizeFrames = [frames[frames.length - 1], ...frames, frames[0]];

  const framesStyle = normalizeFrames.map((_, i) => {
    const n = i - 1;
    if (n === current) {
      return { transform: `translateX(${0})` };
    } else {
      return { transform: `translateX(${-100 * (current - n)}%)` };
    }
  });

  const onClickNav = (direction: Direction) => onTransition(direction);

  const getFixedOffsetX = useCallback(
    (direction: Direction) => {
      if (!containerRef.current) return 0;
      const { width } = window.getComputedStyle(containerRef.current);
      const w = width ? parseFloat(width.split("px")[0]) : 0;
      if (typeof direction === "number") return (direction - current) * -w;
      switch (direction) {
        case "current":
          return 0;
        case "prev":
          return w;
        case "next":
          return -w;
        default:
          throw new Error("not match direction");
      }
    },
    [current, containerRef]
  );

  const updateCurrent = useCallback(
    (prevCurrent: number, direction: Direction) => {
      if (typeof direction === "number") return direction;
      switch (direction) {
        case "current":
          return prevCurrent;
        case "prev":
          if (current === 0) return frames.length - 1;
          return prevCurrent - 1;
        case "next":
          if (current === frames.length - 1) return 0;
          return prevCurrent + 1;
        default:
          throw new Error("not match direction");
      }
    },
    [current, frames.length]
  );

  const onTransition = useCallback(
    (direction: Direction) => {
      if (isProcessTransit) return;
      setOffsetX(getFixedOffsetX(direction));
      setTransitionDuration(options.duration);
      setisProcessTransit(true);
      setTimeout(() => {
        setOffsetX(0);
        setTransitionDuration(0);
        setisProcessTransit(false);
        setCurrent((prevCurrent: number) =>
          updateCurrent(prevCurrent, direction)
        );
      }, options.duration);
    },
    [getFixedOffsetX, isProcessTransit, options.duration, updateCurrent]
  );

  useEffect(() => {
    current > frames.length - 1 && setCurrent(frames.length - 1);
  }, [frames.length, current]);

  useEffect(() => {
    const timerId = setInterval(
      () => onTransition("next"),
      options.duration + options.interval
    );
    return () => clearInterval(timerId);
  }, [onTransition, options.duration, options.interval]);

  return {
    current,
    onClickNav,
    framesStyle,
    normalizeFrames,
    transitionDuration,
    offsetX,
  };
}
