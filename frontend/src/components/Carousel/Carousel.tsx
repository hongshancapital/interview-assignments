import React, { useState, useEffect, useRef } from 'react';
import styled, { css } from 'styled-components';

const getWidth = () => window.innerWidth;

const SCarouselWrapper = styled.div`
  display: flex;
`;

interface ICarouselSlide {
  active?: boolean;
}

const SCarouselSlide = styled.div<ICarouselSlide>`
  flex: 0 0 auto;
  opacity: ${(props) => (props.active ? 1 : 0)};
  transition: all 0.5s ease;
  width: 100%;
`;

interface ICarouselProps {
  currentSlide: number;
}

const SCarouselSlides = styled.div<ICarouselProps>`
  display: flex;
  ${(props) =>
    props.currentSlide &&
    css`
      transform: translateX(-${props.currentSlide * 100}%);
    `};
  transition: all 0.5s ease;
`;

interface IProps {
  children: JSX.Element[];
  autoPlay: number;
}

const Carousel = ({ children, autoPlay }: IProps) => {
  // const [currentSlide, setState] = useState({
  //   activeIndex: 0,
  //   translate: 0,
  //   transition: 0.45,
  // });

  const [currentSlide, setCurrentSlide] = React.useState(0);
  // const { translate, transition, activeIndex } = currentSlide;

  // const autoPlayRef = useRef();

  // const prevSlide = () => {
  //   if (activeIndex === 0) {
  //     return setState({
  //       ...currentSlide,
  //       translate: (children.length - 1) * getWidth(),
  //       activeIndex: children.length - 1,
  //     });
  //   }

  //   setState({
  //     ...currentSlide,
  //     activeIndex: activeIndex - 1,
  //     translate: (activeIndex - 1) * getWidth(),
  //   });
  // };

  // const nextSlide = () => {
  //   if (activeIndex === children.length - 1) {
  //     return setState({
  //       ...currentSlide,
  //       translate: 0,
  //       activeIndex: 0,
  //     });
  //   }

  //   setState({
  //     ...currentSlide,
  //     translate: (activeIndex + 1) * getWidth(),
  //     activeIndex: activeIndex + 1,
  //   });
  // };

  const prevSlide = () => {
    setCurrentSlide((currentSlide - 1 + activeSlide.length) % activeSlide.length);
  }

  const nextSlide   = () => {
    setCurrentSlide((currentSlide + 1) % activeSlide.length);
  }

  useEffect(() => {
    const interval = setInterval(nextSlide, autoPlay * 1000);
    return () => clearInterval(interval);
  });

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
      {!autoPlay && (
        <>
          <button onClick={prevSlide}>Left</button>
          <button onClick={nextSlide}>Right</button>
        </>
      )}
    </div>
  );
};

export default Carousel;
