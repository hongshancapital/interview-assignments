import React, { useState, useEffect } from 'react';
import styled, { css } from 'styled-components';
import * as images from '../../../src/assets/*.png'
interface ICarouselProps {
  currentSlide: number;
}

interface IProps {
  children: JSX.Element[];
  autoPlay: number;
}

const SCarouselWrapper = styled.div`
  position: relative;
  height: 100vh;
  width: 100vw;
  margin: 0 auto;
  overflow: hidden;
  background: black;
`;

interface ICarouselSlide {
  active?: boolean;
}

const SCarouselSlides = styled.div<ICarouselProps>`
  height: 100vh;
  width: 100vw;
  ${(props) =>
    props.currentSlide &&
    css`
      transform: translateX(-${props.currentSlide * 100}%);
    `};
  transition: all 0.5s ease;
`;

const SCarouselSlide = styled.div<ICarouselSlide>`
  flex: 0 0 auto;
  opacity: ${(props) => (props.active ? 1 : 0)};
  transition: all 0.5s ease;
`;

const Carousel = ({ children, autoPlay }: IProps) => {

  const [currentSlide, setCurrentSlide] = useState(0);

  const prevSlide = () => {
    setCurrentSlide((currentSlide - 1 + activeSlide.length) % activeSlide.length);
  }

  const nextSlide   = () => {
    setCurrentSlide((currentSlide + 1) % activeSlide.length);
  }

  // useEffect(() => {
  //   const interval = setInterval(nextSlide, autoPlay * 1000);
  //   return () => clearInterval(interval);
  // });

  const activeSlide = children.map((slide, index) => (
    <SCarouselSlide active={currentSlide === index} key={index}>
      {slide}
    </SCarouselSlide>
  ));

  return (
    <div>
      <SCarouselWrapper>
        <SCarouselSlides currentSlide={currentSlide}>
          {activeSlide}
        </SCarouselSlides>
      </SCarouselWrapper>
    </div>
  );
};

export default Carousel;
