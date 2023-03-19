import { IndicatorsProps } from "../type";
import { memo } from "react";
import { Indicator } from "./indicator";
import styles from "../style.module.scss";

export const Indicators = memo(function Indicators({
  activeSlideId,
  data,
  autoplay,
  delay,
  onClick,
}: IndicatorsProps) {
  return (
    <div className={styles["carousel-indicator-container"]}>
      <ul>
        {data.map(({ id }) => {
          const isActive = id === activeSlideId;
          return (
            <Indicator
              key={id}
              delay={delay}
              autoplay={autoplay}
              isActive={isActive}
              id={id}
              onClick={onClick}
            />
          );
        })}
      </ul>
    </div>
  );
});
