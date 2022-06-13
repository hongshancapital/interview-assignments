import React from 'react';
import styled from 'styled-components';
import MemoProgressBar from './processbar';

interface IDotsDiv {
  dots: JSX.Element[];
  activeDot?: number;
  autoPlay?: number;
}

const DotsDiv = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  bottom: 70px;
`;

const Dots = ({ dots, activeDot, autoPlay }: IDotsDiv) => {

  return (
    <DotsDiv>
      {dots.map((dot, i) => (
        <MemoProgressBar
          key={dot.type.name}
          active={activeDot === i}
          autoPlay={autoPlay}
        />
      ))}
    </DotsDiv>
  );
};

export default Dots;
