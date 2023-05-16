
import styles from './pagination.module.scss';

export interface PaginationProps {
  total: number;
  current?: number;
  duration?: number;
  style?: React.CSSProperties;
  className?: string;
  onChange?: (current: number) => void;
}

export const Pagination = (props: PaginationProps) => {
  const { current = 0, duration = 1000, total, style, className, onChange } = props;

  const elements = new Array(total).fill(0).map((_, index) => {
    const isActive = current === index;

    const progressStyle = isActive ? {
      width: '100%',
      transition: `width ${duration}ms linear`
    } : undefined;

    const clickHandler = () => {
      onChange?.(index);
    };

    return (
      <div
        key={index}
        onClick={clickHandler}
        className={styles.item}
      >
        <div className={styles.itemProgress} style={progressStyle}></div>
      </div>
    )
  });

  return (
    <div style={style} className={`${styles.container} ${className}`}>
      {elements}
    </div>
  )
};