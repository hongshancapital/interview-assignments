import cn from 'classnames';
import React from 'react';

import styles from './dot.module.scss';

interface IDotProps {
  onClick: () => void;
  active: boolean;
  interval?: number | null;
}

export const Dot: React.FC<IDotProps> = ({ active, onClick, interval }) => {
  return (
    <div
      className={cn(styles.dot, { [styles["dot-active"]]: active })}
      onClick={onClick}
    >
      <div
        className={styles["dot-inner"]}
        style={{
          animationDuration: `${interval}ms`,
        }}
      />
    </div>
  );
};
