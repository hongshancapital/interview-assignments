import React, { FC, HTMLAttributes, useCallback, useMemo } from "react";
import { useCarouselState } from "../state";
import { classnames } from "../utils/classnames";
import { merge } from "../utils/merge";
import "./index.css";

export interface IIndicatorProps {
  verticalAlign?: "top" | "center" | "bottom";
  horizontalAlign?: "left" | "center" | "right";
  direction?: "vertical" | "horizontal";
}

const ALIGN_MAP = {
  "verticalAlign-top": { top: "10%" },
  "verticalAlign-center": { top: "50%", y: "-50%" },
  "verticalAlign-bottom": { bottom: "10%" },
  "horizontalAlign-left": { left: "10%" },
  "horizontalAlign-center": { left: "50%", x: "-50%" },
  "horizontalAlign-right": { right: "10%" },
};

export const Indicator: FC<IIndicatorProps & HTMLAttributes<HTMLDivElement>> = ({
  horizontalAlign = "center",
  verticalAlign = "bottom",
  direction,
  children,
  className,
  style,
  ...divProps
}) => {
  const position = useMemo(
    () =>
      merge<{ top?: string; bottom?: string; left?: string; right?: string; x?: string; y?: string }>(
        ALIGN_MAP[`horizontalAlign-${horizontalAlign}`] as any,
        ALIGN_MAP[`verticalAlign-${verticalAlign}`] as any
      ),
    [horizontalAlign, verticalAlign]
  );

  const [{ current, direction: swipeDirection, inDrag, autoPlay, duration, stopAtGesture, count }] =
    useCarouselState();

  const getTransitionProperty = useCallback(
    (index: number) => {
      if (autoPlay && stopAtGesture && inDrag) return "none";
      if (index === current) return "width";
      return "none";
    },
    [inDrag, autoPlay, stopAtGesture, current]
  );

  const getWidth = useCallback((index: number) => (index === current ? "100%" : 0), [current]);

  console.log(current);

  return (
    <div
      className={classnames("indicator", className)}
      style={{
        ...style,
        top: position.top,
        bottom: position.bottom,
        left: position.left,
        right: position.right,
        transform: `translate3d(${position.x ?? 0},${position.y ?? 0},0)`,
      }}
      {...divProps}
    >
      {new Array(count).fill(null).map((...[, index]) => (
        <div key={`indicator-item-${index}`} className="indicator-item">
          <span
            className="progress"
            style={{
              transitionProperty: getTransitionProperty(index),
              transitionDuration: (duration ?? 0) + "ms",
              width: getWidth(index),
            }}
          ></span>
        </div>
      ))}
    </div>
  );
};
