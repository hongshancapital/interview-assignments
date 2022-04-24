import React from "react"
import { fireEvent, render } from "@testing-library/react"
import { Dot } from "./Dot"
import "@testing-library/jest-dom"

describe("Dot", () => {
	it("render Dot", () => {
		const { container } = render(<Dot />)
		expect(container.firstChild).toHaveClass("dot-wrapper")
	})

	it("render active Dot", () => {
		const { container } = render(<Dot active={true} />)
		expect(container.firstChild).toHaveClass("active")
	})

	it("render click Dot", () => {
		const callback = jest.fn()
		const { container } = render(
			<Dot
				onClick={() => {
					callback()
				}}
			/>
		)
		expect(container.firstChild).toHaveClass("dot-wrapper")
		// 测试点击事件
		fireEvent.click(container.firstChild!)
		expect(callback).toBeCalled()
	})
})
