import React from 'react';
import { render } from '@testing-library/react';
import Carousel, { Slider } from './index';

test('render Slider successfully', () => {
    const sliderProps = {
        className: 'iphone color-white',
        title: 'xPhone',
        description: 'Lots to love. Less to spend.\nStarting at $399.'
    }
    const { container, getByText } = render(<Slider {...sliderProps} />);

    expect(container.firstChild).toHaveClass('iphone')
    expect(container.firstChild).toHaveClass('color-white')

    const titleElement = getByText(sliderProps.title);
    expect(titleElement).toBeInTheDocument();

    const descriptionElement = getByText(/Lots to love. Less to spend.|Starting at \$399./i);
    expect(descriptionElement).toBeInTheDocument();
});

test('render Carousel successfully', () => {
    const props = {
        className: 'my-carousel-wrap',
        children: [<div>1</div>, <div data-testid="second-slider">2</div>],
        duration: 3000
    }
    const { container, getByText, getByTestId } = render(<Carousel {...props} />);
    const firstSlider = getByText('1')
    const secondSlider = getByText('2')

    expect(container.firstChild).toHaveClass(props.className)
    expect(firstSlider).toBeInTheDocument()
    expect(secondSlider).toBeInTheDocument()

    expect(firstSlider.closest('section')).toHaveStyle(`left: 0%`)
    expect(secondSlider.closest('section')).toHaveStyle(`left: 100%`)
});
