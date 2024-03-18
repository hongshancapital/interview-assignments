import { render } from '@testing-library/react';
import Carousel from './';
import { SLIDE_DATA } from '../../App';
import { act } from 'react-dom/test-utils';

describe('test Carousel', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  test('renders children correctly', () => {
    const { getAllByRole } = render(<Carousel list={SLIDE_DATA} />);
    const headingElements = getAllByRole('heading');
    expect(headingElements.length).toBe(SLIDE_DATA.length);
  });

  test('slides to next index after delay', async () => {
    const { getAllByRole } = render(
      <Carousel list={SLIDE_DATA} delay={3000} />,
    );
    const headingElements = getAllByRole('heading');

    for(let i = 0;i < SLIDE_DATA.length;i++){
      expect(headingElements[i].parentNode).toHaveClass('carousel-slide-active');
      act(() => {
        jest.advanceTimersByTime(3000);
      });
    }
    expect(headingElements[0].parentNode).toHaveClass('carousel-slide-active');
  });

  test('should slide correctly after click', () => {
    const { container } = render(<Carousel list={SLIDE_DATA} />);
    const paginationItems = container.querySelectorAll<HTMLElement>(
      '.carousel-pagination-item',
    );
    act(() => {
      paginationItems[1]?.click?.();
    });
    const slideItems = container.querySelectorAll('.carousel-slide');
    expect(slideItems[1]).toHaveClass('carousel-slide-active');
  });
});
