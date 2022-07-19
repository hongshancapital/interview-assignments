import React, { useEffect, useState } from 'react';
import './index.css';

export interface CarouselProps {
	items: JSX.Element[];
}

interface CarouselState {
	selectedItem: number;
	sliderStyle: React.CSSProperties;
}

const Carousel = ({ items }: CarouselProps) => {
	const initState: CarouselState = {
		selectedItem: 0,
		sliderStyle: {}
	};

	const [state, setState] = useState<CarouselState>(initState);

	useEffect(() => {
		const updateCarousel = () => {
			setState((state) => {
				let nextItem = state.selectedItem + 1;
				if (nextItem >= items.length) {
					nextItem = 0;
				}
				const sliderStyle: React.CSSProperties = { transform: `translateX(${-nextItem * 100}%)` };
				return {
					selectedItem: nextItem,
					sliderStyle: sliderStyle
				};
			});
		};

		let autoPlay = setInterval(updateCarousel, 3000);
		return () => clearInterval(autoPlay);
	}, []);

	const progressStyle = (selectedItem: number, index: number): React.CSSProperties => ({
		width: selectedItem === index ? 40 : 0,
		visibility: selectedItem === index ? 'visible' : 'hidden'
	});

	return (
		<div className="carousel">
			<div className="progress-lines">
				{items.map((val, index) => (
					<div className="progress-line-wrapper">
						<div className="progress-line">
							<div className="progress-line-bg"
							     style={progressStyle(state.selectedItem, index)}>
							</div>
						</div>
					</div>
				))}
			</div>
			<div className="slider-wrapper">
				<ul className="slider" style={state.sliderStyle || {}}
				>{items.map((item, index) => <li className="slide" key={index.toString()}>{item}</li>)}</ul>
			</div>
		</div>
	);
};

export default Carousel;
