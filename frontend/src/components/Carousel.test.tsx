import { render, renderHook } from '@testing-library/react';
import { roller } from '../utils';
import { Carousel, useCarousel } from './Carousel';
import Page1 from './pages/Page1';
import Page2 from './pages/Page2';
import Page3 from './pages/Page3';

describe('Carousel tests', () => {
  it('递增器', () => {
    expect(roller(5, 0)).toBe(1);
    expect(roller(5, 3)).toBe(4);
    expect(roller(5, 4)).toBe(0);
  });

  it('hooks逻辑测试', () => {
    jest.useFakeTimers();
    jest.spyOn(global, 'setTimeout');
    const { result } = renderHook(() => useCarousel(5, 1000));
    expect(setTimeout).toHaveBeenCalledTimes(1);
    expect(result.current).toEqual({ total: 5, activeIndex: 0 });
  });

  it('page-1', () => {
    const { getByText } = render(<Page1 />);
    expect(getByText('xPhone')).toBeInTheDocument();
  });

  it('page-2', () => {
    const { getByText } = render(<Page2 />);
    expect(getByText('Tablet')).toBeInTheDocument();
  });

  it('page-3', () => {
    const { getByText } = render(<Page3 />);
    expect(getByText('Get airpods.')).toBeInTheDocument();
  });

  it('Carousel has process dots', () => {
    const { getByText, container } = render(
      <Carousel>
        <Page1 />
        <Page2 />
      </Carousel>
    );
    expect(getByText('xPhone')).toBeInTheDocument();
    expect(container.querySelectorAll('.process .process-dot')).toHaveLength(2);
    expect(container.querySelector('.carousel-container')).toHaveStyle({ left: '0vw' });
  });
});
