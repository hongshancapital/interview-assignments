import React, { useState, useEffect } from "react";

export const useProgressCount = (time: number) => {
	const [count, setCount] = useState<number>(0);

	useEffect(() => {
		let preTime = 0, rafId = 0;
		const animation = (startTime: number) => {
			preTime = preTime || startTime;
			const progress = startTime - preTime;
			setCount(startTime - preTime);

			if (progress < time) {
				rafId = requestAnimationFrame(animation);
			} 
			// console.log(startTime)
		}

		rafId = requestAnimationFrame(animation);

		return () => cancelAnimationFrame(rafId);
	}, [time]);

	return count / time;
}