import React, { memo } from 'react';
import { keyframes } from 'styled-components';
import styled from 'styled-components';

interface IBarSpan {
  active?: boolean;
  autoPlay?: number;
}
const rotate = keyframes`
  0% { width: 0; }
  100% { width: 35px; }
`;

const BarSpan = styled.span<IBarSpan>`
  margin-right: 5px;
  cursor: pointer;
  height: 3px;
  width: 35px;
  background: ${(props) => (props.active ? 'green' : '#ABA9AB')};
  
`;

const Bar = styled.div<IBarSpan>`
  animation: ${(props) => (props.active ? rotate : '')} ${(props) => props.autoPlay || 3}s linear infinite;
  height: 3px;
  background: #FFFFFF;
`;

const ProgressBar = ({ active, autoPlay }: IBarSpan) => {
  return (
    <BarSpan active={active} autoPlay={autoPlay}>
      <Bar active={active} autoPlay={autoPlay}></Bar>
    </BarSpan>
  );
};

const MemoProgressBar = memo(ProgressBar);

export default MemoProgressBar;