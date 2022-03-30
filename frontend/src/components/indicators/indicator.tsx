import React from "react";
import classNames from "classnames";

import styles from "./styles.module.scss";

export function Indicator({
  animation,
  duration,
  paused,
  isActive,
  ...hostProps
}: IndicatorProps) {
  const progressBarStyle = isActive
    ? {
        animationDuration: `${duration}ms`,
        animationPlayState: paused ? "paused" : "running",
      }
    : {};

  return (
    <button
      {...hostProps}
      className={classNames({
        [styles.button]: true,
        [styles.isActive]: isActive,
        [styles.disableAnim]: !animation,
      })}
    >
      <div className={styles.buttonInner}>
        <span aria-hidden={true} className={styles.track}>
          <span className={styles.progress} style={progressBarStyle} />
        </span>
      </div>
    </button>
  );
}

export type IndicatorProps = Omit<
  JSX.IntrinsicElements["button"],
  "className" | "style"
> & {
  /** 使用进度条动画效果 */
  animation: boolean;
  /** 动画时长 */
  duration: number;
  /** 当前项是否选中 */
  isActive?: boolean;
  /** 控制动画状态是否停止 */
  paused?: boolean;
};
