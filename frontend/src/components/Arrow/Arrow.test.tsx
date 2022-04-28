import React from 'react';
import { render, cleanup, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event'
import Arrow from './Arrow';

describe('<Arrow />', () => {
	afterEach(cleanup);

	it('should render if showArrow is true and call onSlideToDirection prop when click event occurs', async () => {
		const onSlideToDirection = jest.fn();
		const { container, getByText } = render(<Arrow
      showArrow={true}
      onSlideToDirection={onSlideToDirection}
    />);

    const buttons = container.querySelector('.carousel-arrow');
    expect(buttons).toBeInTheDocument();

    const nextButton = getByText("NEXT");
    const prevButton = getByText("PREV");

    userEvent.click(nextButton);
		expect(onSlideToDirection).toHaveBeenCalled();

    userEvent.click(prevButton);
		expect(onSlideToDirection).toHaveBeenCalled();
	});

	it('should not render if showArrow is false', async () => {
    const onSlideToDirection = jest.fn();
    const { container } = render(<Arrow
      showArrow={false}
      onSlideToDirection={onSlideToDirection}
    />);
  
    const buttons = container.querySelector('.carousel-arrow');
    expect(buttons).not.toBeInTheDocument();
	});
});
