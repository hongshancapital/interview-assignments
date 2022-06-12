import React, { memo, useState, useEffect } from 'react';
import styled from 'styled-components';

interface IDotSpan {
  active?: boolean;
  autoPlay: number;
}

const DotSpan = styled.span<IDotSpan>`
  margin-right: 5px;
  cursor: pointer;
  height: 3px;
  width: 35px;
  background: -webkit-linear-gradient(to right, #8E54E9, #4776E6);
  background: linear-gradient(to right, #8E54E9, #4776E6);
  transition: all 4s;
  transition-duration: 1s;
  background: ${(props) => (props.active ? 'green' : 'gray')};
`;

interface IBar {
  progress?: number;
}

const Bar = styled.div<IBar>`
  width: ${({ progress }) => `${progress}%`};
  height: 3px;
  background: gray;
`;

const ProgressBar = ({ active, autoPlay }: IDotSpan) => {
  const step = 1
  const interval = 10
  const maxProgress = 100
  const [progressPercentage, setProgressPercentage] = useState(100);

  useEffect(() => {
    const updateProgress = () => setProgressPercentage(progressPercentage + step)
    if (progressPercentage < maxProgress) {
      setTimeout(updateProgress, interval)
    }
  }, [progressPercentage])


  return (
    <DotSpan active={active} autoPlay={autoPlay}>
      <Bar progress={progressPercentage}></Bar>
    </DotSpan>
  );
};

const MemoDot = memo(ProgressBar);

export default MemoDot;