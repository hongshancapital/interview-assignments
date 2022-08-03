import React from 'react';
import { render } from '@testing-library/react';
import { CarouselItem } from './index';

const item = {
    color: '#fff',
    title: ['learn react'],
    subTitle: ['hello! react'],
    imageName: 'xphone',
}

test('renders CarouselItem', () => {
    const { getByText, container } = render(<CarouselItem {...item}/>);
    const titleElement = getByText(/learn react/i);
    const subTitleElement = getByText(/hello! react/i);
    expect(titleElement).toBeInTheDocument();
    expect(subTitleElement).toBeInTheDocument();

    const itemDom = container.getElementsByTagName('h1')[0];
    const { color } = window.getComputedStyle(itemDom);
    expect(color).toBe("rgb(255, 255, 255)");
});
