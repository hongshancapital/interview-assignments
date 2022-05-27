import React, {useRef} from "react";

import './index.scss'

type IndicatorsProps = {
	autoPlay: Boolean,
	interval: number,
	count: number,
	current: number,
	handleClick: (index: number) => void
}

const Indicators = (props: IndicatorsProps) => {
	const {autoPlay, interval, count, current, handleClick} = props;

	const indicatorsRef = useRef(null);

	if (count <= 1) return null

	return (
			<div className="carousel-indicator" ref={indicatorsRef}>
				{
					new Array(count).fill(null).map((child, index) => {
						return <div key={index} onClick={() => handleClick(index)}
						            className={current === index ? "indicator-item is-active" : "indicator-item"}>
							{
								autoPlay && <div className="indicator-progress" style={{animationDuration: `${interval}ms`}}></div>
							}
						</div>
					})
				}
			</div>
	)
}

export default Indicators