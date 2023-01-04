import React, { useEffect, useState, useRef } from 'react';
import { Card, CardData } from './Card';
import { Dot } from './Dot';
import { useLatest } from '../../utils';
import './index.css';

export interface CarouselProps {
	data: CardData[]
	delay?: number;
	duration?: number;
}

const Carousel: React.FC<CarouselProps> = ({
	data, 
	delay = 3, 
	duration = 0.8
}) => {
	const len = data.length;
	const [currenIndex, setCurrentIndex] = useState(0);
	const timerRef = useRef<NodeJS.Timer | null>(null);

	const updateIndex = useLatest(() => {
		setCurrentIndex((currenIndex + 1) % len);
	});

	useEffect(() => {
		timerRef.current = setInterval(() => {
			updateIndex.current();
		}, delay * 1000);
		return () => {
			if (timerRef.current) {
				clearInterval(timerRef.current);
			}
		}
	}, []);

	return (
		<div className='carousel-wrapper'>
			<div className='card-wrapper'
				style={{
					transition: `${duration}s`,
					transform: `translateX(${-100*currenIndex}%)`
				}}
				>
				{data.map((item, index) => <Card data={item} key={`card-${index}`}></Card>)}
			</div>
			<div className='dot-wrapper'>
				{data.map((item, index) => <Dot 
					key={`dot-${index}`}
					isActive={index === currenIndex}
					duration={delay}
				></Dot>)}
			</div>
		</div>
	);
}

export default Carousel;