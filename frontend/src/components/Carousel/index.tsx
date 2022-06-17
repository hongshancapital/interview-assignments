import React, { ReactNode, useEffect, useRef, useState } from "react";

import "./index.css";

declare module "react" {
  interface CSSProperties {
    "--translate3d"?: string;
    "--dot-width"?: string;
  }
}

export interface Props
  extends React.DetailedHTMLProps<
    React.HTMLAttributes<HTMLDivElement>,
    HTMLDivElement
  > {
  children: ReactNode[];
  duration?: number;
  initOrder?: number;
}

export default function Carousel({
  children,
  className,
  duration = 2,
  initOrder = 0,
  ...rest
}: Props) {
  const [progress, setProgress] = useState<number>(initOrder); // 0 - childrenLength 区间
  const childrenLength = children.length;
  const durationMS = duration * 1000;
  const contentWrapperRef = useRef<HTMLDivElement>(null);
  const dotWidth = (progress % 1) * 100;
  const order = Math.floor(progress);

  useEffect(() => {
    let start: number | null = null;
    let reqId = requestAnimationFrame(function loop(timestamp) {
      reqId = requestAnimationFrame(loop);
      if (start === null) {
        start = timestamp;
        return;
      }
      const diff = timestamp - start;
      start = timestamp;
      setProgress(
        (progress) => (progress + diff / durationMS) % childrenLength
      );
    });
    return () => {
      cancelAnimationFrame(reqId);
    };
  }, [durationMS, childrenLength]);

  return (
    <div className={`carousel ${className || ""}`} {...rest}>
      <div
        ref={contentWrapperRef}
        style={{
          "--translate3d": `translate3d(-${order * 100}%, 0px, 0px)`,
        }}
        className="carousel-list"
      >
        {children}
      </div>
      <div className="carousel-slick-dots">
        {Array(childrenLength)
          .fill("$")
          .map((_, index) => {
            const isFocus = index === order;
            return (
              <span
                style={isFocus ? { "--dot-width": `${dotWidth}%` } : {}}
                key={index}
              />
            );
          })}
      </div>
    </div>
  );
}
