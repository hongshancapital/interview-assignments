import React, { useRef, useState } from "react";
import "./index.css";

export interface CarouselProps {
  interval?: number; // 轮播间隔，默认3s
}

const Carousel: React.FC<CarouselProps> = ({ interval = 3, children }) => {
  const ref = useRef<HTMLDivElement>(null);

  const [current, setCurrent] = useState(0);

  // 具体的切换逻辑
  const render = (offset: number) => {
    if (!ref.current) return;
    const containerwidth = ref.current.clientWidth;
    ref.current.style.transform = `translateX(${-containerwidth * offset}px)`;
  };

  const handleAnimateEnd = () => {
    if (!ref?.current?.children.length) return;
    const next = (current + 1) % ref?.current?.children.length;
    setCurrent(next);
    render(next);
    console.log(next);
  };

  return (
    <div className="wrapper">
      <div ref={ref} style={{ display: "flex" }}>
        {children}
      </div>
      <div className="footer">
        {Array.isArray(children) && children.length > 0
          ? children.map((c, idx) => (
              <div
                className="bar"
                key={idx}
                onAnimationEnd={handleAnimateEnd}
              >
                <div
                  className="progress"
                  style={
                    current === idx
                      ? {
                          animation: `mymove ${interval}s linear`,
                        }
                      : undefined
                  }
                ></div>
              </div>
            ))
          : null}
      </div>
    </div>
  );
};

export default Carousel;
