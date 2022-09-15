import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Test from '../pages/Test';

describe('Test Component test', () => {
  it('Test Component rendered', () => {
    const { container } = render(<Test />);
    expect(container).toMatchSnapshot();
  });

  it('Test Component contains three carouselItem', () => {
    const { getByTestId } = render(<Test />);
    const carouselInner = getByTestId('carousel-inner-wrapper');
    const indicator = getByTestId('indicator-wrapper');
    expect(carouselInner).toBeInTheDocument();
    expect(carouselInner.childElementCount).toEqual(3);
    expect(indicator).toBeInTheDocument();
    expect(indicator.childElementCount).toEqual(3);
  });

  it('click last Indicator', () => {
    const { getByTestId } = render(<Test />);
    const carouselInner = getByTestId('carousel-inner-wrapper');
    const indicator = getByTestId('indicator-wrapper');
    fireEvent.click(indicator.lastChild!);
    expect(carouselInner).toHaveStyle({ transform: 'translateX(-200%)' });
  });
});
