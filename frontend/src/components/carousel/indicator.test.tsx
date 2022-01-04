import React from 'react'
import { render } from '@testing-library/react'
import Indicator from './indicator'

test('indicators count equals to 3', () => {
    const { container } = render(<Indicator count={3} index={0} duration={0} />)

    expect(container.querySelectorAll('.line').length).toBe(3)
})

test('the second indicator is activated', () => {
    const { container } = render(<Indicator count={3} index={1} duration={0} />)
    const indicators = container.querySelectorAll('.line')

    expect(indicators[0].classList.contains('active')).toBeFalsy()
    expect(indicators[1].classList.contains('active')).toBeTruthy()
    expect(indicators[2].classList.contains('active')).toBeFalsy()
})
