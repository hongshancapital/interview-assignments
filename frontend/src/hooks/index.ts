import React, { useState, useEffect } from "react";

export const useProgressCount = (time: number, isCurrent: boolean) => {
	const [count, setCount] = useState<number>(0);

	useEffect(() => {
		if (isCurrent) {
			let preTime = 0, rafId = 0;
			const animation = (startTime: number) => {
				preTime = preTime || startTime;
				const progress = startTime - preTime;
				setCount(progress);

				if (progress < time) {
					rafId = requestAnimationFrame(animation);
				} 
			}

			rafId = requestAnimationFrame(animation);

			return () => cancelAnimationFrame(rafId);
		}
	}, [time, isCurrent]);

	return count / time;
}