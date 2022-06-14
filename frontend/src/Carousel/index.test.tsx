import React from 'react';
import { render } from '@testing-library/react';
import { Carousel } from './index';
import { LIST } from './const';

describe('Carousel', () => {
  test('renders Carousel', () => {
    const { getByText, container } = render(<Carousel list={LIST} duration={2000} />);
    const item1 = getByText(/Less to spend/i);
    const item2 = getByText(/Just the right amount of everything./i);
    const item3 = getByText(/Buy a Tablet or xPhone for college./i);
    expect(item1).toBeInTheDocument();
    expect(item2).toBeInTheDocument();
    expect(item3).toBeInTheDocument();

    const itemDom = container.getElementsByClassName('indicator-lines')[0];
    const targetDom = itemDom.getElementsByTagName('span')[0];
    const { animationDuration } = window.getComputedStyle(targetDom);
    expect(animationDuration).toBe("2s");
  });
});