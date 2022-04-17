import React from "react"
import { render } from "@testing-library/react"
import { ProcessIndicators } from "./ProcessIndicator"
import "@testing-library/jest-dom"
import { IndicatorProps } from "../Carousel/Carousel"

afterEach(() => {
	jest.useRealTimers()
	jest.clearAllTimers()
})

beforeEach(() => {
	jest.useFakeTimers("legacy")
})

describe("ProcessIndicators", () => {
	it("it can have default Indicators ", () => {
		const activeIndex = 2
		const { container } = render(
			<ProcessIndicators
				activeIndex={activeIndex}
				count={4}></ProcessIndicators>
		)

		expect(
			container.querySelector(".indicator-wrapper")
		).toBeInTheDocument()

		expect(container.querySelectorAll(".process-wrapper").length).toEqual(4)
		expect(
			container.querySelectorAll(".process-inner")[activeIndex]
		).toHaveClass("active")
	})

	it("it can have custom Indicator ", () => {
		const CustomIndicator: React.FC<IndicatorProps> = ({ active }) => {
			return (
				<div
					className='custom-indicator'
					style={{
						background: active ? "red" : "blue",
					}}></div>
			)
		}
		const activeIndex = 2
		const count = 4
		const { container } = render(
			<ProcessIndicators
				count={count}
				activeIndex={activeIndex}
				Indicator={CustomIndicator}></ProcessIndicators>
		)

		expect(container.querySelectorAll(".custom-indicator")?.length).toEqual(
			count
		)
		expect(
			container.querySelectorAll(".custom-indicator")[activeIndex]
		).toHaveStyle("background: red")
		// TODO: 指示器点击后轮播图的激活测试
	})
})
