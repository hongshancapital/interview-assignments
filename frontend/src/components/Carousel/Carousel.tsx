import React, { useState, useRef, CSSProperties, useEffect, useCallback } from "react";
import classNames from "classnames";

import "./style.css";
import { off } from "process";

export interface ICarouselProps {
  wait: number; // ms
  stopWhenHover?: boolean;
  width?: number;
  height?: number;
  items: JSX.Element[];
  customCSS: string;
}

const EmptyPage = () => (
  <div className="container text-center">Opps! Nothing!</div>
);

export function Caroussel(props: ICarouselProps) {
  const [passedTime, setPassedTime] = useState(0);
  const [pageIndex, setPageIndex] = useState(0);
  const [offsets, setOffsets] = useState<number[]>([]);
  const containerRef = useRef(null);
  let timer = useRef<number>(null);
  const { width, height, customCSS, items, wait } = props;

  useEffect(() => {
    const baseOffset:number = (containerRef.current as unknown as HTMLElement)?.offsetWidth;

    for (let i = 0; i < items.length; i += 1) {
      const currentOffset = offsets[i];
      if (currentOffset === undefined) {
        offsets[i] = i * baseOffset;
      } else {
        offsets[i] = i * baseOffset - (pageIndex % items.length) * baseOffset;
      }
    }
    setOffsets(offsets);
  }, [pageIndex]);

  setTimeout(() => {
    let previous = Date.now();
    const core = () => {
      const remaining = Date.now() - previous;
      if (remaining >= wait) {
        setPageIndex(pageIndex + 1);
      }
      setPassedTime(remaining);
      setTimeout(core, wait);
    };
    core();
  }, wait);
  const style: CSSProperties = {};
  if (width) {
    style.width = `${width}px`;
  }
  if (height) {
    style.height = `${height}px`;
  }
  if (items.length === 0) return <EmptyPage />;

  return (
    <div
      className={classNames("conatiner relative", customCSS || "")}
      ref={containerRef}
      style={style}
    >
      {items.map((e, i) => {
        const style = {
          left: `${offsets[i]}px`
        };
        return <div className="w-full h-full" style={style}>{e}</div>;
      })}
      <div className="container flex flex-row items-center absolute p-1">
        {items.map(() => {
          const width = `${(passedTime / wait)}%`;
          return <div className="w-6 h-2">
            <div style={{
              width: `${width}`
            }}/>
          </div>;
        })}
      </div>
    </div>
  );
}
