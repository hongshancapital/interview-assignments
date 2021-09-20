import React, { useState, useRef, CSSProperties, useEffect } from "react";
import classNames from "classnames";

import "./style.css";

export interface ICarouselProps {
  wait: number; // ms
  stopWhenHover?: boolean;
  width?: number;
  height?: number;
  items: JSX.Element[];
  customCSS?: string;
}

const EmptyPage = () => (
  <div className="container text-center">Opps! Nothing!</div>
);

export function Caroussel(props: ICarouselProps) {
  const [passedTime, setPassedTime] = useState(0);
  const [pageIndex, setPageIndex] = useState(0);
  const containerRef = useRef<HTMLDivElement>(null);
  const { customCSS, items, wait } = props;

  // start the loop
  useEffect(() => {
    window.setTimeout(() => {
      setPageIndex(pageIndex + 1);
    }, wait);
  }, [pageIndex]);
  if (items.length === 0) return <EmptyPage />;
  return (
    <div
      className={classNames("w-full h-full relative overflow-hidden", customCSS || "")}
      ref={containerRef}
    >
      {items.map((e, i) => {      
        const activeCardIdx = pageIndex % items.length;
        let animation = activeCardIdx === i
          ? 'active'
          : (i < activeCardIdx ? 'previous' : 'next');
        return (
          <div
            className={classNames(
              "w-full h-full carousel-card hidden",
              animation,
            )}
          >
            {e}
          </div>
        );
      })}
      <div className="indicators flex flex-row items-center absolute p-1">
        {items.map((_, i) => {
          return (
            <div className="m-1 w-12 h-2 bg-gray-400">
              <div
                className={classNames("h-full indicator-inner", i === (pageIndex % items.length) ? 'bg-red-300 w-full' : 'w-0')}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
}
