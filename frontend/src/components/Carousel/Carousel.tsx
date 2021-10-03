import React, {
  useState,
  useRef,
  useEffect,
  MutableRefObject,
} from "react";
import classNames from "classnames";

import "./style.css";

export interface ICarouselProps {
  interval?: number; // ms
  items: JSX.Element[];
  customCSS?: string;
  indicators?: boolean;
  cycle: boolean;
}

const EmptyPage = () => (
  <div className="container text-center">Opps! Nothing!</div>
);
export function Carousel(props: ICarouselProps) {
  const { customCSS, items, interval, indicators, cycle } = props;
  const [pageIndex, setPageIndex] = useState(0);
  const inputRef = useRef<HTMLInputElement>(null);
  let timer: MutableRefObject<number | null> = useRef<number>(null);

  const clearCycle = () => {
    if (timer.current !== null) {
      clearTimeout(timer.current);
    }
  };

  // schedule next swip
  useEffect(() => {
    clearCycle();
    if (cycle) {
      timer.current = window.setTimeout(() => {
        setPageIndex((pageIndex + 1) % items.length);
      }, interval);
    }
  }, [pageIndex, interval, cycle, items.length]);

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
    >
      <div className="w-full h-full relative overflow-hidden">
        {items.map((e, i) => {
          let animation =
            pageIndex === i ? "active" : i < pageIndex ? "previous" : "next";
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
    </div>
  );
}

Carousel.defaultProps = {
  cycle: false,
  interval: 3000,
  indicators: false,
  customCSS: '',
};
