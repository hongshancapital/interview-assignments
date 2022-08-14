import React, { CSSProperties, memo, PropsWithChildren } from 'react';
import styles from './CarouselItem.module.css';

export interface CarouselItemProps {
  style?: CSSProperties;
}

export const CarouselItem = memo<PropsWithChildren<CarouselItemProps>>(({ style, children }) => {
  return <div style={style} className={styles.item}>{children}</div>;
});

CarouselItem.displayName = 'CarouselItem';
