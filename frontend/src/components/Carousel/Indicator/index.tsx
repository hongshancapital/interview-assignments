import { memo, type FC } from "react";
import clns from "classnames";

import { isFunction } from "../../utils/isFunction";

import styles from "./index.module.scss";

export interface IndicatorProps {
  /** 指示器数量 */
  count: number;

  /** 当前激活的索引 */
  activeIndex: number;

  /** 当前激活索引动画时长，单位 ms */
  duration: number;

  /**
   * 点击索引回调事件
   */
  onClick?: (current: number) => void;
}

const Indicator: FC<IndicatorProps> = memo(
  ({ count, activeIndex, duration, onClick }) => {
    const handleClick = (index: number) => {
      if (activeIndex === index) return;
      isFunction(onClick) && onClick(index);
    };

    return (
      <div
        className={styles.carousel_indicator}
        data-testid="carousel_indicator"
      >
        {new Array(count).fill(0).map((_, index) => (
          <div
            className={styles.carousel_indicator_block}
            key={index}
            onClick={() => handleClick(index)}
          >
            <div className={styles.carousel_indicator_item}>
              <div
                className={clns(styles.carousel_indicator_progressbar, {
                  [styles.active]: activeIndex === index,
                })}
                style={
                  activeIndex === index
                    ? {
                        animationDuration: `${duration}ms`,
                      }
                    : undefined
                }
              />
            </div>
          </div>
        ))}
      </div>
    );
  }
);

export default Indicator;
