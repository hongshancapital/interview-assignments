import React from 'react';
import { memo } from 'react';
import { classNames } from './utils';
import styles from './index.module.css';

const Item: React.FC<JSX.IntrinsicElements['section']> = ({
  className,
  children,
  ...restProps
}) => {
  return (
    <section
      {...restProps}
      className={classNames(className, styles.carousel_item)}
    >
      {children}
    </section>
  )
}

export default memo(Item);