import React, { useEffect, useState } from "react";
import classNames from "classnames";

import styles from "./index.module.css";

interface CarouseLoadingProps {
  count?: number;
  current?: number;
  duration?: number;
}

const CarouseLoading: React.FC<CarouseLoadingProps> = ({
  count = 3,
  current = 0,
  duration,
}) => {
  const [componentLoaded, setComponentLoaded] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setComponentLoaded(true);
      clearTimeout(timer);
    });
  }, []);

  const loadingInner = (isLoading: boolean) => {
    const loadingTransition = isLoading ? `width ${duration}s linear` : "none";
    const loadingWidth = componentLoaded && isLoading ? "100%" : "0px";

    return (
      <div
        className={styles.item_inner}
        style={{
          width: loadingWidth,
          transition: loadingTransition,
        }}
      />
    );
  };

  return (
    <div
      className={classNames(styles.container, {
        [styles.hidden]: !componentLoaded,
      })}
    >
      {Array.from({
        length: count,
      }).map((_, index) => {
        return (
          <div className={styles.item} key={index}>
            {current === index ? loadingInner(true) : loadingInner(false)}
          </div>
        );
      })}
    </div>
  );
};

export default CarouseLoading;
