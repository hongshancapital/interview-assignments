import React, { MouseEvent, RefObject, useRef } from "react";
import useCarousel from "./useCarousel";
import "./index.css";

interface Props {
  children: JSX.Element[];
  options?: Options;
}

interface Options {
  duration?: number;
  dotColor?: string;
  interval?: number;
}

const defaultOptions = {
  duration: 500,
  dotColor: "#d1d3d1",
  interval: 2000,
};

export default function Carousel({ children: frames, options }: Props) {
  const _options = { ...defaultOptions, ...options };
  const containerRef: RefObject<HTMLDivElement> = useRef(null);
  const {
    current,
    offsetX,
    transitionDuration,
    framesStyle,
    onClickNav,
    normalizeFrames,
  } = useCarousel({
    frames,
    containerRef,
    options: _options,
  });

  const handleNavToTarget = (event: MouseEvent<HTMLDivElement>) => {
    const el = event.currentTarget;
    el.dataset.frameIndex && onClickNav(+el.dataset.frameIndex);
  };

  return (
    <div className="Wrapper">
      <div className="main-box" style={{
      transform: `translateX(${offsetX}px)`,
      transitionDuration: `${transitionDuration}ms`,
      
    }} ref={containerRef}>
        {normalizeFrames
          .map((frame, i) => (
            <div key={i} className="Frame-Box" style={framesStyle[i]}>
              {frame}
            </div>
          ))}
      </div>
      <div className="Nav">
        {frames.map((_, i) => (
          <div
            className="NavItem "
            style={{
              background: _options.dotColor,
            }}
            key={i}
            data-frame-index={i}
            onClick={handleNavToTarget}
          >
            <div
              className="process"
              style={{
                width: current === i ? "100%" : 0,
                transitionDuration: `${
                  current === i ? _options.interval : 0
                }ms`,
              }}
            ></div>
          </div>
        ))}
      </div>
    </div>
  );
}
