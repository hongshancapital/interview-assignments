import React from 'react';
import { render } from '@testing-library/react';
import CarouselItem from '.';

test('render empty', () => {
    const { queryByText } = render(<CarouselItem />);
    expect(queryByText(/CarouselItem/i)).toBeNull()
});

test('render text', () => {
    const { getByText } = render(<CarouselItem title={['CarouselItem']} />);
    expect(getByText(/CarouselItem/i)).toBeInTheDocument();
});
