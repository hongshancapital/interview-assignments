import React, { FC } from "react";

import styles from "./index.module.css";
import Progress from "./Progress";

interface IndicatorProps {
  activeIndex: number;
  count: number;
  delay: number;
  autoplay: boolean;
  onChange?: (index: number) => void;
}

const Indicator: FC<IndicatorProps> = (props) => {
  const { count, delay, activeIndex, autoplay, onChange } = props;

  const renderItems = () => {
    return Array.from(new Array(count)).map((_, index) => {
      return (
        <Progress
          key={index}
          delay={delay}
          isActive={index === activeIndex}
          index={index}
          autoplay={autoplay}
          onChange={onChange}
        />
      );
    });
  };

  return <div className={styles.indicator}>{renderItems()}</div>;
};

export default Indicator;
