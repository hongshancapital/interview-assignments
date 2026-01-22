import React from 'react';
import { render, screen } from '@testing-library/react';
import Progress from '..';

describe('Progress', () => {

    it('进度不为0', () => {
        const { container } = render(<Progress rate={'60%'} />)
        expect(container.querySelector('.bar')).toBeInTheDocument()
        expect(container.querySelectorAll('.bar').length).toBe(1)
    })

    it('进度为0', () => {
        const { container } = render(<Progress rate={0} />)
        expect(container.querySelector('.bar')).not.toBeInTheDocument()
        expect(container.querySelectorAll('.bar').length).toBe(0)
    })
})
