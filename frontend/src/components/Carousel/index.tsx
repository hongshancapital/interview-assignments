/**
 * 轮播组件
 */
import React, { FC } from 'react';
import { useCarousel } from './useCarousel';
import './index.scss';

interface Props {
	interval: number; // 切换间隔 unit: ms
	children?: React.ReactNode;
}

const Carousel = (props: Props) => {
	const itemCount = Array.isArray(props.children) ? props.children.length : 0;
	const { currentIndex, translateX } = useCarousel(itemCount, props.interval);

	if (!props.children) return null;

	return (
		<div className='carousel-container'>
			<div className='carousel-list' style={{ transform: `translateX(${translateX})` }}>
				{props.children}
			</div>
			<div className='carousel-progress'>
				{itemCount > 1 ? Array.from({ length: itemCount }).map((_, index) => {
					return (
						<div className='carousel-progress-item' key={index}>
							{index === currentIndex ? (
								<div className='carousel-progress-item-bg' style={{ animationDuration: `${props.interval}ms` }}></div>
							) : null}
						</div>
					)
				}) : null}
			</div>
		</div >
	)
}

const CarouselItem: FC = (props) => {
	return (
		<div className="carousel-item1">{props.children}</div>
	)
}

Carousel.Item = CarouselItem;
export default Carousel;