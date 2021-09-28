import React from 'react';

export interface CarouselContentProps {
	children: React.ReactNode,
	activeIndex: number,
};

function CarouselContent({ activeIndex, children }: CarouselContentProps) {
	return (
		<div
			className="carousel-content"
			style={{
				marginLeft: `-${activeIndex * 100}%`,
			}}
		>
			{children}
		</div>
	);
}

export default CarouselContent;
