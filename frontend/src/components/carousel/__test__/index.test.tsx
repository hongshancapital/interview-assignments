import { Carousel } from '../index';
import mountTest from '../../../tests/mountTest';
import { act, render } from '@testing-library/react';
import React from 'react';

interface CarouselRef {
  slider: number;
}

const sleep = (time: number) => new Promise((resolve) => {
  setTimeout(resolve, time);
});

describe('Carousel', () => {
  mountTest(Carousel);

  it('should render correctly', () => {
    const { getByTestId } = render(
      <Carousel>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    const element = getByTestId('carousel');
    expect(element).toBeInTheDocument();

    const list = element.querySelector('.dots-item');
  });

  it('should auto play', async () => {
    const ref = React.createRef<CarouselRef>();
    const { container } = render(
      <Carousel ref={ref} interval={1}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>,
    );
    await sleep(500);
    act(() => {
      expect(ref?.current?.slider).toBe(0);
    });
    expect(container.querySelectorAll('.dots-bg')[0]).toHaveStyle('animation: bg-animation 1s linear;');

    await sleep(1000);
    act(() => {
      expect(ref?.current?.slider).toBe(1);
    });
    expect(container.querySelectorAll('.dots-bg')[1]).toHaveStyle('animation: bg-animation 1s linear;');

    await sleep(1000);
    act(() => {
      expect(ref?.current?.slider).toBe(2);
    });
    expect(container.querySelectorAll('.dots-bg')[2]).toHaveStyle('animation: bg-animation 1s linear;');
  });
});