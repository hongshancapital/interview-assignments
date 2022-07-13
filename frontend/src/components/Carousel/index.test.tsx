import React from 'react';
import { render } from '@testing-library/react';
import Carousel from './index';

const TestCarousel = () => {

	const data = [
		{
			key: 0,
			title: ['xPhone'],
		},
		{
			key: 1,
			title: ['Tablet'],
		}
	]
	return (
		<Carousel autoPlay>
			{data.map(item => {
				return (
					<div className="item" key={item.key}>
						<div className="item-wrap">
							{
								item.title?.map((title: String, i) => (
									<h1 key={i}>{title}</h1>
								))
							}
						</div>
					</div>
				)
			})}
		</Carousel>
	)
}

test('render Carousel', () => {
	const { getByText } = render(<TestCarousel />);
	const linkElement = getByText('xPhone');
	expect(linkElement).toBeInTheDocument();
});
