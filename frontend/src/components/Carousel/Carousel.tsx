import React, { useEffect, useMemo, useRef, useState } from "react"
import classnames from "classnames"
import "./Carousel.scss"
import { ProcessIndicators } from "../Indicator/ProcessIndicator"

interface CarouselProps {
	// 自定义 className
	className?: string
	// 动画持续时间
	animationDuration?: number
	// 播放间隔
	delay?: number
	// 是否自动播放
	autoplay?: boolean
	// 初始播放索引
	initialIndex?: number
	// 高度
	height?: string
	// 宽度
	width?: string
	// 是否循环播放
	loop?: boolean
	// 轮播方向
	direction?: "horizontal" | "vertical"
	// 展示内容
	children?: React.ReactNode
	// 生成指示器的函数 为了让指示器方便被替换
	generateIndicator?: (props: IndicatorsProps) => React.ReactNode
	// 指示器变化时的回调
	onActiveIndexChange?: (index: number) => void
}
export interface IndicatorsProps {
	activeIndex?: number
	count?: number
	duration?: number
	Indicator?: React.FC<IndicatorProps>
	setActive?: (index: number) => void
}
export interface IndicatorProps {
	active?: boolean
	animationDuration?: number
	onClick?: () => void
}
/**
 * 轮播图组件
 * @returns
 */
export const Carousel: React.FC<CarouselProps> = ({
	className,
	delay = 3000,
	initialIndex = 0,
	animationDuration = 1000,
	autoplay = true,
	height = "100vh",
	width = "100vw",
	loop = true,
	direction = "horizontal",
	children,
	generateIndicator = (props) => {
		return ProcessIndicators(props)
	},
	onActiveIndexChange,
}: CarouselProps) => {
	const [activeIndex, setActiveIndex] = useState(initialIndex)
	const [sliderIndex, setSliderIndex] = useState(initialIndex)
	const childrenLen = Array.isArray(children) ? children.length : 0
	const [openAnimation, setOpenAnimation] = useState(false)
	const sliderRef = useRef<HTMLDivElement>(null)

	// 指示器应该作为可以替换的部分传入
	const Indicator = useMemo(() => {
		const CustomIndicator = generateIndicator({
			activeIndex: activeIndex,
			count: childrenLen,
			duration: delay,
			setActive: (index: number) => {
				setActiveIndex(index)
			},
		})
		return (
			<div
				className={classnames({
					"carousel-indicator-wrapper": true,
				})}>
				{CustomIndicator}
			</div>
		)
	}, [activeIndex, childrenLen, delay, generateIndicator])

	// 轮播的循环动画
	useEffect(() => {
		if (activeIndex === 0 && sliderIndex !== 0) {
			setSliderIndex(childrenLen)
		} else {
			setSliderIndex(activeIndex)
		}
		const transitionRef = sliderRef?.current
		if (sliderIndex === childrenLen) {
			const handle = () => {
				setOpenAnimation(false)
				setSliderIndex(activeIndex)
				transitionRef?.removeEventListener("transitionend", handle)
			}
			transitionRef?.addEventListener("transitionend", handle)
			return () => {
				transitionRef?.removeEventListener("transitionend", handle)
			}
		}
	}, [activeIndex, childrenLen, sliderIndex, sliderRef])

	// 回调函数
	useEffect(() => {
		onActiveIndexChange && onActiveIndexChange(activeIndex)
	}, [activeIndex, onActiveIndexChange])

	// 播放动画
	useEffect(() => {
		setOpenAnimation(true)
		if (autoplay) {
			if (!loop && activeIndex === childrenLen - 1) {
				return
			}
			const timer = setTimeout(() => {
				const currentIndex = (activeIndex + 1) % childrenLen
				setActiveIndex(currentIndex)
			}, delay)
			return () => {
				clearTimeout(timer)
			}
		}
	}, [activeIndex, autoplay, childrenLen, delay, loop])

	const transformStyles = useMemo(() => {
		return {
			transition: openAnimation ? `all ${animationDuration}ms` : "none",
			transform: `translate(${
				direction === "horizontal" ? -sliderIndex * 100 : 0
			}%, ${direction === "vertical" ? -sliderIndex * 100 : 0}%)`,
		}
	}, [animationDuration, direction, openAnimation, sliderIndex])

	const carouselItemStyle = useMemo(() => {
		return {
			height: height,
			width: width,
			display: direction === "vertical" ? "block" : "inline-block",
		}
	}, [direction, height, width])

	return (
		<div
			className={classnames(className, {
				"carousel-viewport": true,
			})}
			style={{
				width: width,
				height: height,
			}}>
			<div
				ref={sliderRef}
				className={classnames({
					"carousel-wrapper": true,
				})}
				style={transformStyles}>
				{Array.isArray(children)
					? children.map((item: React.ReactNode, index: number) => {
							return (
								<div
									className='carousel-item'
									style={carouselItemStyle}
									key={index}>
									{item}
								</div>
							)
					  })
					: children}
				{Array.isArray(children) && childrenLen ? (
					<div
						className='carousel-item'
						style={carouselItemStyle}
						key={childrenLen}>
						{children[0]}
					</div>
				) : null}
			</div>
			{Indicator}
		</div>
	)
}
