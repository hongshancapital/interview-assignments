import React, { useState, useEffect } from 'react';
import styled, {css} from 'styled-components';

// Carousel组件外层容器div的样式
const SCarouselWrapper = styled.div`
  display: flex;
  position: relative;
  margin: 0 auto;
  min-height: 100vh;
  justify-content: center;
  align-items: center;
  overflow: hidden;
`;

// 当前显示的slide的索引接口
interface ICarouselProps {
  currentSlide: number;
}

// 滑动页面的容器div
const SCarouselSlides = styled.div<ICarouselProps>`
  display: flex;
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
  /* display: ${(props) => (props.active ? 'flex' : 'none')}; */
  opacity: ${props => (props.active ? 1 : 0)};
  transition: all 0.5s ease;
  width: 100%;
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

  const prevSlide = () => {
    setCurrentSlide(
      (currentSlide - 1 + activeSlide.length) % activeSlide.length,
    );
  };

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
        <SCarouselSlides currentSlide={currentSlide}>
        {activeSlide}
        </SCarouselSlides>
      </SCarouselWrapper>
      <button
        onClick={() => {
          setCurrentSlide((currentSlide - 1 + activeSlide.length) % activeSlide.length);
        }}
      >
        Left
      </button>
      <button
        onClick={() => {
          setCurrentSlide((currentSlide + 1) % activeSlide.length);
        }}
      >
        Right
      </button>
    </div>
  );
};

export default Carousel;
