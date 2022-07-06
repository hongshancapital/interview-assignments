import React, { memo } from 'react';
import { keyframes } from 'styled-components';
import styled from 'styled-components';

interface IBarSpan {
  active?: boolean;
  autoPlay?: number;
}
// CSS控制进度条
const rotate = keyframes`
  0% { width: 0; }
  100% { width: 35px; }
`;
// 当前激活的进度条背景颜色
const BarSpan = styled.span<IBarSpan>`
  margin-right: 5px;
  cursor: pointer;
  height: 3px;
  width: 35px;
  background: ${(props) => (props.active ? '#818A93' : '#818A93')};
  
`;

// CSS控制当前激活的进度条进度
const Bar = styled.div<IBarSpan>`
  animation: ${(props) => (props.active ? rotate : '')} ${(props) => props.autoPlay || 3}s linear infinite;
  height: 3px;
  background: ${(props) => (props.active ? 'white' : '#818A93')};
`;

/**
 * @Description 单个进度条子组件
 * @date 2022-06-13
 * @param {boolean} active
 * @param {number} autoPlay
 * @returns {JSX.Element}
 */
const ProgressBar = ({ active, autoPlay }: IBarSpan) => {
  return (
    <BarSpan active={active} autoPlay={autoPlay}>
      <Bar active={active} autoPlay={autoPlay}></Bar>
    </BarSpan>
  );
};

const MemoProgressBar = memo(ProgressBar);

export default MemoProgressBar;