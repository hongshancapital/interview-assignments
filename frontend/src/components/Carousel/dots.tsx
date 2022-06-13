import React, { useEffect, useState } from 'react';
import { act } from 'react-dom/test-utils';
import styled from 'styled-components';
import MemoProgressBar from './processbar';

interface IDotsDiv {
  dots: JSX.Element[];
  activeDot?: number;
}

const DotsDiv = styled.div`
  position: absolute;
  bottom: 20%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const Dots = ({ dots, activeDot }: IDotsDiv) => {
  const [autoPlay, setAutoPlay] = useState(false);

  useEffect(() => {
    console.log('activedot', activeDot);
  }, [activeDot]);

  return (
    <DotsDiv>
      {dots.map((dot, i) => (
        <MemoProgressBar
          key={dot.type.name}
          active={activeDot === i}
        />
      ))}
    </DotsDiv>
  );
};

export default Dots;
