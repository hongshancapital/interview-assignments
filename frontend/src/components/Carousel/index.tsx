import React, { useState, useEffect, useRef, useCallback } from "react";
import './index.css';

export interface CarouselProps {
	children: React.ReactNode;
	style?: React.CSSProperties;
	autoPlay?: Boolean,
	autoPlayTime?: number,
	animationTime?: number,
	easing?: string,
}

export interface CarouselRef {
	goTo: (current: number) => void;
	next: () => void;
	prev: () => void;
}

const Carousel = React.forwardRef<CarouselRef, CarouselProps>(
	(
		{
			children,
			autoPlay = true,
			autoPlayTime = 3000,
			animationTime = 500,
			easing = 'linear',
			style
		},
		ref,
	) => {
		const [width, setWidth] = useState<number>(0);
		const [current, setCurrent] = useState<number>(0);
		const containerRef = useRef<HTMLDivElement>(null);
		let timer = useRef<NodeJS.Timer>();

		const goTo = (cur: number) => {
			setCurrent(cur);
		};

		const prev = useCallback(() => {
			if (current === 0) {
				setCurrent((children as [])?.length - 1);
			} else {
				setCurrent(current - 1);
			}
		}, [current]);

		const next = useCallback(() => {
			if (current >= (children as [])?.length - 1) {
				setCurrent(0);
			} else {
				setCurrent(current + 1);
			}
		}, [current]);

		 const getWidth = () => {
        return containerRef.current?.offsetWidth || 0;
    };


		useEffect(() => {
			setWidth(getWidth());
			window.addEventListener('resize', () => {
				setWidth(getWidth());
			});
			return () => {
				window.removeEventListener("resize", () => {
					setWidth(getWidth());
				});
			}
		}, []);

		useEffect(() => {
			if (timer.current) {
				clearInterval(timer.current);
			}
			if (autoPlay && autoPlayTime) {
				timer.current = setInterval(() => {
					next();
				}, autoPlayTime)
			}

			return () => {
				clearInterval(timer.current as NodeJS.Timer);
			}
		}, [autoPlay, autoPlayTime, next]);

		// 暴露自定义方法
		React.useImperativeHandle(ref, () => ({
			goTo,
			prev,
			next,
		}), []);

		const renderChildren = () => {
			return React.Children.map(children, child => {
				const childClone = React.cloneElement(child as React.ReactElement);
				return (
					<li className="carousel-item" style={{ width }}>
						{childClone}
					</li>
				);
			});
		}

		return (
			<div className='container' style={{ ...style }} ref={containerRef}>
				<ul
					className="carousel-wrap"
					style={{
						width: width * (children as []).length,
						transform: `translateX(-${current * width}px)`,
						transitionDuration: `${animationTime}ms`,
						transitionTimingFunction: `${easing}`
					}}
				>
					{renderChildren()}
				</ul>
				<div className="dot">
					{(children as [])?.map((item: React.ReactNode, index: number) => {
						return (
							<div className="dot-item" key={index} onClick={() => goTo(index)}>
								<div className="dot-item-inner" style={{ width: current === index ? '50px' : 0, transitionDuration: current === index ? `${autoPlayTime}ms` : '0s', transitionTimingFunction: `${easing}` }}></div>
							</div>
						)
					})}
				</div>
			</div>
		);
	},
);

export default Carousel;