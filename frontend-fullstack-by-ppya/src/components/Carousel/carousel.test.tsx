import '@testing-library/jest-dom';

import { render, screen, fireEvent } from '@testing-library/react';
import Carousel from '@/components/Carousel';
import Indicators from '@/components/Carousel/indicators';
import { dataSource } from '@/pages/Home';
import React from 'react';
 
describe('Carousel', () => {
  const { unmount, container } = render(
    <Carousel dataSource={dataSource} activeIndex={1} />
  )

  // 正常渲染
  test('renders Carousel component', () => {
    const childDom = container.getElementsByClassName('carousel-item');
    expect(childDom.length).toBe(dataSource.length);
    expect(screen.getByText('11111')).toBeInTheDocument();
    expect(screen.getByText('22222')).toBeInTheDocument();
    expect(screen.getByText('33333')).toBeInTheDocument();
    
    const parentDom = container.getElementsByClassName('carousel-list')[0];
    const { transform } = window.getComputedStyle(parentDom);
    expect(transform).toBe(`translate(-${1 / 3 * 100}%, 0px) translateZ(0px) translate3d(0, 0, 0)`,)
  })

  const handleClick = jest.fn();
  // 点击
  test('click Carousel indicators 0', () => {
    const { container: indicatorsContainer } = render(
      <Indicators
        dataSource={dataSource}
        activeIndex={0}
        duration={3000}
        autoPlay={true}
        clickItem={handleClick}
    />)
    const clickDom = indicatorsContainer.getElementsByClassName('carousel-indicators-item')[0];
    fireEvent.click(clickDom);
    expect(handleClick).toHaveBeenCalled();
  })

  test('Carousel indicators 2 auto paly and active', () => {
    const { container: indicatorsContainer } = render(
      <Indicators
        dataSource={dataSource}
        activeIndex={2}
        duration={3000}
        autoPlay={true}
        clickItem={handleClick}
    />)
    const activeDom = indicatorsContainer.getElementsByClassName('carousel-indicators-item')[2];
    expect(activeDom).toHaveClass('active');
  })
  
  // 卸载
  test('Carousel unmount', () => {
    unmount();
    expect(container.innerHTML).toBe('');
  })
})