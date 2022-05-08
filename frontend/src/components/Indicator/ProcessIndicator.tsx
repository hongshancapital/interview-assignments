import React from "react"
import { IndicatorsProps } from "../Carousel/Carousel"
import { Process } from "./Process/Process"
import "./ProcessIndicator.scss"

export const ProcessIndicators: React.FC<IndicatorsProps> = (
	props: IndicatorsProps
) => {
	const {
		activeIndex = 0,
		count = 0,
		duration = 1000,
		Indicator = Process,
		setActive,
	} = props

	return (
		<div className='indicator-wrapper'>
			{Array(count)
				.fill(0)
				.map((item, index) => {
					return (
						<Indicator
							active={activeIndex === index}
							animationDuration={duration}
							onClick={() => {
								setActive && setActive(index)
							}}
							key={index}></Indicator>
					)
				})}
		</div>
	)
}
