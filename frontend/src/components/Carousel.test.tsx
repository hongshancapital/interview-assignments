import React from 'react';
import { render } from '@testing-library/react';
import Carousel from './Carousel';
import { CarouselItemData } from './interface';
import airpods from '../assets/airpods.png'
import iphone from '../assets/iphone.png'
import tablet from '../assets/tablet.png'

const list: CarouselItemData[] = [
    {
        url: iphone,
        alt: 'iphone',
        title: <p style={{ color: '#fff', fontSize: '28px' }}>xPhone</p>,
        desc: <>
            <p style={{ color: '#fff', fontSize: '14px' }}>Lots to love. Less to speed. </p>
            <p style={{ color: '#fff', fontSize: '14px' }}>Starting at $399'</p>
        </>,
        backgroundColor: '#111'
    },
    {
        url: tablet,
        alt: 'tablet',
        title: <p style={{ fontSize: '28px' }}>Tablet</p>,
        desc: <p style={{ fontSize: '14px' }}>Just the right amount of ervrything.</p>,
        backgroundColor: '#fafafa'
    },
    {
        url: airpods,
        alt: 'airpods',
        title: <p style={{ fontSize: '28px' }}>Buy a Tablet or xPhone for college</p>,
        desc: <p style={{ fontSize: '28px' }}>Get arPods.</p>,
        backgroundColor: '#f1f1f3'
    }
]

test('Carousel only one item works', () => {
    const data = list.slice(0, 1)
    const { container } = render(<Carousel list={data} />)
    expect(container.children.length).toBe(1)
});

test('Carousel has more then one item works', () => {
    const { container } = render(<Carousel list={list} />)
    expect(container.firstChild?.childNodes?.length).toBe(2)
    expect(container.firstChild?.firstChild?.childNodes?.length).toBe(3)
    expect(container.firstChild?.lastChild?.childNodes?.length).toBe(3)
})