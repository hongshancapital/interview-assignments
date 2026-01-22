import React from 'react';
import { render, screen } from '@testing-library/react';
import Slider from '..';

describe('Slider', () => {

    it('渲染结果', () => {
        const { getAllByText, container } = render(<Slider>
            <div>aaa</div>
            <div>bbb</div>
            <div>ccc</div>
        </Slider>)

        expect(getAllByText('aaa').length).toBe(2)
        expect(getAllByText('bbb').length).toBe(1)
        expect(getAllByText('ccc').length).toBe(1)
        expect(container.querySelectorAll('.dot').length).toBe(3)
    })
})
