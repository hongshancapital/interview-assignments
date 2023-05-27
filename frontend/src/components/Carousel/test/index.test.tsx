import React from 'react';
import { render, screen } from '@testing-library/react';
import airpods from '../../../assets/airpods.png'
import iphone from '../../../assets/iphone.png'
import tablet from '../../../assets/tablet.png'
import Carousel from '..';

describe('Carousel', () => {
    const items = [
        {
            title: 'xPhone',
            text: 'Lots of love. Less to Spend.',
            imgSrc: iphone,
            style: { color: '#fff' }
        },
        {
            title: 'Tablet',
            text: 'Just the right amount of everything',
            imgSrc: tablet,
        },
        {
            title: 'Buy a Tablet or Xphone for college.',
            text: '',
            imgSrc: airpods,
        },
    ]

    it('渲染个数', () => {
        const { container } = render(<Carousel items={items} />)
        expect(container.querySelector('.item')).toBeInTheDocument()
        expect(container.querySelectorAll('.item').length).toBe(4)
    })

    it('首张图渲染两次', () => {
        const { getAllByText } = render(<Carousel items={items} />)

        expect(getAllByText('Lots of love. Less to Spend.').length).toBe(2)
    })
})
