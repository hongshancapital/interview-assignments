import React from "react";
import styles from "./index.module.sass";

export default ({
  enable,
  onClick,
  onAnimationEnd,
}: {
  enable: boolean;
  onClick: () => void;
  onAnimationEnd: () => void;
}) => {
  return (
    <div
      className={styles.item}
      onClick={() => onClick()}
      onAnimationEnd={() => onAnimationEnd()}
    >
      <div
        className={`${styles.indicator} ${enable ? styles.enable : ""}`}
      ></div>
    </div>
  );
};
