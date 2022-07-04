import React from "react";
import styles from "./index.module.sass";

export default ({
  idx,
  enable,
  onClick,
  onAnimationEnd,
}: {
  idx: number;
  enable: boolean;
  onClick: () => void;
  onAnimationEnd: () => void;
}) => {
  return (
    <div
      className={styles.item}
      onClick={() => onClick()}
      data-testid={`footer-${idx}`}
      onAnimationEnd={() => onAnimationEnd()}
    >
      <div
        className={`${styles.indicator} ${enable ? styles.enable : ""}`}
      ></div>
    </div>
  );
};
