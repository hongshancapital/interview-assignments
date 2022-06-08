import React, { CSSProperties } from 'react';
import { classNames } from '../utils/ClassNames';
import './Progress.scss';

interface IProgressProps {
  /**
   * 是否播放中
   */
  running: boolean;

  /**
   * 点击触发的方法
   */
  onClick: React.MouseEventHandler<HTMLDivElement>;

  /**
   * 播放结束触发的方法
   */
  onEnd: () => void;

  /**
   * 播放时长
   */
  interval?: number;
}

const ProgressHooks: React.FC<IProgressProps> = (props) => {
  const { onClick, running, onEnd, interval = 2000 } = props;
  const style: CSSProperties = {
    animationDuration: `${interval}ms`,
  };
  return (
    <div
      onClick={onClick}
      className={classNames(
        'ProgressTrack',
        running ? 'ProgressTrackRunning' : ''
      )}
    >
      <div
        className="ProgressBar"
        style={style}
        onAnimationEnd={() => {
          onEnd();
        }}
      />
    </div>
  );
};

export default ProgressHooks;
