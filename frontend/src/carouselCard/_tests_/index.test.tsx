import { render } from '@testing-library/react';
import CarouselCard from '../index';

describe('[CarouselCard]页面模块: 轮播卡片', () => {
  test('[#1 - CarouselCard]用例名称：正常渲染', () => {
    const { container } = render(<CarouselCard />);
    expect(container.firstElementChild).toBeTruthy();
    const carouselCardElement = container.querySelector('.carousel-card');
    expect(carouselCardElement).toBeInTheDocument();
  });
});
