import React from 'react'
import { render, fireEvent } from '@testing-library/react'
import SlickList from './index'

test('renders SlickList', () => {
    const onChoose = jest.fn()
    const instance = render(
        <SlickList
            total={3}
            activeIndex={0}
            interval={4000}
            onChoose={onChoose}
        />
    )
    expect(instance).toBeDefined()
    
    const firstChild = instance.container.firstElementChild
    expect(firstChild).toBeInTheDocument();

    fireEvent.click(firstChild as Element)
    expect(onChoose).toHaveBeenCalledTimes(1)
})