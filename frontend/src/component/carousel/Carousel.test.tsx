import React from 'react';
import { render } from '@testing-library/react';
import { Card } from './Card';
import { Dot } from './Dot';
import Carousel from './index';
import { testData } from '../../App';

const cardData = testData.data[0]

describe('Card', () => {

	test('title render', () => {
		const { getByText } = render(<Card data={cardData}></Card>);
		cardData.titles.forEach(title => {
			expect(getByText(title)).toBeInTheDocument();
		});
	});

	test('img render', () => {
		const { container } = render(<Card data={cardData}></Card>);
		const imgEl = container.querySelectorAll('.card-img');
		expect(imgEl.length).toEqual(1);
		expect(imgEl[0]).toBeInTheDocument();
	});

});

describe('Dot', () => {

	test('active render', () => {
		const { container } = render(<Dot isActive={true} duration={3}></Dot>);
		const activeEl = container.querySelector('.active');
		expect(activeEl).toBeInTheDocument();
	});

	test('active render', () => {
		const { container } = render(<Dot isActive={false} duration={3}></Dot>);
		const activeEl = container.querySelector('.active');
		expect(activeEl).toBeNull();
	});

});

describe('Carousel', () => {

	test('dot active', () => {
		const { container } = render(<Carousel {...testData}></Carousel>);
		const dotEls = container.querySelectorAll('.dot');
		expect(dotEls[0]).toHaveClass('active');
	});

	test('card render', () => {
		const { container } = render(<Carousel {...testData}></Carousel>);
		const cardEls = container.querySelectorAll('.card');
		expect(cardEls.length).toEqual(testData.data.length);
	});

});