import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Carousel from '.';

test('render empty Carousel', () => {
    const { queryByText } = render(<Carousel />);
    expect(queryByText(/Carousel/i)).toBeNull()
});

test('render Carousel', () => {
    const { getByText, container } = render(<Carousel ><div>test1</div><div>test2</div></Carousel>);
    expect(getByText(/test1/i)).toBeInTheDocument();
    fireEvent.click(container.getElementsByClassName('carousel-dot')[1]);
    expect(getByText(/test2/i)).toBeInTheDocument();
});