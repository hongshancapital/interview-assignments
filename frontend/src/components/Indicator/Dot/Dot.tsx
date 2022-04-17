import React from "react"
import classnames from "classnames"
import "./Dot.scss"
import { IndicatorProps } from "../../Carousel/Carousel"

export const Dot: React.FC<IndicatorProps> = (props: IndicatorProps) => {
	return (
		<div
			className={classnames("dot-wrapper", {
				active: props.active,
			})}
			onClick={() => {
				props.onClick && props.onClick()
			}}></div>
	)
}
