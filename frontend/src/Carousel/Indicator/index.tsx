import React, { FC, HTMLAttributes, useCallback, useMemo } from "react";
import {
  useAutoPlayDurationState,
  useAutoPlayState,
  useCurrentState,
  useInDragState,
  useInTransitionState,
  useItemsCountState,
  useStopAtGestureState,
} from "../state";
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
  direction = "horizontal",
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

  const [current, setCurrent] = useCurrentState();
  const [inDrag] = useInDragState();
  const [autoPlay] = useAutoPlayState();
  const [duration] = useAutoPlayDurationState();
  const [stopAtGesture] = useStopAtGestureState();
  const [count] = useItemsCountState();
  const [inTransition] = useInTransitionState();

  const transitionProperty = useMemo(() => (direction === "horizontal" ? "width" : "height"), [direction]);

  const getTransitionProperty = useCallback(
    (index: number) => {
      if (autoPlay && stopAtGesture && inDrag) return "none";
      if (index === current) return transitionProperty;
      return "none";
    },
    [autoPlay, current, inDrag, stopAtGesture, transitionProperty]
  );

  const getSize = useCallback(
    (index: number) =>
      (autoPlay && stopAtGesture && inDrag) || inTransition ? 0 : index === current ? "100%" : 0,
    [current, inDrag, stopAtGesture, autoPlay, inTransition]
  );

  return (
    <div
      className={classnames("indicator", className, direction)}
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
        <div
          key={`indicator-item-${index}`}
          className="indicator-item"
          onClick={() => {
            setCurrent(index);
          }}
        >
          <span
            className="progress"
            style={{
              transitionProperty: getTransitionProperty(index),
              transitionDuration: (duration ?? 0) + "ms",
              [transitionProperty]: getSize(index),
            }}
          ></span>
        </div>
      ))}
    </div>
  );
};
