import React from 'react';
import { render, screen, fireEvent, act } from '@testing-library/react';
import { Carousel } from './carousel';
import { CarouselItem } from './carouselItem';

describe('test Carousel based on default props', () => {
	beforeEach(() => {
		window.console.error = jest.fn();
		jest.spyOn(global, 'setInterval').mockResolvedValue({} as never);
		render(
			<Carousel>
				<CarouselItem>slide 1</CarouselItem>
				<div>slide 2</div>
				<div>slide 3</div>
				<CarouselItem>slide 4</CarouselItem>
				<CarouselItem>slide 5</CarouselItem>
			</Carousel>
		);
	});
	afterEach(() => {
		jest.resetAllMocks();
		jest.restoreAllMocks();
	});
	test('Do no render incorrect children and console error info', () => {
		expect(console.error).toHaveBeenCalledTimes(2);
		expect(console.error).toBeCalledWith(
			'Carousel has a child which is not a CarouselItem component'
		);
		expect(screen.queryByText('slide 2')).toBeFalsy();
		expect(screen.queryByText('slide 3')).toBeFalsy();
	});
	test('Should render correct CarouselItem and CarouselIndicator', () => {
		expect(screen.queryByText('slide 1')).toBeInTheDocument();
		expect(screen.queryByText('slide 4')).toBeInTheDocument();
		expect(screen.queryByText('slide 5')).toBeInTheDocument();

		const carouselIndicator = screen.getByTestId('test-indicator');
		expect(carouselIndicator.getElementsByTagName('li').length).toBe(3);
	});
	test('Click indicator items should change active slide', () => {
		screen.getByTestId('test-indicator').getAnimations = jest
			.fn()
			.mockReturnValue([]);
		const carouselElement = screen.getByTestId('test-carousel');
		const carouselIndicator = screen.getByTestId('test-indicator');

		// get and click third indicator element
		const thirdIndicators = carouselIndicator.querySelectorAll('li')[2];
		fireEvent.click(thirdIndicators);

		expect(carouselElement.querySelector('.inner')).toHaveStyle({
			transform: 'translateX(-200%)',
		});
		expect(thirdIndicators.querySelector('.indicator_inside')).toHaveStyle({
			//  active style
			animationDuration: '3s',
			backgroundColor: '#fff',
		});
	});
});

describe('test autoPlay after initial', () => {
	beforeEach(() => {
		jest.useFakeTimers();
		jest.spyOn(global, 'setInterval');
		render(
			<Carousel intervalTime={5000}>
				<CarouselItem>slide 1</CarouselItem>
				<CarouselItem>slide 2</CarouselItem>
			</Carousel>
		);
		screen.getByTestId('test-indicator').getAnimations = jest
			.fn()
			.mockReturnValue([]);
	});
	afterEach(() => {
		jest.useRealTimers();
		jest.restoreAllMocks();
	});
	test('should trigger autoPlay after initial', () => {
		const carouselElement = screen.getByTestId('test-carousel');
		expect(setInterval).toHaveBeenCalledWith(expect.any(Function), 5000);
		act(() => {
			jest.runOnlyPendingTimers();
		});
		expect(carouselElement.querySelector('.inner')).toHaveStyle({
			transform: 'translateX(-100%)',
		});
		expect(carouselElement.querySelectorAll('li')[1].querySelector('.indicator_inside')).toHaveStyle({
			animationDuration: '5s',
			backgroundColor: '#fff',
		});
		act(() => {
			jest.runOnlyPendingTimers();
		});
		expect(carouselElement.querySelector('.inner')).toHaveStyle({
			transform: 'translateX(-0%)',
		});
		expect(carouselElement.querySelectorAll('li')[0].querySelector('.indicator_inside')).toHaveStyle({
			animationDuration: '5s',
			backgroundColor: '#fff',
		});
	});
});
