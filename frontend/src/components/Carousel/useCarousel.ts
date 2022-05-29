import { useEffect, useState, useRef } from "react";

export const useCarousel = (count: number, interval: number) => {
	const [currentIndex, setCurrentIndex] = useState(0);
	const timerRef = useRef<number>();

	useEffect(() => {
		clearTimeout(timerRef.current)
		if(count === 0) return;
		
		timerRef.current = window.setTimeout(() => {
			if(currentIndex === count - 1){
				setCurrentIndex(0);
			}else{
				setCurrentIndex(currentIndex + 1);
			}
		}, interval) as unknown as number;

		return () => {
			clearTimeout(timerRef.current);
		}
	}, [currentIndex, count, interval])

	// 当前item偏移量
	const translateX = -(currentIndex * 100) + '%';

	return {
		currentIndex,
		translateX,
	}
}