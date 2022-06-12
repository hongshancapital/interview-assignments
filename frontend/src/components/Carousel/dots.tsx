import React from 'react';
import styled from 'styled-components';
import MemoDot from './processbar';

interface IDotsDiv {
  slides: JSX.Element[];
  activeSlide?: number;
  autoPlay: number;
}

const DotsDiv = styled.div`
  position: absolute;
  bottom: 20%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const Dots = ({ slides, activeSlide, autoPlay }: IDotsDiv) => {

  return (
    <DotsDiv>
      {slides.map((slide, i) => (
        <MemoDot
          key={slide.type.name}
          active={activeSlide === i}
          autoPlay={autoPlay}
        />
      ))}
    </DotsDiv>
  );
};

export default Dots;
