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
          };

          return (
            <div key={index} className={styles.page}>
              <div
                className={clsx(styles.progress, isActive && styles.active)}
                style={isActive ? activeStyle : undefined}
              ></div>
            </div>
          );
        })}
      </div>
  );
};
