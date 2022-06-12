import React, { memo } from 'react';
import styled from 'styled-components';

interface IDotSpan {
  active?: boolean;
}

const DotSpan = styled.span<IDotSpan>`
  padding: 10px;
  margin-right: 5px;
  cursor: pointer;
  border-radius: 50%;
  background: ${(props) => (props.active ? 'green' : 'gray')};
`;

const Dot = ({active}: IDotSpan) => {
  return <DotSpan active={active}></DotSpan>;
};

const MemoDot = memo(Dot);

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

const Dots = ({slides, activeSlide, autoPlay}: IDotsDiv) => {
  return (
    <DotsDiv>
      {console.log(activeSlide)}
      {slides.map((slide, i) => (
        <MemoDot key={slide.type.name} active={activeSlide === i} />
      ))}
    </DotsDiv>
  );
};

export default Dots;
