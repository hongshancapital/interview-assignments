import React from 'react'
import { render } from '@testing-library/react'
import SlickList from './index'

test('renders SlickList', () => {
    const instance = render(
        <SlickList
            total={3}
            activeIndex={0}
            interval={4000}
        />
    )
    
    expect(instance).toBeDefined()
})