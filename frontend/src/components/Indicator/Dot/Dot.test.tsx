import React from "react"
import { render } from "@testing-library/react"
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
})
