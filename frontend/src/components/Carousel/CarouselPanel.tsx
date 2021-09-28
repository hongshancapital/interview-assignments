import React from 'react';

export interface CarouselPanelProps {
	children: React.ReactNode,
	style?: React.CSSProperties
};

function CarouselPanel({ children, ...props }: CarouselPanelProps) {
	return (
		<div {...props} className="carousel-panel">
			<div className="carousel-panel-inner">
				{children}
			</div>
		</div>
	);
}

export default CarouselPanel;
