import _ from "lodash";
import { useEffect, useRef, useState } from "react";
import { CarouselProps } from ".";
import { AnyFunction } from "../../types/common";
import Day from 'dayjs';
import { getPercent } from "./utils";
import { useMemoizedFn } from "ahooks";

const START_FRAME_NUMBER = 0;

const registerTimeLoop = (fn?: AnyFunction) => {
  let stop = false;
  const doFrameAnimate = () => {
    return window.requestAnimationFrame(() => {
      fn?.();
      if (!stop) {
        doFrameAnimate();
      }
    });
  }
  const t = doFrameAnimate();

  return () => {
    stop = true;
    window.cancelAnimationFrame(t)
  };

  // const t = setInterval(() => {
  //   fn?.()
  // }, 60);
  // return () => window.clearInterval(t);
}

export type UseAutoPlayOptions = Required<Pick<CarouselProps, 'duration' | 'autoPlay' | 'pauseDuringFocus'>> & {
  container: React.RefObject<HTMLDivElement>;
  dotLength: number;
};

const useAutoPlay = (options: UseAutoPlayOptions) => {
  const { duration, autoPlay, pauseDuringFocus, dotLength, container } = options;
  const [initialLastRunTime] = useState(_.now);

  const focusTimestampRef = useRef<number|null>(null);
  const totalPauseTimeRef = useRef<number|null>(null);

  const lastRunTime = useRef<number>(initialLastRunTime);
  const [currentFrameIndex, setCurrentFrameNumber] = useState(START_FRAME_NUMBER);

  const containerFocus = useMemoizedFn(() => {
    if (pauseDuringFocus && !focusTimestampRef.current) {
      focusTimestampRef.current = _.now();
    }
  });
  const containerFocusLeave = useMemoizedFn(() => {
    const focusTimestamp = focusTimestampRef.current;
    if (_.isNumber(focusTimestamp)) {
      totalPauseTimeRef.current = (totalPauseTimeRef.current ?? 0) + _.now() - focusTimestamp;
    }
    focusTimestampRef.current = null;
  });
  // useMemoizedFn is used to avoid callback traps
  const moveNext = useMemoizedFn(() => {
    const nextNumber = currentFrameIndex >= dotLength - 1 ? START_FRAME_NUMBER : (currentFrameIndex + 1);
    lastRunTime.current = _.now();
    totalPauseTimeRef.current = null;
    setCurrentFrameNumber(nextNumber);
  });
  const onSelectFrame = useMemoizedFn((frameIndex: number) => {
    lastRunTime.current = _.now();
    if (container.current) {
      container.current.style.setProperty('--timePassedPercent', `${0}%`);
    }
    setCurrentFrameNumber(frameIndex);
  });

  useEffect(() => {
    if (autoPlay && _.isNumber(duration)) {
      return registerTimeLoop(() => {
        if (focusTimestampRef.current) {
          return;
        }
        const pauseDuration = totalPauseTimeRef.current ?? 0;
        const currentTimestamp = _.now();
        const lastTimestamp = lastRunTime.current;
        const timePassed = currentTimestamp - lastTimestamp - pauseDuration;
        const timePassedPercent = getPercent(timePassed, duration);
        if (container.current) {
          container.current.style.setProperty('--timePassedPercent', `${timePassedPercent}%`);
        }
        if (_.isNumber(currentFrameIndex) && timePassed >= duration) {
          moveNext();
        }
      });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [autoPlay, duration]);

  useEffect(() => {
    setCurrentFrameNumber(START_FRAME_NUMBER);
  }, [dotLength]);

  return {
    currentFrameNumber: currentFrameIndex,
    onSelectFrame,
    containerFocus,
    containerFocusLeave,
  }
}

export default useAutoPlay;
