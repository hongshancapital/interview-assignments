import { memo } from 'react';
import type { PropsType } from './Item.d';
import styles from './index.module.css';

const Item: React.FC<PropsType> = ({
  className,
  style = null,
  children,
}) => {
  return (
    <section
      className={className ? `${className} ${styles.carousel_item}`: styles.carousel_item}
      style={style}
    >
      {children}
    </section>
  )
}

export default memo(Item);