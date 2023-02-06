import React from 'react';
import { render } from '@testing-library/react';
import Sample from '../../Sample';

test('render three pages', () => {
    const { container } = render(<Sample />);
    const carouselElement = container.firstChild;
    expect(carouselElement).toBeInTheDocument();
    expect(carouselElement?.firstChild).toBeInTheDocument();
    expect(carouselElement?.firstChild?.childNodes.length).toEqual(3);
});
