import { IndicatorsProps } from "../type";
import { FC } from "react";
import { Indicator } from "./indicator";
import styles from "../style.module.scss";

export const Indicators: FC<IndicatorsProps> = ({
  activeSlideId,
  data,
  autoplay,
  delay,
  onClick,
}) => {
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
};
