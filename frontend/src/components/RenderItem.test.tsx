import React from 'react'
import { render } from '@testing-library/react'
import '@testing-library/jest-dom'
import RenderItem from './RenderItem'
import { CardItem } from './Carousel'

beforeEach(() => {
    jest.useFakeTimers()
})

test('RenderItem based on item', () => {
    const item: CardItem = {
        key: 1,
        url: '',
        title: 'xPhone',
        color: '#fff'
    }
    const { getByText } = render(
        <RenderItem item={ item }/>
    )
    expect(getByText('xPhone')).toBeInTheDocument()
})
