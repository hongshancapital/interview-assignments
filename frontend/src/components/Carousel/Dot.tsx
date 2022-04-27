import React, { FC } from 'react';

export interface DotProps {
  index: number,        // 当前下标
  isMotion?: Boolean,   // 是否加载动画
  onClick?: Function    // 点击事件
};

const Dot: FC<DotProps> = ({ index, isMotion = false, onClick = () => {} }) => {
  // todo: 放大点击区域，方便触发
  return (
    <li className='carousel-dot' onClick={() => onClick(index)}>
      <em className={isMotion ? 'animation' : ''}>*</em>
    </li>
  );
}

export default Dot;
