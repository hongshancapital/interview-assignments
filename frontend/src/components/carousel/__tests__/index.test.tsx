import React from 'react';
import { render, screen } from '@testing-library/react';
import Carousel from '../index';
import { ICarousel } from '../interface';

const contentStyle: React.CSSProperties = {
  margin: 0,
  height: '160px',
  color: '#fff',
  lineHeight: '160px',
  textAlign: 'center',
  background: '#364d79',
};

const testProps:ICarousel = {
  autoplay: false,
  afterChange: (currentSlide: number) => {
    console.log(currentSlide);
  },
};

describe('test Carousel component', () => {
  beforeEach(() => {
    jest.resetModules();
  });
  it('测试组件是否正常渲染', () => {
    const wrapper = render(
      <Carousel >
        <div>
          <h3 style={contentStyle}>1</h3>
        </div>
        <div>
          <h3 style={contentStyle}>2</h3>
        </div>
      </Carousel>
    )
    expect(wrapper).toBeDefined();
  })

  it('测试组件接收不同的 props，是否可以正常渲染: ', () => {
    const wrapper = render(
      <Carousel {...testProps}>
        <div>
          <h3 style={contentStyle}>1</h3>
        </div>
        <div>
          <h3 style={contentStyle}>2</h3>
        </div>
      </Carousel>
    )
    const elementWrapper = wrapper.baseElement;
    const element = elementWrapper.firstElementChild;
    expect(element).toBeInTheDocument();
    const carouselElement = element?.getElementsByClassName('carousel')[0];
    expect(carouselElement?.childElementCount).toBe(2);
  })
})
