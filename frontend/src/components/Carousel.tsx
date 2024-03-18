import { HTMLAttributes, ReactNode, useEffect, useState } from "react";
import { useInterval } from "../hooks";

import "./Carousel.scss";

export type CarouselProps = {
  children: ReactNode;
  duration: number;
} & HTMLAttributes<HTMLDivElement>;

export function Carousel({
  children,
  duration,
  className,
  ...rest
}: CarouselProps) {
  const childrens: ReactNode[] = ([] as any[]).concat(children);
  const childrens_len = childrens.length;

  const interval = useInterval();

  const [current, set_current] = useState(0);
  useEffect(() => {
    if (childrens_len < 2) {
      interval.clear();
      return;
    }
    interval.set(
      () => {
        set_current((v) => (v + 1) % childrens_len)
      },
      duration
    );
  }, [childrens_len, duration]);

  return (
    <div className={"carousel " + (className || "")} {...rest}>
      <div
        className="carousel_item_list"
        style={{ transform: `translateX(${-current}00%)` }}
      >
        {childrens.map((it, idx) => (
          <div key={idx} className="carousel_item">
            {it}
          </div>
        ))}
      </div>
      <div className="carousel_tag_list">
        {childrens.map((_, idx) => (
          <div key={idx} className="carousel_tag">
            <div
              className="carousel_tag_progress"
              style={
                childrens_len > 1 && idx === current
                  ? {
                      width: "100%",
                      transition: `width ${duration / 1000}s linear`,
                    }
                  : undefined
              }
            ></div>
          </div>
        ))}
      </div>
    </div>
  );
}
