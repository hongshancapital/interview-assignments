import React from 'react'
import { DotProps } from '../../interface';
import './index.scss'

const prefix = 'dot';
const Dot: React.FC<DotProps> = (props) => {
  const { total, active, animationTime } = props;
  // 当展示的走马灯面板数量少于等于1个的时候不展示面板指示灯
  if (total <= 1) {
    return null;
  }
  return (
    <div className={`${prefix}-wrap`}>
      {(new Array(total)).fill('').map((item, index) => (
        <div className={`${prefix}-process`}>
          <div
            className={index === active ? `${prefix}-process-active` : ''}
            style={{ animationDuration: `${animationTime}s` }}
          />
        </div>
      ))}
    </div>
  )
}

export default Dot; 