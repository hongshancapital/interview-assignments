import React from 'react'
import { fireEvent, render } from '@testing-library/react'
import Carousel from './index'
import { act } from 'react-dom/test-utils'

describe('Carousel', () => {
    beforeEach(() => {
        jest.useFakeTimers()
    })

    it('renders the first slide by default and auto switches to next slide', async () => {
        const { container } = render(
            <Carousel autoplayInterval={3000}>
                <div className="carousel-item">1</div>
                <div className="carousel-item">2</div>
                <div className="carousel-item">3</div>
            </Carousel>
        )
        const div = container.getElementsByClassName('carousel-container')[0]

        expect(div).toBeInTheDocument()

        expect(div).toHaveStyle('transform: translateX(-0%)')

        act(() => {
            jest.runOnlyPendingTimers()
        })

        expect(div).toHaveStyle('transform: translateX(-100%)')
    })

    it('switches to next slide when click second interval item', async () => {
        const { container } = render(
            <Carousel autoplayInterval={3000}>
                <div className="carousel-item">1</div>
                <div className="carousel-item">2</div>
                <div className="carousel-item">3</div>
            </Carousel>
        )
        const div = container.getElementsByClassName('interval-item')[1]

        const carouselContainer = container.getElementsByClassName('carousel-container')[0]

        fireEvent(
            div,
            new MouseEvent('click', {
                bubbles: true,
                cancelable: true,
            })
        )

        expect(carouselContainer).toHaveStyle('transform: translateX(-100%)')
    })
})
