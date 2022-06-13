import React, { memo, useState, useEffect } from 'react';
import styled from 'styled-components';

interface IBarSpan {
  active?: boolean;
  activeSlide?: number;
}

const BarSpan = styled.span<IBarSpan>`
  margin-right: 5px;
  cursor: pointer;
  height: 3px;
  width: 35px;
  background: ${(props) => (props.active ? 'green' : '#ABA9AB')};
`;

interface IBar {
  progress?: number;
}

const Bar = styled.div<IBar>`
  width: ${({ progress }) => `${progress}%`};
  height: 3px;
  background: #FFFFFF;
`;

const ProgressBar = ({ active, activeSlide }: IBarSpan) => {
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

  // useEffect(() => {
  //   setProgressPercentage(0)
  // }, [autoPlay])

  return (
    <BarSpan active={active}>
      
    </BarSpan>
  );
};

const MemoProgressBar = memo(ProgressBar);

export default MemoProgressBar;