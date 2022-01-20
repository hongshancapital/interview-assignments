import React from 'react'
import { render } from '@testing-library/react'
import { act } from 'react-dom/test-utils'
import Carousel from './index'

jest.mock('./indicator', () => () => <div data-testid="indicator" />)

beforeEach(() => {
    jest.useFakeTimers()
})

/**
 * 测试幻灯片循环一圈后回到第一个幻灯片
 */
test('the Carousel slides in a full loop and go back to the first', () => {
    const { container } = render(
        <Carousel>
            <div>1</div>
            <div>2</div>
            <div>3</div>
        </Carousel>
    )

    expect(container.querySelector('.slides').style.transform).toMatch('translateX(0%)')

    act(() => {
        jest.runOnlyPendingTimers()
    })

    expect(container.querySelector('.slides').style.transform).toMatch('translateX(-100%)')

    act(() => {
        jest.runOnlyPendingTimers()
    })

    expect(container.querySelector('.slides').style.transform).toMatch('translateX(-200%)')

    act(() => {
        jest.runOnlyPendingTimers()
    })

    expect(container.querySelector('.slides').style.transform).toMatch('translateX(0%)')
})

test('the Carousel renders the Indicator component', () => {
    const { getByTestId } = render(
        <Carousel>
            <div>1</div>
            <div>2</div>
            <div>3</div>
        </Carousel>
    )

    expect(getByTestId('indicator')).toBeInTheDocument()
})
