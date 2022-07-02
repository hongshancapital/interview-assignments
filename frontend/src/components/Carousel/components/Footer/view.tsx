import React from "react";
import styles from "./index.module.sass";

export default ({
  enable,
  onAnimationEnd,
}: {
  enable: boolean;
  onAnimationEnd: () => void;
}) => {
  return (
    <div className={styles.item} onAnimationEnd={() => onAnimationEnd()}>
      <div
        className={`${styles.indicator} ${enable ? styles.enable : ""}`}
      ></div>
    </div>
  );
};
