import React from 'react'
import { render, waitFor, fireEvent } from '@testing-library/react'
import { Group } from '../../../components/slider/group'
import { sleep } from '../../utils/sleep'
import { Group as Bar } from '../../../components/bar/group'

describe('slider', () => {
    const mockConfig = [{
        title: 'A',
        subtitle: 'a',
        backgroundColor: 'color 1',
        backgroundImage: 'image 1'
      }, {
        title: 'B',
        subtitle: 'b',
        backgroundColor: 'color 2',
        backgroundImage: 'image 2'
      }, {
        title: 'C',
        subtitle: 'c',
        backgroundColor: 'color 3',
        backgroundImage: 'image 3'
      }]
    it('should render 3 items', () => {
        const { container } = render(<Group config={mockConfig} duration={500} />)

        expect(container.querySelectorAll('.slider-wrapper').length)
            .toBe(3)
    })

    it('should change transform periodically', async () => {
        const { container } = render(<Group config={mockConfig} duration={500} />)
        const inner = container.querySelector('.slider-group-inner')

        expect(inner).toHaveStyle('transform: translateX(-000%)')
        await waitFor(() => sleep(500))
        expect(inner).toHaveStyle('transform: translateX(-100%)')
        await waitFor(() => sleep(500))
        expect(inner).toHaveStyle('transform: translateX(-200%)')
        await waitFor(() => sleep(500))
        expect(inner).toHaveStyle('transform: translateX(-000%)')
    })

    it('should change transform after swiped', async () => {
        const { container } = render(<Group config={mockConfig} duration={500} />)
        const inner = container.querySelector('.slider-group-inner')

        await waitFor(() => sleep(100))
        expect(inner).toHaveStyle('transform: translateX(-000%)')
        await waitFor(() => {
            fireEvent.mouseDown(window, {
                clientX: 0,
                clientY: 0
            })
            fireEvent.mouseMove(window, {
                clientX: 0,
                clientY: 0
            })
            fireEvent.mouseMove(window, {
                clientX: window.innerWidth / 2,
                clientY: 0
            })
            fireEvent.mouseUp(window, {
                clientX: window.innerWidth / 2,
                clientY: 0
            })
        })
        expect(inner).toHaveStyle('transform: translateX(-100%)')
        await waitFor(() => {
            fireEvent.mouseDown(window, {
                clientX: window.innerWidth,
                clientY: 0
            })
            fireEvent.mouseMove(window, {
                clientX: window.innerWidth,
                clientY: 0
            })
            fireEvent.mouseMove(window, {
                clientX: window.innerWidth / 2,
                clientY: 0
            })
            fireEvent.mouseUp(window, {
                clientX: window.innerWidth / 2,
                clientY: 0
            })
        })
        expect(inner).toHaveStyle('transform: translateX(-000%)')
    })
    it('should change transform after clicked bar', async () => {
        const { container } = render(<>
            <Group config={mockConfig} duration={500} />
            <Bar barCount={3} duration={500} />
        </>)
        const inner = container.querySelector('.slider-group-inner')

        await waitFor(() => sleep(100))
        expect(inner).toHaveStyle('transform: translateX(-000%)')
        const bars = container.querySelectorAll('.bar')
        await waitFor(() => {
            fireEvent.click(
                bars.item(1)
            )
        })
        await waitFor(() => expect(inner).toHaveStyle('transform: translateX(-100%)'))
        await waitFor(() => {
            fireEvent.click(
                bars.item(2)
            )
        })
        await waitFor(() => expect(inner).toHaveStyle('transform: translateX(-200%)'))
        await waitFor(() => {
            fireEvent.click(
                bars.item(0)
            )
        })
        await waitFor(() => expect(inner).toHaveStyle('transform: translateX(-000%)'))
    })
})