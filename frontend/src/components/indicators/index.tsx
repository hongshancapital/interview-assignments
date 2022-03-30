import React from "react";
import classNames from "classnames";

import { Indicator } from "./indicator";
import styles from "./styles.module.scss";

export function Indicators({
  activeIndex,
  animation,
  count,
  duration,
  paused,
  placement = "bottom",
  onChange,

  className,
  ...hostProps
}: IndicatorsProps) {
  return (
    <div
      {...hostProps}
      data-placement={placement}
      className={classNames(styles.indicators, className)}
    >
      {Array.from({ length: count }, (_, i) => (
        <Indicator
          key={i}
          animation={animation}
          duration={duration}
          isActive={i === activeIndex}
          paused={paused}
          onClick={() => onChange?.(i)}
        />
      ))}
    </div>
  );
}

export type IndicatorsProps = Omit<JSX.IntrinsicElements["div"], "onChange"> & {
  /** 当前激活的 index */
  activeIndex: number;
  /** 是否使用进度动画效果 */
  animation: boolean;
  /** 数量 */
  count: number;
  /** 每一页持续的事件  */
  duration: number;
  /** 是否处于暂停状态 */
  paused?: boolean;
  /** 位置，默认为 bottom */
  placement?: "top" | "right" | "bottom" | "left";

  /** 点击切换回调 */
  onChange?(index: number): void;
};
