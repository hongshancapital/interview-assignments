/* eslint-disable array-callback-return */
/* eslint-disable react-hooks/exhaustive-deps */
import React, { MouseEvent, RefObject, useMemo, useRef } from "react";
import useCarousel from "./useCarousel";
import useCarouselEvents from "./useCarouselEvents";
import "./index.css";

interface Props {
  children: JSX.Element[];
  options?: Options;
}

interface Options {
  duration?: number;
  threshold?: number;
  dotColor?: string;
  arrowColor?: string;
  loop?: boolean;
  auto?: boolean;
  interval?: number;
}

const defaultOptions = {
  duration: 500,
  threshold: 100,
  dotColor: "#d1d3d1",
  arrowColor: "#000",
  loop: true,
  auto: true,
  interval: 2000,
};

export default function Carousel({ children: frames, options }: Props) {
  const _options = { ...defaultOptions, ...options };
  const containerRef: RefObject<HTMLDivElement> = useRef(null);
  const {
    current,
    containerStyle,
    framesStyle,
    isFirstPage,
    isLastPage,
    onTouchStart,
    onTouchMove,
    onTouchEnd,
    onClickNav,
    normalizeFrames,
  } = useCarousel({
    frames,
    containerRef,
    options: _options,
  });
  const events = useCarouselEvents({
    onStart: onTouchStart,
    onMove: onTouchMove,
    onEnd: onTouchEnd,
  });

  const frameContents = useMemo(
    () =>
      normalizeFrames
        .map((frame, i) => {
          const n = i - 1;
          if (n === current) {
            return frame;
          } else if (n < current) {
            return !_options.loop && isFirstPage ? null : frame;
          } else if (n > current) {
            return !_options.loop && isLastPage ? null : frame;
          }
        })
        .map((frame, i) => (
          <div key={i} className="Frame-Box" style={framesStyle[i]}>
            {frame}
          </div>
        )),
    [current, normalizeFrames]
  );

  const handleNavToPrev = () => onClickNav("prev");

  const handleNavToNext = () => onClickNav("next");

  const handleNavToTarget = (event: MouseEvent<HTMLDivElement>) => {
    const el = event.currentTarget;
    el.dataset.frameIndex && onClickNav(+el.dataset.frameIndex);
  };

  return (
    <div className="Wrapper">
      <div
        className="main-box"
        style={containerStyle}
        ref={containerRef}
        {...events}
      >
        {frameContents}
      </div>
      <div>
        {(_options.loop || !isFirstPage) && (
          <div
            className="ArrowPrev"
            style={{ borderColor: _options.arrowColor }}
            onClick={handleNavToPrev}
          />
        )}
        {(_options.loop || !isLastPage) && (
          <div
            className="ArrowNext"
            style={{ borderColor: _options.arrowColor }}
            onClick={handleNavToNext}
          />
        )}
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
                transitionDuration: `${current === i ? _options.interval : 0}ms`,
              }}
            ></div>
          </div>
        ))}
      </div>
    </div>
  );
}
