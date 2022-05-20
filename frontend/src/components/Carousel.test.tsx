import React from 'react'
import { render } from '@testing-library/react'
import '@testing-library/jest-dom'
import Carousel from './Carousel'

beforeEach(() => {
    jest.useFakeTimers()
})

test('Carousel component render', () => {
    const { getByText } = render(
        <Carousel>
            <p>slider 1</p>
            <p>slider 2</p>
            <p>slider 3</p>
        </Carousel>
    )
    expect(getByText('slider 1')).toBeInTheDocument()
    expect(getByText('slider 2')).toBeInTheDocument()
    expect(getByText('slider 3')).toBeInTheDocument()
})
