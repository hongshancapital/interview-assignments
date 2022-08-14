import React, { useRef } from 'react';
import { render } from '@testing-library/react';
import { Carousel, CarouselRefMethod } from './Carousel';
import { act } from 'react-dom/test-utils';
import { renderHook } from '@testing-library/react-hooks';

test('Carousel - render', () => {
  jest.useFakeTimers();
  const commonStyle = { width: 300, height: 100 };
  const { container } = render(
    <div style={commonStyle}>
      <Carousel autoplay={false}>
        <Carousel.Item>
          <div style={commonStyle}>Hello</div>
        </Carousel.Item>
        <Carousel.Item>
          <div style={commonStyle}>World</div>
        </Carousel.Item>
        <Carousel.Item>
          <div style={commonStyle}>HeiHei</div>
        </Carousel.Item>
      </Carousel>
    </div>
  );
  const dom = container.firstChild as HTMLElement;
  expect(dom).not.toBeNull();
  const carousel = dom.firstChild as HTMLElement;
  expect(carousel).not.toBeNull();
  const carouselInner = carousel.firstChild as HTMLElement;
  expect(carouselInner).not.toBeNull();
  expect(carouselInner.children).toHaveLength(3);
  expect(container).toMatchSnapshot();
});

test('Carousel - props', () => {
  jest.useFakeTimers();
  const commonStyle = { width: 300, height: 100 };
  const onChange = jest.fn();
  const { container, rerender, getByTestId } = render(
    <div style={commonStyle}>
      <Carousel duration={3000} onChange={onChange} indicator={false}>
        <Carousel.Item>
          <div style={commonStyle}>Hello</div>
        </Carousel.Item>
        <Carousel.Item>
          <div style={commonStyle}>World</div>
        </Carousel.Item>
        <Carousel.Item>
          <div style={commonStyle}>HeiHei</div>
        </Carousel.Item>
      </Carousel>
    </div>
  );
  const dom = container.firstChild as HTMLElement;
  const carousel = dom.firstChild as HTMLElement;
  expect(carousel.children).toHaveLength(1);
  expect(onChange).not.toHaveBeenCalled();
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(onChange).toHaveBeenCalledTimes(1);
  expect(onChange.mock.calls[0][0]).toBe(1);
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(onChange).toHaveBeenCalledTimes(2);
  expect(onChange.mock.calls[1][0]).toBe(2);
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(onChange).toHaveBeenCalledTimes(3);
  expect(onChange.mock.calls[2][0]).toBe(0);
  const carouselInner = carousel.firstChild as HTMLElement;
  expect(carouselInner).toHaveStyle('transform: translate3d(0%, 0, 0)');

  rerender(
    <div style={commonStyle}>
      <Carousel duration={3000} onChange={onChange}>
        <Carousel.Item>
          <div style={commonStyle}>Hello</div>
        </Carousel.Item>
        <Carousel.Item>
          <div style={commonStyle}>World</div>
        </Carousel.Item>
        <Carousel.Item>
          <div style={commonStyle}>HeiHei</div>
        </Carousel.Item>
      </Carousel>
    </div>
  );
  expect(carousel.children).toHaveLength(2);
  const indicator = carousel.lastChild as HTMLElement;
  expect(indicator.children).toHaveLength(3);
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(onChange).toHaveBeenCalledTimes(4);
  expect(onChange.mock.calls[2][0]).toBe(0);
  expect(getByTestId('test-active')).toBeInTheDocument();
});

test('Carousel  - ref', () => {
  jest.useFakeTimers();
  const commonStyle = { width: 300, height: 100 };
  const onChange = jest.fn();
  const ref = React.createRef<CarouselRefMethod>();
  const { container } = render(
    <div style={commonStyle}>
      <Carousel duration={3000} onChange={onChange} autoplay={false} ref={ref}>
        <Carousel.Item>
          <div style={commonStyle}>Hello</div>
        </Carousel.Item>
        <Carousel.Item>
          <div style={commonStyle}>World</div>
        </Carousel.Item>
        <Carousel.Item>
          <div style={commonStyle}>HeiHei</div>
        </Carousel.Item>
      </Carousel>
    </div>
  );

  act(() => {
    ref.current?.play();
    jest.advanceTimersByTime(1000);
  });
  expect(onChange).not.toBeCalled();
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(onChange).toBeCalledTimes(1);
  expect(onChange.mock.calls[0][0]).toBe(1);
  act(() => {
    ref.current?.pause();
  });
  act(() => {
    jest.advanceTimersByTime(3000);
  });
  expect(onChange).toBeCalledTimes(1);
  expect(onChange.mock.calls[0][0]).toBe(1);

  act(() => {
    ref.current?.update(2);
  });
  const dom = container.firstChild as HTMLElement;
  const carousel = dom.firstChild as HTMLElement;
  const carouselInner = carousel.firstChild as HTMLElement;
  expect(carouselInner).toHaveStyle('transform: translate3d(-200%, 0, 0)');
  act(() => {
    ref.current?.update(10);
  });
  expect(carouselInner).toHaveStyle('transform: translate3d(0%, 0, 0)');

  expect(container).toMatchSnapshot();
});
