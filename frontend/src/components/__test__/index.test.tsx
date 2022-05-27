import React from "react";
import {render} from "@testing-library/react";
import {act} from "@testing-library/react-hooks";
import Carousel, {CarouselRefs} from '../Carousel/index'
import Item from '../Item'
describe("Carousel Component", () => {
	it("组件包含goTo, prev, next函数", () => {
		const carouselRef = React.createRef<CarouselRefs>()
		const Component = () => {
			return <Carousel ref={carouselRef}>
				<Item>xPhone</Item>
				<Item>tablet</Item>
				<Item>air pods</Item>
			</Carousel>
		}
		render(<Component />)

		expect(typeof carouselRef.current?.goTo).toBe('function')
		expect(typeof carouselRef.current?.prev).toBe('function')
		expect(typeof carouselRef.current?.next).toBe('function')
	})

	it('某个indicator的状态应该是is-active', () => {
		const carouselRef = React.createRef<CarouselRefs>()
		const Component = () => {
			return <Carousel ref={carouselRef}>
				<Item>xPhone</Item>
				<Item>tablet</Item>
				<Item>air pods</Item>
			</Carousel>
		}
		const { container } = render(<Component />);

		const indicatorList =container.querySelectorAll('.indicator-item')
		expect(indicatorList[0].className.indexOf('is-active') > -1).toBeTruthy()
		act(() => {
			carouselRef.current?.goTo(1)
		})
		expect(indicatorList[1].className.indexOf('is-active') > -1).toBeTruthy()
		act(() => {
			carouselRef.current?.goTo(4)
		})
		expect(indicatorList[0].className.indexOf('is-active') > -1).toBeTruthy()
	})
})