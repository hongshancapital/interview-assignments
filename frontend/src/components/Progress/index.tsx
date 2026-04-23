import { memo, FC } from 'react';
import './index.css';

interface ProgressProps {
  /** progress className */
  className?: string;
  /** progress percentage */
  percent?: string;
  /** Outer progress bar style */
  style?: React.CSSProperties;
  /** inner progress bar style */
  barStyle?: React.CSSProperties;
  /** click event */
  onClick?: () => void;
}

const Progress: FC<ProgressProps> = function ({
  className = '',
  style,
  barStyle,
  percent,
  onClick,
}) {
  return (
    <div className={`progress ${className}`} style={style} onClick={onClick}>
      <div
        className={`progress-bar`}
        style={{ width: percent, ...barStyle }}
      ></div>
    </div>
  );
};

export default memo(Progress);
