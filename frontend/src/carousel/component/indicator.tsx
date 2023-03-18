import styles from "../style.module.scss";
import { memo, useCallback } from "react";
import { IndicatorProps } from "../type";

export const Indicator = memo(function Indicator({
  isActive,
  id,
  delay,
  autoplay,
  onClick,
}: IndicatorProps) {
  const handleClick = useCallback(() => {
    onClick && onClick(id);
  }, [id, onClick]);
  return (
    <li
      onClick={handleClick}
      style={{ animationDuration: `${delay}ms` }}
      className={`${styles["carousel-indicator"]} ${
        isActive && autoplay ? styles["carousel-indicator-progress"] : ""
      } ${isActive && !autoplay ? styles["carousel-indicator-active"] : ""} `}
    />
  );
});
