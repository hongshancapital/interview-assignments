import React, { CSSProperties, memo, PropsWithChildren, useMemo } from 'react';
import { cx, useScreen } from '../../common/common';
import styles from './index.module.css';

export interface FullScreenProps {
  center?: boolean;
  style?: CSSProperties;
  className?: string;
}

export const FullScreen = memo<PropsWithChildren<FullScreenProps>>(({ center, style, className, children }) => {
  const [width, height] = useScreen();

  return (
    <div style={{ ...style, width, height }} className={cx(styles.full, className, center && styles.center)}>
      {children}
    </div>
  );
});
