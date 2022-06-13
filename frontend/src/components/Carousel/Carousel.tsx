import React, { useState, useEffect } from 'react';
import styled, { css } from 'styled-components';
import Dots from './dots';

// Carousel组件外层容器div的样式
const SCarouselWrapper = styled.div`
  position: relative;
  margin: 0 auto;
  min-height: 100vh;
  justify-content: center;
  flex-wrap: wrap;
  align-items: center;
  overflow: hidden;
  background-color: #fcfafc;
`;

// 当前显示的slide的索引接口
interface ICarouselProps {
  currentSlide: number;
}

// 滑动页面的容器div
const SCarouselSlides = styled.div<ICarouselProps>`
  display: flex;
  width: 100%;
  ${(props) =>
    props.currentSlide &&
    css`
      transform: translateX(-${props.currentSlide * 100}%);
    `};
  transition: all 0.5s ease;
`;

// 判断当前slide是否是激活状态的接口
interface ICarouselSlide {
  active?: boolean;
}

// 通过透明度控制是否显示
const SCarouselSlide = styled.div<ICarouselSlide>`
  flex: 0 0 auto;
  opacity: ${(props) => (props.active ? 1 : 0)};
  transition: all 0.5s ease;
  width: 100%;
  min-height: 100vh;
`;

const Container = styled.div`
  display: block;
`;

// 传入Carsouel组件的props接口
interface IProps {
  children: JSX.Element[];
  autoPlay: number;
}

// Carsouel组件
const Carousel = ({ children, autoPlay }: IProps) => {
  const [currentSlide, setCurrentSlide] = useState(0);

  const activeSlide = children.map((slide, index) => (
    <SCarouselSlide active={currentSlide === index} key={index}>
      {slide}
    </SCarouselSlide>
  ));

  const nextSlide = () => {
    setCurrentSlide((currentSlide + 1) % activeSlide.length);
  };

  useEffect(() => {
    const interval = setInterval(nextSlide, autoPlay * 1000);
    return () => clearInterval(interval);
  });

  return (
    <div>
      <SCarouselWrapper>
        <Container>
          <SCarouselSlides currentSlide={currentSlide}>
            {activeSlide}
          </SCarouselSlides>
          <Dots
            dots={children}
            activeDot={currentSlide}
            autoPlay={autoPlay}
          ></Dots>
        </Container>
      </SCarouselWrapper>
    </div>
  );
};

export default Carousel;
