import React, { FC, useState, useEffect } from "react";

import styles from "./index.module.css";

interface ProgressProps {
  index: number;
  isActive: boolean;
  delay: number;
  autoplay: boolean;
  onChange?: (index: number) => void;
}

const Progress: FC<ProgressProps> = (props) => {
  const { isActive, delay, autoplay, onChange, index } = props;
  const [mounted, setMounted] = useState<boolean>(false);

  const cubeStyle =
    mounted && isActive
      ? {
          width: "100%",
          transition: autoplay ? `width ${delay}ms linear` : undefined,
        }
      : undefined;

  const handleClick = () => {
    onChange && onChange(index);
  };

  useEffect(() => {
    if (!mounted) {
      // Make the transition to be executed initially once
      setMounted(true);
    }
  }, [mounted]);

  return (
    <span className={styles.progress} onClick={handleClick}>
      <span style={cubeStyle} />
    </span>
  );
};

export default Progress;
