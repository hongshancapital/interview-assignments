import React, { useRef, useState, useEffect, FC } from 'react';

interface ImageObject {
	url: string;
	title?: string[] | React.ReactNode[];
	description?: string[] | React.ReactNode[];
}

interface CarouselProps {
	images: ImageObject[];
	duration?: number;
}

const Carousel: FC<CarouselProps> = ({ images, duration = 3000 }) => {
	const [currentSlide, setCurrentSlide] = useState(0);
	const [isDragging, setIsDragging] = useState(false);
	const [dragStart, setDragStart] = useState(0);
	const [dragEnd, setDragEnd] = useState(0);
	const [transition, setTransition] = useState("0.5s ease-out");
	const [progress, setProgress] = useState(0);
	const sliderRef = useRef<HTMLDivElement>(null);
	const innerRef = useRef<HTMLDivElement>(null);
	const timerRef = useRef<NodeJS.Timeout>();

	const getNextSlide = (current: number, delta: number) => {
		let next = current + delta;
		if (next < 0) {
			next = images.length - 1;
		} else if (next >= images.length) {
			next = 0;
		}
		return next;
	};

	const handlePrev = () => {
		const nextSlide = getNextSlide(currentSlide, -1);
		setCurrentSlide(nextSlide);
		setTransition("0.5s ease-out");
		setProgress((nextSlide + 1) * 100);
	};

	const handleNext = () => {
		const nextSlide = getNextSlide(currentSlide, 1);
		setCurrentSlide(nextSlide);
		setTransition("0.5s ease-out");
		setProgress((nextSlide + 1) * 100);
	};

	const handleDragStart = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
		setDragStart(event.clientX);
		setIsDragging(true);
		setTransition("none");
	};

	const handleDragMove = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
		if (isDragging) {
			setDragEnd(event.clientX);
		}
	};

	const handleDragEnd = () => {
		if (isDragging) {
			const delta = dragEnd - dragStart;
			if (Math.abs(delta) > 100) {
				setCurrentSlide((prev) => getNextSlide(prev, delta > 0 ? -1 : 1));
				setProgress((currentSlide + 1) * 100);
			}
			setIsDragging(false);
			setDragStart(0);
			setDragEnd(0);
			setTransition("0.5s ease-out");
		}
	};

	useEffect(() => {
		const inner = innerRef.current;
		if (inner) {
			inner.addEventListener("transitionend", () => {
				setTransition("none");
			});
		}

		timerRef.current = setInterval(() => {
			handleNext();
		}, duration);

		return () => {
			if (timerRef.current) {
				clearInterval(timerRef.current);
			}
			if (inner) {
				inner.removeEventListener("transitionend", () => {
					setTransition("none");
				});
			}
		};
	}, [currentSlide, duration, handleNext]);

	return (
		<div
			style={{
				position: "relative",
				width: "100vw",
				height: "100vh",
				overflow: "hidden",
			}}
			onMouseDown={handleDragStart}
			onMouseMove={handleDragMove}
			onMouseUp={handleDragEnd}
			onMouseLeave={handleDragEnd}
		>
			<div
				ref={sliderRef}
				style={{
					position: "absolute",
					display: "flex",
					width: `${images.length * 100}vw`,
					height: "100%",
					left: `-${currentSlide * 100}vw`,
					transition,
				}}
			>
				{images.map((image, index) => (
					<div
						key={index}
						style={{
							position: "relative",
							width: "100vw",
							height: "100%",
							backgroundImage: `url(${image.url})`,
							backgroundPosition: "center",
							backgroundSize: "cover",
							display: "flex",
							justifyContent: "center",
							alignItems: "center",
						}}
					>
						<div
							style={{
								position: "absolute",
								bottom: "50%",
							}}
						>
							<div style={{ fontSize: 40 }}>
								{image.title?.map(txt => (
									<p>{txt}</p>
								))}
							</div>
							<div style={{ fontSize: 25 }}>
								{image.description?.map(txt => (
									<p>{txt}</p>
								))}
							</div>
						</div>
					</div>
				))}
			</div>
			<div
				ref={innerRef}
				style={{
					position: "absolute",
					width: "100%",
					bottom: "20px",
					display: "flex",
					justifyContent: "center",
				}}
			>
				{images.map((image, index) => (
					<div
						key={index}
						style={{
							width: "60px",
							height: "3px",
							backgroundColor: currentSlide === index ? "#fff" : "#6b6b6b",
							marginLeft: index === 0 ? 0 : "10px",
						}}
					/>
				))}
			</div>
		</div>
	);
};

export default Carousel;