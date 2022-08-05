import React from 'react';
import { render, cleanup } from '@testing-library/react';
import Dots from './Dots';
import userEvent from '@testing-library/user-event'

const count = 3;

describe('<Dots />', () => {
	afterEach(cleanup);

	it('should render if count > 0', async () => {
		const onSlideToIndex = jest.fn();
    const currentIndex = 1;
  
		const { container } = render(<Dots
      count={count}
      currentStateIndex={currentIndex}
      delay={3000}
      onSlideToIndex={onSlideToIndex}
    />);

    const dotsWrap = container.querySelector('.carousel-dots');
    const dots = container.getElementsByClassName('carousel-dots-item');
    const selectDot = dots[currentIndex].querySelector('.carousel-dots-highlight');
		
    expect(dotsWrap).toBeInTheDocument();
    expect(dots.length).toEqual(count);
    expect(selectDot).toBeInTheDocument();
	});

	it('should not render if count = 0', async () => {
		const onSlideToIndex = jest.fn();
		const { container } = render(<Dots
      count={0}
      currentStateIndex={1}
      delay={3000}
      onSlideToIndex={onSlideToIndex}
    />);

    const dots = container.querySelector('.carousel-dots');
		expect(dots).toBeNull();
	});

	it('when the click event occurs, current dot gets the focus', async () => {
		const onSlideToIndex = jest.fn();
		const { container } = render(<Dots
      count={count}
      currentStateIndex={1}
      delay={3000}
      onSlideToIndex={onSlideToIndex}
    />);

    const dots = container.querySelectorAll('.carousel-dots-item');
    userEvent.click(dots[1]);
		expect(onSlideToIndex).toHaveBeenCalled();
	});
});
