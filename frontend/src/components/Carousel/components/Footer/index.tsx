import React from "react";
import View from "./view";
import styles from "./index.module.sass";

export default ({
  length,
  current,
  setCurrent,
}: {
  length: number;
  current: number;
  setCurrent: (idx: number) => void;
}) => {
  const arr = new Array(length).fill(null);

  return (
    <div className={styles.footer}>
      {arr.map((_, index: number) => (
        <View
          key={index}
          enable={current === index}
          onAnimationEnd={() =>
            setCurrent(current === length - 1 ? 0 : current + 1)
          }
        />
      ))}
    </div>
  );
};
