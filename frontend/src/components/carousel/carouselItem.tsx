import React, { FC, ReactNode } from 'react';
import style from './carousel.module.css';

export interface CarouselItemProps {
	children: ReactNode;
	styles?: object;
	key?: string | number;
}

const CarouselItem: FC<CarouselItemProps> = ({
	children,
	styles = {},
	key,
}) => {
	return (
		<div key={key} style={{ ...styles }} className={style.carousel_item}>
			<section className={style.text_section}>
				{children}
			</section>
		</div>
	);
};
CarouselItem.displayName = 'CarouselItem';
export { CarouselItem };
