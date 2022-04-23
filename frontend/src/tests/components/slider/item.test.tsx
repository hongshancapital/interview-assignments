import React from "react"
import { render } from '@testing-library/react'
import { Item } from '../../../components/slider/item'

describe('Slider\'s item', () => {
    it('should render correctly', () => {
        const { getByText, container } = render(
            <Item
                title="Test Title"
                subtitle="Test Subtitle"
                backgroundColor="Test Color"
                backgroundImage="Text Image"
            />
        )
        expect(getByText('Test Title')).toBeInTheDocument()
        expect(getByText('Test Subtitle')).toBeInTheDocument()
        expect(container).toHaveStyle('background-color: Test Color')
        expect(container.querySelector('.footer')).toHaveStyle('background-image: Test Image')
    })
})