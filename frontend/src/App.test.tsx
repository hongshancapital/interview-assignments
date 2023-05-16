import { render, fireEvent, act } from '@testing-library/react';
import App, { PAGES } from './App';

// Mock ResizeObserver interface, actually it can be use a polyfill to do that
class ResizeObserver {
  observe() {}
  unobserve() {}
  disconnect() {}
}
beforeAll(() => {
  window.ResizeObserver = ResizeObserver;
});

describe('App', () => {
  it(`should have ${PAGES.length} children`, () => {
    const { getByTestId } = render(<App />);
    const sliderItems = getByTestId('app-container').querySelectorAll('.sliderItem');
    expect(sliderItems).toHaveLength(PAGES.length);
  });

  it('should render correct content', () => {
    const { getByTestId } = render(<App />);
    const sliderItems = getByTestId('app-container').querySelectorAll('.sliderItem');
    PAGES.forEach((page, index) => {
      expect(sliderItems[index]).toContainHTML(
        render(page.title).container.innerHTML
      );

      if (page.content) {
        expect(sliderItems[index]).toContainHTML(
          render(page.content).container.innerHTML
        );
      }

      if (page.description) {
        expect(sliderItems[index]).toContainHTML(
          render(page.description).container.innerHTML
        );
      }
    });
  });

  it('#2 page should be active in active during 3000ms ~ 6000ms', () => {
    jest.useFakeTimers();
    const { getByTestId } = render(<App />);
    const appContainer = getByTestId('app-container');
    const sliderItems = appContainer.querySelectorAll('.sliderItem');
    const activeClass = 'carousel-slider-item-active';
    expect(sliderItems[0]).toHaveClass(activeClass);
    expect(sliderItems[1]).not.toHaveClass('carousel-slider-item-active');
    expect(sliderItems[2]).not.toHaveClass('carousel-slider-item-active');
    act(() => {
      jest.advanceTimersByTime(5000);
    });

    expect(sliderItems[0]).not.toHaveClass(activeClass);
    expect(sliderItems[1]).toHaveClass('carousel-slider-item-active');
    expect(sliderItems[2]).not.toHaveClass('carousel-slider-item-active');
  });

  it('should slide to #2 page when click the #2 pagination', async () => {
    const { getByTestId } = render(<App />);
    const appContainer = getByTestId('app-container');

    const target = appContainer.querySelectorAll<HTMLElement>('.pagination .item')[1];

    await fireEvent(target, new MouseEvent('click', {
      bubbles: true,
      cancelable: true,
    }))

    const activeClass = 'carousel-slider-item-active';
    const sliderItems = appContainer.querySelectorAll('.sliderItem');

    expect(sliderItems[0]).not.toHaveClass(activeClass);
    expect(sliderItems[1]).toHaveClass(activeClass);
    expect(sliderItems[2]).not.toHaveClass(activeClass);
  });
});
