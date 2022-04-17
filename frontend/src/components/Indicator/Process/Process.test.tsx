import React from "react"
import { render } from "@testing-library/react"
import { Process } from "./Process"
import "@testing-library/jest-dom"
import { act } from "react-dom/test-utils"
function sleep(ms: number) {
	return new Promise((resolve) => setTimeout(resolve, ms))
}

describe("Process", () => {
	it("render Precess", () => {
		const { container } = render(<Process />)
		expect(container.firstChild).toHaveClass("process-wrapper")
	})

	it("render active Precess", () => {
		const { container } = render(<Process active={true} />)
		expect(container.firstChild?.firstChild).toHaveClass(
			"process-inner active"
		)
	})

	// TODO:测试动画执行
	it("can set animationDuration and animate run in true status", () => {
		jest.useFakeTimers()
		const { container } = render(<Process animationDuration={2000} />)
		act(() => {
			sleep(1000)
		})
		expect(container.firstChild?.firstChild).toHaveStyle(
			"animation-duration: 2000ms"
		)
	})
})
