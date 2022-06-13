import React from 'react';
import styled from 'styled-components';
import MemoProgressBar from './processbar';

interface IDotsDiv {
  dots: JSX.Element[];
  activeDot?: number;
  autoPlay?: number;
}

const DotsDiv = styled.div`
  position: absolute;
  display: flex;
  justify-content: center;
  bottom: 10%;
  left: 46%;
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
