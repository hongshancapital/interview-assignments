/*
 * 批量复制组件
 */
import React, { ReactElement, ReactNode } from 'react';

interface RepeatProps {
  numTimes: number,   // 复制次数
  children: Function  // 注意: 类型是 Function
};

function Repeat({ numTimes, children }: RepeatProps): ReactElement {
  let items: ReactNode[] = [];
  for (let i = 0; i < numTimes; i++) {
    items.push(children(i));
  }
  return <>{items}</>;
}

export default Repeat;
