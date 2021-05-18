import classNames from 'classnames';
import React, { ReactNode } from 'react';

import styles from './index.scss';

export type ButtonSize = 'regular' | 'small' | 'tiny';

export type ButtonType = 'regular' | 'ghost' | 'secondary';

export type Props = {
  children: ReactNode;
  size?: ButtonSize;
  disabled?: boolean;
  loading?: boolean;
  onClick?: (event?: React.MouseEvent) => void;
  className?: string;
  type?: ButtonType;
  color?: string;
  noBorder?: boolean;
  borderRadius?: number | string;
  onMouseEnter?: (event?: React.MouseEvent) => void;
  onMouseLeave?: (event?: React.MouseEvent) => void;
};

const Button = ({
  children,
  size = 'regular',
  disabled,
  loading,
  onClick,
  className,
  type = 'regular',
  noBorder = false,
  borderRadius,
  onMouseEnter,
  onMouseLeave,
}: Props) => (
  <div
    className={classNames(
      styles.button,
      noBorder && styles.noBorder,
      disabled && styles.disabled,
      loading && styles.loading,
      size === 'small' && styles.small,
      size === 'tiny' && styles.tiny,
      type === 'ghost' && styles.ghost,
      type === 'secondary' && styles.secondary,
      className,
    )}
    onClick={() => !loading && !disabled && onClick?.()}
    onMouseEnter={onMouseEnter}
    onMouseLeave={onMouseLeave}
    style={{ borderRadius }}
  >
    {loading && 'Loading...'}
    {children}
  </div>
);

export default Button;
