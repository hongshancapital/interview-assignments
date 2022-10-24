import React from 'react'
import { render } from '@testing-library/react'
import Carousel from './index'

test('renders Carousel', () => {
    const instance = render(
        <Carousel>
            <div>children1</div>
            <div>children2</div>
        </Carousel>
    )
    expect(instance).toBeDefined()

    const { getByText } = instance
    const childElement = getByText(/children1/i)
    expect(childElement).toBeInTheDocument()
})