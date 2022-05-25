import React from 'react';

/**
 * 时间线组件
 */
interface IProps {
  isActive: boolean; // 当前时间线是否激活动画
  duration: number; // 动画时长
}
const Timeline: React.FC<IProps> = ({ isActive, duration }) => {
  return (
    <div className="timeline-content">
      <div
        className={`inner${isActive ? ' active' : ''}`}
        // 根据设置的duration参数覆盖动画时长属性
        style={{ animationDuration: `${duration}s` }}
      />
    </div>
  );
};

export default Timeline;
