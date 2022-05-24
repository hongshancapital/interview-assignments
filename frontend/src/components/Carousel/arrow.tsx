import * as React from "react";
import classNames from "classnames";

import { PointerProps } from "./pointer";

interface ArrowProps extends Pick<PointerProps, "activeKey" | "count"> {
  showArrow: boolean;
  prev: () => void;
  next: () => void;
}

const Arrow: React.FC<ArrowProps> = ({
  count,
  activeKey,
  showArrow,
  next,
  prev,
}) => {
  const arrowShowHideState = {
    left: activeKey === 0 || !showArrow,
    right: activeKey === count - 1 || !showArrow,
  };
  return (
    <div>
      <div
        className={classNames("carousel-arrow", "carousel-arrow-left", {
          "carousel-arrow-hide": arrowShowHideState.left,
        })}
        onClick={prev}
      >
        <i>&lt;</i>
      </div>
      <div
        className={classNames("carousel-arrow", "carousel-arrow-right", {
          "carousel-arrow-hide": arrowShowHideState.right,
        })}
        onClick={next}
      >
        <i>&gt;</i>
      </div>
    </div>
  );
};

export default Arrow;
