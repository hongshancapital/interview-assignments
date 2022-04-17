import React from "react"
import { render } from "@testing-library/react"
import App from "./App"
import "@testing-library/jest-dom"

describe("App", () => {
	it("className default to carousel-viewport", () => {
		const { container } = render(<App />)
		expect(container.firstChild?.firstChild).toHaveClass(
			"carousel-viewport"
		)
	})
})
