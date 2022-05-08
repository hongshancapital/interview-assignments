import React from "react"
import classnames from "classnames"
import "./Process.scss"
import { IndicatorProps } from "../../Carousel/Carousel"

export const Process: React.FC<IndicatorProps> = ({
	active = false,
	animationDuration = 1000,
	onClick,
}: IndicatorProps) => {
	const animationStyle = {
		animationDuration: animationDuration + "ms",
	}
	return (
		<div
			className='process-wrapper'
			onClick={() => {
				onClick && onClick()
			}}>
			<div
				className={classnames("process-inner", {
					active: active,
				})}
				style={animationStyle}></div>
		</div>
	)
}
