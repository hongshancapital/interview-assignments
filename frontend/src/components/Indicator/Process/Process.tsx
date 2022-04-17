import React from "react"
import classnames from "classnames"
import "./Process.scss"
import { IndicatorProps } from "../../Carousel/Carousel"

export const Process: React.FC<IndicatorProps> = (props: IndicatorProps) => {
	const animationStyle = {
		animationDuration: props.animationDuration + "ms",
	}
	return (
		<div
			className='process-wrapper'
			onClick={() => {
				props.onClick && props.onClick()
			}}>
			<div
				className={classnames("process-inner", {
					active: props.active,
				})}
				style={animationStyle}></div>
		</div>
	)
}
