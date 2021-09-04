import React from "react";
import { render, act } from "@testing-library/react";
import Carousel from "../components/Carousel";
import { SLIDES_DATA } from "../common/constants";


describe('Carousel Component', () => {
	const TIME = 2000;

	beforeEach(() => {
	  jest.useFakeTimers();
	});

  afterEach(() => {
    jest.useRealTimers();
  });

  test.skip('测试正常渲染slide和nav', () => {
		const { container, queryAllByTestId } = render(<Carousel slides={SLIDES_DATA} delay={TIME} />);
		
		expect(queryAllByTestId('slide')).toBeInTheDOM(container);
		expect(queryAllByTestId('nav-progress')).toBeInTheDOM(container);
	});

  test('测试slide是否正常滚动', () => {
		const { getByTestId } = render(<Carousel slides={SLIDES_DATA} delay={TIME} />);

		const styleWidth = `width: ${SLIDES_DATA.length * 100}%;`;
		
		let styleTransform = (index: number) => (`transform: translateX(${-100/SLIDES_DATA.length * index}%);`);

		const node = getByTestId('slides-item');
		expect(node).toHaveAttribute('style', `${styleWidth} ${styleTransform(0)}`);
		
		// 等待2秒
		act(() => jest.advanceTimersByTime(TIME));
		expect(node).toHaveAttribute('style', `${styleWidth} ${styleTransform(1)}`);
	
		// 再等待2秒
		act(() => jest.advanceTimersByTime(TIME));
		expect(node).toHaveAttribute('style', `${styleWidth} ${styleTransform(2)}`);

		// 再再等待2秒
		act(() => jest.advanceTimersByTime(TIME));
		expect(node).toHaveAttribute('style', `${styleWidth} ${styleTransform(0)}`);

		// 再再再等待2秒
		act(() => jest.advanceTimersByTime(TIME));
		expect(node).toHaveAttribute('style', `${styleWidth} ${styleTransform(1)}`);
	});

});