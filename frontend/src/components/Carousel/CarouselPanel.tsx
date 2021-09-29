import React from 'react';
import { CarouselOptionType } from '.';
export interface CarouselPanelProps {
	children?: React.ReactNode,
	style?: React.CSSProperties,
	option?: CarouselOptionType,
};

function CarouselPanel({ children, option, ...props }: CarouselPanelProps) {
	const classNameQueue: Array<string> = ['carousel-panel'];
	if (option) {
		const { className, render, title, content } = option;
		className && classNameQueue.push(className);
		if (render) {
			children = render();
		} else {
			children = (
				<>
					<h1 className="slide-title" key="title">{title}</h1>
					<p className="slide-content" key="content">{content}</p>
				</>
			);
		}
	}
	return (
		<div {...props} className={classNameQueue.join(' ')}>
			<div className="carousel-panel-inner">
				{children}
			</div>
		</div>
	);
}

export default CarouselPanel;
