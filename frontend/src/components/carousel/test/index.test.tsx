import { render, act } from '@testing-library/react';
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

  it('should expose render el', () => {
    const ref = React.createRef<CarouselRef>();
    render(
      <Carousel ref={ref}>
        <div />
      </Carousel>,
    );
    const { el } = ref.current || {};
    expect((el as HTMLDivElement).nodeType).toBe(1);
  });

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

    it('should keep defaultIndex', () => {
      const { rerender, container } = render(<Carousel defaultIndex={1} />);
      rerender(
        <Carousel defaultIndex={1}>
          <div key="1" />
          <div key="2" />
          <div key="3" />
        </Carousel>,
      );
      expect(container.querySelectorAll('.carousel-item')[1]).toHaveClass('carousel-item-active');
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
