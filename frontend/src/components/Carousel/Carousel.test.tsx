import React from "react"
import { fireEvent, render, screen } from "@testing-library/react"
import { act } from "react-dom/test-utils"
import { Carousel, IndicatorsProps } from "./Carousel"
import "@testing-library/jest-dom"

const mockChildren = (count: number, childNode?: React.ReactNode) => {
	const children = []
	for (let i = 0; i < count; i++) {
		if (childNode) {
			children.push(childNode)
		} else {
			children.push(<div key={i}>test-{i}</div>)
		}
	}
	return children
}

const mockTimerRun = (count: number) => {
	for (let i = 0; i < count; i++) {
		act(() => {
			jest.runOnlyPendingTimers()
		})
	}
}

afterEach(() => {
	jest.useRealTimers()
	jest.clearAllTimers()
})

beforeEach(() => {
	jest.useFakeTimers("legacy")
})

describe("Carousel", () => {
	it("className default to carousel-viewport", () => {
		const { container } = render(<Carousel />)
		expect(container.firstChild).toHaveClass("carousel-viewport")
	})

	it("can have custom className", () => {
		const { container } = render(<Carousel className='custom-class-name' />)
		expect(container.firstChild).toHaveClass("custom-class-name")
	})

	it("can have on children", () => {
		render(
			<Carousel>
				<div>test</div>
			</Carousel>
		)
		expect(screen.getByText("test")).toBeInTheDocument()
	})

	it("can have 3 children", () => {
		const { container } = render(<Carousel>{mockChildren(3)}</Carousel>)
		expect(container.querySelectorAll(".carousel-item").length).toBe(4)
	})

	it("can have 5 children", () => {
		const { container } = render(<Carousel>{mockChildren(5)}</Carousel>)
		expect(container.querySelectorAll(".carousel-item").length).toBe(6)
	})

	it("horizontal and auto play", () => {
		jest.useFakeTimers()
		const { container } = render(
			<Carousel autoplay={true}>{mockChildren(5)}</Carousel>
		)
		mockTimerRun(2)
		expect(container.querySelector(".carousel-wrapper")).toHaveStyle(
			"transform: translate(-200%, 0%)"
		)
	})

	it("it can set default active index", () => {
		const { container } = render(
			<Carousel initialIndex={2}>{mockChildren(5)}</Carousel>
		)
		expect(container.querySelector(".carousel-wrapper")).toHaveStyle(
			"transform: translate(-200%, 0%)"
		)
	})

	it("it can set onActiveIndexChange callback", () => {
		jest.useFakeTimers("legacy")
		const callback = jest.fn()
		render(
			<Carousel
				onActiveIndexChange={(index) => {
					callback()
				}}>
				{mockChildren(3)}
			</Carousel>
		)

		expect(callback).toHaveBeenCalledTimes(1)
	})

	it("it can set play delay", () => {
		jest.useFakeTimers("legacy")
		const callback = jest.fn()
		render(
			<Carousel
				autoplay={true}
				delay={2000}
				loop={true}
				onActiveIndexChange={(index) => {
					callback()
					// console.log("it can set play delay", index)
				}}>
				{mockChildren(3)}
			</Carousel>
		)

		act(() => {
			jest.advanceTimersByTime(2000)
		})
		expect(callback).toHaveBeenCalledTimes(2)
	})

	it("it can loop play ", () => {
		jest.useFakeTimers("legacy")
		const callback = jest.fn()
		render(
			<Carousel
				autoplay={true}
				loop={true}
				onActiveIndexChange={(index) => {
					callback()
				}}
				animationDuration={1000}>
				{mockChildren(2)}
			</Carousel>
		)
		mockTimerRun(3)
		expect(callback).toBeCalledTimes(4)
	})

	it("disable loop play ", () => {
		jest.useFakeTimers("legacy")
		const callback = jest.fn()
		render(
			<Carousel
				autoplay={true}
				loop={false}
				onActiveIndexChange={(index) => {
					callback()
				}}
				animationDuration={1000}>
				{mockChildren(2)}
			</Carousel>
		)
		mockTimerRun(3)
		expect(callback).toBeCalledTimes(2)
	})

	it("it can have default Indicators ", () => {
		const { container } = render(
			<Carousel autoplay={true}>{mockChildren(4)}</Carousel>
		)

		mockTimerRun(2)
		expect(
			container.querySelector(".indicator-wrapper")
		).toBeInTheDocument()

		expect(container.querySelectorAll(".process-wrapper").length).toEqual(4)
		expect(container.querySelectorAll(".process-inner")[2]).toHaveClass(
			"active"
		)

		// TODO: after click indicator what Carousel active index test
		// 点击事件
		fireEvent.click(container.querySelectorAll(".process-inner")[1])
		expect(container.querySelectorAll(".process-inner")[1]).toHaveClass(
			"active"
		)
		expect(container.querySelector(".carousel-wrapper")).toHaveStyle(
			"transform: translate(-100%, 0%)"
		)
	})

	it("it can have custom Indicator as ", () => {
		const CustomIndicators: React.FC<IndicatorsProps> = (props) => {
			const { activeIndex = 0, count = 0 } = props
			return (
				<div className='custom-indicator-wrapper'>
					{Array(count)
						.fill(0)
						.map((item, index) => {
							return (
								<div
									className='custom-indicator'
									key={index}
									style={{
										background:
											activeIndex === index
												? "red"
												: "blue",
									}}></div>
							)
						})}
				</div>
			)
		}
		const { container } = render(
			<Carousel
				generateIndicator={(props) => {
					return CustomIndicators(props)
				}}>
				{mockChildren(2)}
			</Carousel>
		)
		expect(
			container.querySelector(".custom-indicator-wrapper")
		).toBeInTheDocument()
		expect(container.querySelectorAll(".custom-indicator")?.length).toEqual(
			2
		)
		// TODO: 指示器点击后轮播图的激活测试
	})
})
