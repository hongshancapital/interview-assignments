import React, {
  useState,
  useRef,
  useEffect,
  KeyboardEvent,
  MutableRefObject,
  MouseEvent,
} from "react";
import classNames from "classnames";

import "./style.css";

export interface ICarouselProps {
  interval?: number; // ms
  items: JSX.Element[];
  customCSS?: string;
  keyboard?: boolean;
  indicators?: boolean;
  control?: boolean;
  pasued?: string | boolean;
  cycle?: boolean;
}

const EmptyPage = () => (
  <div className="container text-center">Opps! Nothing!</div>
);

export function Carousel(props: ICarouselProps) {
  const {
    customCSS,
    items,
    interval,
    keyboard,
    indicators,
    control,
    cycle,
    pasued,
  } = props;
  const [pageIndex, setPageIndex] = useState(0);
  const inputRef = useRef<HTMLInputElement>(null);
  let timer: MutableRefObject<number | null> = useRef<number>(null);
  let isPausedCyCle: MutableRefObject<boolean> = useRef<boolean>(false);
  const toNext = () => {
    setPageIndex(pageIndex + 1);
  };
  const toPrevious = () => {
    let idx = pageIndex - 1;
    if (idx < 0) {
      idx = items.length - 1;
    }
    setPageIndex(idx);
  };
  const clearCycle = () => {
    if (timer.current !== null) {
      clearTimeout(timer.current);
    }
  };
  const pauseCycle = () => {
    if (pasued === "hover") {
      isPausedCyCle.current = true;
      clearCycle();
    }
  };
  const startCycle = () => {
    isPausedCyCle.current = false;
    if (cycle && pasued === "hover") {
      clearCycle();
      toNext();
    }
  };
  const onKeyUp = (e: KeyboardEvent) => {
    e.preventDefault();
    if (keyboard) {
      const { key } = e;
      const isHitKeyPrompt = key === "ArrowRight" || key === "ArrowLeft";
      if (isHitKeyPrompt) {
        clearCycle();
        switch (key) {
          case "ArrowLeft": {
            toPrevious();
            break;
          }
          case "ArrowRight": {
            toNext();
            break;
          }
        }
      }
    }
  };
  const getFocus = (e: MouseEvent) => {
    e.stopPropagation();
    if (inputRef?.current) {
      inputRef.current.focus();
    }
  };
  const nav2Prev = () => {
    clearCycle();
    toPrevious();
  };
  const nav2Next = () => {
    clearCycle();
    toNext();
  };
  // schedule next swip
  useEffect(() => {
    if (cycle && !isPausedCyCle.current) {
      timer.current = window.setTimeout(() => {
        toNext();
      }, interval);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [pageIndex]);
  useEffect(() => {
    if (inputRef) {
      inputRef.current?.focus();
    }
  }, []);
  if (items.length === 0) return <EmptyPage />;
  return (
    <div
      className={classNames(
        "w-full h-full relative overflow-hidden",
        customCSS || ""
      )}
      onClick={getFocus}
      onMouseEnter={pauseCycle}
      onMouseLeave={startCycle}
    >
      {keyboard ? (
        <input
          className="keyboard-dector absolute focus:no-underline focus:outline-none"
          onKeyUp={onKeyUp}
          ref={inputRef}
        />
      ) : null}
      <div className="w-full h-full relative overflow-hidden">
        {items.map((e, i) => {
          const activeCardIdx = pageIndex % items.length;
          let animation =
            activeCardIdx === i
              ? "active"
              : i < activeCardIdx
              ? "previous"
              : "next";
          return (
            <div
              className={classNames(
                "w-full h-full carousel-card hidden",
                animation
              )}
              key={i}
            >
              {e}
            </div>
          );
        })}
      </div>
      {indicators ? (
        <div className="indicators flex flex-row items-center absolute p-1">
          {items.map((_, i) => {
            const duration = cycle ? `${interval}ms` : "0";
            const style = {
              transitionDuration: duration,
            };
            return (
              <div className="m-1 w-14 h-0.5 bg-gray-400" key={i}>
                <div
                  className={classNames(
                    "h-full indicator-inner",
                    i === pageIndex % items.length ? "bg-gray-50 w-full" : "w-0"
                  )}
                  style={style}
                />
              </div>
            );
          })}
        </div>
      ) : null}
      {control ? (
        <>
          <button
            className="control-prev
            absolute w-1/5 h-full
            flex justify-start items-center
            text-gray-400
            opacity-0 hover:opacity-100 transition-opacity duration-1000"
            data-testid="prev"
            onClick={nav2Prev}
            role="link"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="h-12 w-12"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M15 19l-7-7 7-7"
              />
            </svg>
          </button>
          <button
            className="control-next
            absolute w-1/5 h-full 
            flex justify-end items-center text-gray-400
            opacity-0 hover:opacity-100 transition-opacity duration-1000"
            data-testid="next"
            onClick={nav2Next}
            role="link"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="h-12 w-12"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M9 5l7 7-7 7"
              />
            </svg>
          </button>
        </>
      ) : null}
    </div>
  );
}

Carousel.defaultProps = {
  interval: 1000,
};
