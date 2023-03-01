import { render, act, fireEvent } from '@testing-library/react';
import React from 'react'
import Carousel, { CarouselRef } from '..';

describe('Carousel component', () => {
  it('should render carousel items and dots', () => {
    const { container } = render(<Carousel>
      <div>1</div>
      <div>2</div>
      <div>3</div>
      <div>4</div>
    </Carousel>)

    expect(container.querySelectorAll('.carousel-item').length).toBe(4)
    expect(container.querySelectorAll('.dots-item').length).toBe(4)
  })

  it('should has prev, next and go function', async () => {
    const ref = React.createRef<CarouselRef>();
    const { container } = render(
      <Carousel ref={ref}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>,
    );
    const { prev, next, goTo } = ref.current || {};
    expect(typeof prev).toBe('function');
    expect(typeof next).toBe('function');
    expect(typeof goTo).toBe('function');
    expect(container.querySelectorAll('.carousel-item')[0]).toHaveClass('carousel-item-active');
    act(() => {
      ref.current?.goTo(2);
    })
    expect(container.querySelectorAll('.carousel-item')[2]).toHaveClass('carousel-item-active');
    act(() => {
      ref.current?.prev();
    })
    expect(container.querySelectorAll('.carousel-item')[1]).toHaveClass('carousel-item-active');
    act(() => {
      ref.current?.next();
    })
    expect(container.querySelectorAll('.carousel-item')[2]).toHaveClass('carousel-item-active');
  });

  it('should execute beforeChange and afterChange callback', async () => {
    const ref = React.createRef<CarouselRef>();
    let index = 0
    const fn1 = jest.fn((from, to) => {
      if (from === 2 && to === 1) return false
    })
    const fn2 = jest.fn((current) => {
      index = current
    })
    render(
      <Carousel ref={ref} autoplay={false} beforeChange={fn1} afterChange={fn2}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>,
    );
    act(() => {
      ref.current?.goTo(2);
    })
    expect(fn1).toBeCalledTimes(1);
    expect(fn2).toBeCalledTimes(1);
    expect(index).toBe(2);

    act(() => {
      ref.current?.prev()
    })
    expect(fn1).toBeCalledTimes(2);
    expect(fn2).toBeCalledTimes(1);
    expect(index).toBe(2);

    act(() => {
      ref.current?.next()
    })
    expect(fn1).toBeCalledTimes(3);
    expect(fn2).toBeCalledTimes(2);
    expect(index).toBe(0);
  });

  describe('should active when children change', () => {
    it('should active', () => {
      const { rerender, container } = render(<Carousel />);
      expect(container.querySelector('.carousel-item-active')).toBeFalsy();
      expect(container.querySelector('.dots-item-active')).toBeFalsy();

      // Update children
      rerender(
        <Carousel>
          <div />
        </Carousel>,
      );
      expect(container.querySelector('.carousel-item-active')).toBeTruthy();
      expect(container.querySelector('.dots-item-active')).toBeTruthy();
    });

  });

  describe('dots', () => {
    it('should render dots', () => {
      const { container, rerender } = render(
        <Carousel dots={true}>
          <div>1</div>
          <div>2</div>
          <div>3</div>
        </Carousel>,
      );
      expect(container.querySelector('.dots')).toBeInTheDocument();

      rerender(
        <Carousel dots={false}>
          <div>1</div>
          <div>2</div>
          <div>3</div>
        </Carousel>
      )
      expect(container.querySelector('.dots')).not.toBeInTheDocument();

      rerender(
        <Carousel dots={{ className: 'name' }}>
          <div>1</div>
          <div>2</div>
          <div>3</div>
        </Carousel>
      )
      expect(container.querySelector('.dots')).toBeInTheDocument();
    });

    it('should handle click', () => {
      let idx = 0
      const spy = jest.fn((current: number) => {
        idx = current
      })
      const { getByText } = render(
        <Carousel afterChange={spy}>
          <div>a</div>
          <div>b</div>
          <div>c</div>
        </Carousel>,
      );
      act(() => {
        fireEvent.click(getByText(/2/))
      });
      expect(idx).toBe(2);

      act(() => {
        fireEvent.click(getByText(/0/))
      });
      expect(idx).toBe(0);
    });

    describe('should works for dotPosition', () => {
      (['left', 'right', 'top', 'bottom'] as const).forEach((dotPosition) => {
        it(dotPosition, () => {
          const { container } = render(
            <Carousel dotPosition={dotPosition}>
              <div />
            </Carousel>,
          );
          container.normalize();
          expect(container.firstChild).toMatchSnapshot();
        });
      });
    });

    it('use dots to provide dotsClass', () => {
      const { container } = render(
        <Carousel dots={{ className: 'customDots' }}>
          <div>1</div>
          <div>2</div>
          <div>3</div>
        </Carousel>,
      );
      expect(container.querySelector('.dots')).toHaveClass('customDots');
    });
  })
});
