import React, { CSSProperties, memo, PropsWithChildren } from 'react';
import { cx } from '../../common/common';
import styles from './CarouselItem.module.css';

export interface CarouselItemProps {
  style?: CSSProperties;
  className?: string;
}

export const CarouselItem = memo<PropsWithChildren<CarouselItemProps>>(({ style, className, children }) => {
  return <div style={style} className={cx(className, styles.item)}>{children}</div>;
});

CarouselItem.displayName = 'CarouselItem';
