import React from "react";

import './index.scss'

type CarouselProps = {
	style?: React.CSSProperties,
	children: React.ReactNode,
}

const CarouselItem = ((props: CarouselProps) => {
	const {children, style} = props
	return (
			<div className="carousel-item" style={style}>
				{children}
			</div>
	)
})

export default CarouselItem