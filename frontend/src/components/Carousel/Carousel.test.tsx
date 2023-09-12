import React from 'react';
import { render } from '@testing-library/react';
import Carousel from './Carousel';

test('Carousel Item length should be 1', () => {
    const bg = '#000000';
    const { container } = render(
        <Carousel>
            <Carousel.Item bg={bg}></Carousel.Item>
        </Carousel>
    )
    const items = container.querySelectorAll('.carousel-item')
    expect(items.length).toEqual(1)
})