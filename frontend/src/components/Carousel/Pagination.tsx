import * as React from "react";
import clsx from "clsx";
import styles from "./Pagination.module.scss";

interface IPaginationProps {
    total: number
    activeIndex?: number
    className?: string
    delay: number
}

export const Pagination: React.FC<IPaginationProps> = ({ total, activeIndex = -1, className, delay }) => {
  const list = React.useMemo(()=>new Array(total).fill(''), [total]);

  return (
    <div className={clsx(styles.root, className)}>
        {list.map((_: string, index: number) => {
          const isActive = index === activeIndex;
          const activeStyle = {
            transition: `transform ${delay}ms`,
            transformOrigin: 'top right',
            transform: 'scaleX(0)',
          };

          return (
            <div key={index} className={styles.page}>
              <div
                className={styles.progress}
                style={isActive ? activeStyle : undefined}
              ></div>
            </div>
          );
        })}
      </div>
  );
};
