import React from 'react';

export interface CarouselBarProps {
	total: number,
	current: number,
	duration: number,
};

interface SliderProps {
	running: boolean,
	duration: number,
};

function Slider({ running, duration }: SliderProps) {
	return (
		<span className="slider-item">
			<span
				className={running ? 'running': 'stop'}
				style={{ animationDuration: `${duration}ms` }}
			/>
		</span>
	);
}

function CarouselBar({ duration, current, total } : CarouselBarProps) {
	return (
		<div className="carousel-bar">
			{Array(total).fill(null).map((_, i) => {
				return (
					<Slider
						key={i}
						duration={duration}
						running={i === current}
					/>
				);
			})}
		</div>
	);
}

export default CarouselBar;
