import React, { useRef } from 'react';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import Carousel, { CarouselImperative } from './index';

test('Carousel 自动播放', async() => {
  const { container } = render(<Carousel autoPlay={true} delay={200} speed={200} width={1440} height={900} carouselItems={[
    () => <div>first</div>,
    () => <div>second</div>,
  ]}></Carousel>);

  expect(screen.getByText('first')).toBeVisible();

  await waitFor(() => expect(container?.querySelectorAll('.indicator-item-percent')[0]).toHaveClass('indicator-item-active'));

  const prevTime = performance.now();

  await waitFor(() => expect(container?.querySelector('.carousel-items')).toHaveStyle('left: -100%'));

  expect(Math.abs(performance.now() - prevTime - 400)).toBeLessThan(30);

  await waitFor(() => expect(container?.querySelectorAll('.indicator-item-percent')[1]).toHaveClass('indicator-item-active'));

});

test('Carousel 手动播放', async() => {
  const ManualCarousel = () => {
    const carouselRef = useRef<CarouselImperative>(null);
    const next = () => {
      if (carouselRef.current) {
        carouselRef.current.next();
      }
    };
    return <div>
      <Carousel ref={carouselRef} autoPlay={false} delay={200} speed={200} width={1440} height={900} carouselItems={[
        () => <div>first</div>,
        () => <div>second</div>,
      ]}></Carousel>
      <button onClick={next}>next</button>
    </div>;
  };
  const { container, getByRole } = render(<ManualCarousel></ManualCarousel>);

  fireEvent.click(getByRole('button'));

  await waitFor(() => expect(container?.querySelector('.carousel-items')).toHaveStyle('left: -100%'));

});
