import React from "react"
import { render, screen } from "@testing-library/react"
import { Banner } from "./Banner"
import "@testing-library/jest-dom"
import iphone from "../../assets/iphone.png"
describe("Banner", () => {
	it("render title", () => {
		const { container } = render(<Banner title='test-title-for-banner' />)
		expect(container.querySelector(".banner-wrapper")).toBeInTheDocument()
		expect(screen.getByText("test-title-for-banner")).toBeInTheDocument()
		expect(container.querySelector(".banner-title")?.textContent).toEqual(
			"test-title-for-banner"
		)
	})

	it("render desc", () => {
		const { container } = render(<Banner desc='test-desc-for-banner' />)
		expect(container.querySelector(".banner-wrapper")).toBeInTheDocument()
		expect(screen.getByText("test-desc-for-banner")).toBeInTheDocument()
		expect(container.querySelector(".banner-desc")?.textContent).toEqual(
			"test-desc-for-banner"
		)
	})

	it("render img", () => {
		const { container } = render(<Banner img={iphone} />)
		expect(container.querySelector(".banner-img")).toBeInTheDocument()
		expect(container.querySelector(".banner-img")).toHaveAttribute(
			"src",
			iphone
		)
	})

	it("render background", () => {
		const { container } = render(<Banner background='#000' />)
		expect(container.querySelector(".banner-wrapper")).toHaveStyle(
			"background-color: rgb(0, 0, 0)"
		)
	})

	it("render color", () => {
		const { container } = render(<Banner color='#000' />)
		expect(container.querySelector(".banner-wrapper")).toHaveStyle(
			"color: rgb(0, 0, 0)"
		)
	})

	it("can have custom className", () => {
		const { container } = render(
			<Banner
				title='test-for-banner'
				img={iphone}
				color='#000'
				className='custom-class-name'
			/>
		)
		expect(container.firstChild).toHaveClass("custom-class-name")
	})
})
