import React from 'react'
import { render, waitFor } from '@testing-library/react'
import { Group } from '../../../components/slider/group'
import { sleep } from '../../utils/sleep'

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
})