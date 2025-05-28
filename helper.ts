
import { useState, useEffect, useRef } from "react";

interface UseCarouselProps {
	total?: number;
	speed?: number;
	autoplay?: boolean;
	loop?: boolean;
}

function useInterval(callback: Function, delay: number) {
	const savedCallback = useRef<Function>(() => { });

	useEffect(() => {
		savedCallback.current = callback;
	}, [callback]);

	useEffect(() => {
		function tick() {
			savedCallback && savedCallback.current();
		}
		if (delay !== null) {
			const id = setInterval(tick, delay);
			return () => clearInterval(id);
		}
	}, [delay]);
}

function useCarousel({
	total = 0,
	speed = 2000,
	autoplay = false,
	loop = true,
}: UseCarouselProps) {
	const [offset, setOffset] = useState(0);
	const [items, setItems] = useState<any[]>([]);

	function run() {
		if (offset === total - 1) {
			setOffset(loop ? 0 : offset);
		} else {
			setOffset(offset + 1);
		}
	}

	function prev() {
		if (loop) {
			setOffset((offset - 1 + total) % total);
		} else {
			setOffset(offset === 0 ? 0 : offset - 1);
		}
	}

	function next() {
		if (loop) {
			setOffset((offset + 1) % total);
		} else {
			setOffset(offset ===  total - 1 ? total - 1 : offset + 1);
		}
	}

	function goTo(index: number) {
		setOffset(index);
	}

	function addItem(ref: any) {
		setItems([...items, ref]);
	}

	useInterval(() => {
		if (autoplay && offset < total) {
			run();
		}
	}, speed);

	return { offset, addItem, setItems, prev, next, goTo };
}

export {
	useInterval,
	useCarousel,
};
