import React from 'react';
import { render, cleanup, act, fireEvent } from '@testing-library/react';
import Carousel, { defaultProps } from '.';


const len = 3;
const carouselItemNodes = (len: number) => {
	return new Array(len).fill(0).map((_, i) => {
		return <div key={i} style={{ height: 600 }}>{i + 1}</div>;
	});
};

jest.useFakeTimers();
jest.spyOn(global, 'setTimeout');

describe('<Carousel>{children}</Carousel>', () => {

	afterEach(() => {
		jest.clearAllTimers();
		jest.resetAllMocks();
		cleanup();
	});

	it('should render right layout', async () => {
		const { getByTestId } = render(<Carousel
      {...defaultProps}
      className="my-carousel"
      children={carouselItemNodes(len)}
    />);

    const carousel = getByTestId('carousel');
		expect(carousel.firstChild)
    expect(carousel.firstChild!.firstChild).toBeTruthy();
    expect(carousel.firstChild!.firstChild!.firstChild).toBeTruthy();

    const dots = carousel.querySelectorAll('.carousel-dots-item');
    const highlightDot = dots[0].querySelector('.carousel-dots-highlight');
		expect(highlightDot).toBeInTheDocument();

    const items = carousel.querySelectorAll('.carousel-item');
    expect(items.length).toEqual(len + 2);
	});

  it("should transform when pressing arrow keys", async () => {
		const { getByTestId } = render(
			<Carousel
				{...defaultProps}
				useArrowKeys={true}
				children={carouselItemNodes(3)}
			/>,
		);
		const carousel = getByTestId('carousel');

		fireEvent.click(carousel);
		fireEvent.keyDown(carousel, { keyCode: 39 });

    jest.advanceTimersByTime(defaultProps.duration);

    const dots = carousel.querySelectorAll('.carousel-dots-item');
    const highlightDot = dots[1].querySelector('.carousel-dots-highlight');
		expect(highlightDot).toBeInTheDocument();

	});

  it("should slide when click dots", async () => {
		const { getByTestId } = render(
			<Carousel
				{...defaultProps}
				children={carouselItemNodes(len)}
			/>,
		);

		const carousel = getByTestId('carousel');
    const dots = carousel.querySelectorAll('.carousel-dots-item');
    expect(dots.length).toEqual(len);

		fireEvent.click(dots[1]);
		jest.useFakeTimers();


    const highlightDot = dots[1].querySelector('.carousel-dots-highlight');
		expect(highlightDot).toBeInTheDocument();

	});

  it("should slide when autoPlay is true", async () => {
		const { getByTestId } = render(
			<Carousel
				{...defaultProps}
				children={carouselItemNodes(len)}
			/>,
		);
		act(() => {
			jest.advanceTimersByTime(defaultProps.delay);
		});
		const carousel = getByTestId('carousel');
		const dots = carousel.querySelectorAll('.carousel-dots-item');
		const highlightDot = dots[1].querySelector('.carousel-dots-highlight');
		expect(highlightDot).toBeInTheDocument();
	});
});
