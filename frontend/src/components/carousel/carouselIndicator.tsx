import React, { FC, CSSProperties } from 'react';
import style from './carousel.module.css';

interface CarouselIndicatorProps {
	length: number;
	activeIndex?: number;
	duration?: number;
	handleClick?: (index: number) => void;
	indicatorRef: React.RefObject<HTMLUListElement>;
}

export const CarouselIndicator: FC<CarouselIndicatorProps> = ({
	length,
	handleClick,
	activeIndex,
	duration,
	indicatorRef,
}) => {
	const handleClickIndicator = (index: number) => {
		handleClick && handleClick(index);
	};
	const activeStyle: CSSProperties = {
		animationDuration: `${duration}s`,
		backgroundColor: '#fff',
	};
	const renderIndicatorList = () => {
		let indicatorList = [];
		for (let i = 0; i < length; i++) {
			indicatorList.push(
				<li
					className={style.indicator_outer}
					onClick={() => {
						handleClickIndicator(i);
					}}
					key={i.toString()}
				>
					<div
						className={style.indicator_inside}
						style={i === activeIndex ? activeStyle : {}}
					/>
				</li>
			);
		}
		return indicatorList;
	};
	return (
		<ul
			className={style.indicator}
			ref={indicatorRef}
			data-testid="test-indicator"
		>
			{renderIndicatorList()}
		</ul>
	);
};
