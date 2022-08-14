import React from 'react';
import { render, screen } from '@testing-library/react';
import App, { dataInfos } from './App';

test('test Carousel should render correct data information', () => {
	render(<App />);
	const carouselElement = screen.getByTestId('test-carousel');
	expect(carouselElement).toBeInTheDocument();

	const carouselItems = carouselElement.getElementsByClassName('carousel_item');
	expect(carouselItems.length).toEqual(dataInfos.length);
	dataInfos.forEach((item, index) => {
		expect(screen.getByText(item.title.replace('\n', ''))).toBeInTheDocument();
		item.describe &&
			expect(
				screen.getByText(item.describe.replace('\n', ''))
			).toBeInTheDocument();
		expect(carouselItems[index]).toHaveStyle({ ...item.style });
	});
});
