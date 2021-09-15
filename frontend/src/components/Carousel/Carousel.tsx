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
  const [offsets, setOffsets] = useState<number[]>([]);
  const containerRef = useRef<HTMLDivElement>(null);
  let timer = useRef<number | null>(null);
  const { width, height, customCSS, items, wait } = props;

  useEffect(() => {
    if (containerRef && containerRef.current) {
      const baseOffset: number = containerRef.current.offsetWidth || 0;
      const newOffsets = items.map((_, i) => {
        let currentOffset = offsets[i];
        if (currentOffset === undefined) {
          currentOffset = i * baseOffset;
        } else {
          currentOffset =
            i * baseOffset - (pageIndex % items.length) * baseOffset;
        }
        return currentOffset;
      });
      setOffsets(newOffsets);
    }
  }, [pageIndex]);
  // start the loop
  useEffect(() => {
    window.setTimeout(() => {
      setPageIndex(pageIndex + 1);
    }, wait);
  }, [pageIndex]);
  const style: CSSProperties = {};
  if (width) {
    style.width = `${width}px`;
  }
  if (height) {
    style.height = `${height}px`;
  }
  if (items.length === 0) return <EmptyPage />;
  console.log(offsets);
  return (
    <div
      className={classNames("w-full h-full relative border-4 border-yellow-200", customCSS || "")}
      ref={containerRef}
      style={style}
    >
      {items.map((e, i) => {
        const style = {
          top: 0,
          left: `${offsets[i]}px`,
        };
        return (
          <div
            className={classNames(
              "w-full h-full absolute carousel-card",
              i === pageIndex % items.length ? "visible" : "invisible"
            )}
            style={style}
          >
            {e}
          </div>
        );
      })}
      <div className="container flex flex-row items-center absolute p-1">
        {items.map(() => {
          const width = `${passedTime / wait}%`;
          return (
            <div className="w-6 h-2">
              <div
                style={{
                  width: `${width}`,
                }}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
}
