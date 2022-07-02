import React from "react";
import View from "./view";
import { configs } from "../../configs";
import styles from "./index.module.sass";

export default ({
  current,
  setCurrent,
}: {
  current: number;
  setCurrent: (idx: number) => void;
}) => {
  const arr = new Array(configs.length).fill(null);

  return (
    <div className={styles.footer}>
      {arr.map((_, index: number) => (
        <View
          key={index}
          enable={current === index}
          onClick={() => setCurrent(index)}
          onAnimationEnd={() =>
            setCurrent(current === configs.length - 1 ? 0 : current + 1)
          }
        />
      ))}
    </div>
  );
};
