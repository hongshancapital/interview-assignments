import React, { useEffect, useRef, useState } from "react";
import { IData } from "./App";
import style from "./Carousel.module.scss";
import { CarouselContainer, CarouselWrap, PaginationInner } from "./styles";

function Carousel(props: any) {
	const [index, setIndex] = useState(0);
	const intervalRef = useRef<NodeJS.Timeout>();

	useEffect(() => {
		setIndex(i => (i + 1) % props.data.length);
		intervalRef.current = setInterval(() => {
			setIndex(i => (i + 1) % props.data.length);
		}, props.delay);
		return () => {
			intervalRef.current && clearInterval(intervalRef.current);
		};
	}, [props.delay, props.data.length]);
	return (
		<CarouselContainer {...props} className={style.carousel}>
			<CarouselWrap
				{...props}
				className={style.wrap}
				style={{ transform: "translate3d(" + -index * props.width + "px, 0, 0)" }}
			>
				{props.data.map((item: IData, index: number) => {
					return (
						<div
							key={index}
							className={style.slide}
							style={{
								color: item.style.color,
								backgroundImage: "url(" + item.style.backgroundImage + ")",
							}}
						>
							<h3>
								{item.title.map((item, index) => (
									<React.Fragment key={index}>
										{item}
										<br />
									</React.Fragment>
								))}
							</h3>
							<p>
								{item.desc.map((item, index) => (
									<React.Fragment key={index}>
										{item}
										<br />
									</React.Fragment>
								))}
							</p>
						</div>
					);
				})}
			</CarouselWrap>
			<ul className={style.pagination}>
				{new Array(props.data.length).fill(null).map((item, pIndex) => {
					return (
						<li key={pIndex} className={index === pIndex ? style.active : style.inactive}>
							<PaginationInner {...props}></PaginationInner>
						</li>
					);
				})}
			</ul>
		</CarouselContainer>
	);
}

export default Carousel;
