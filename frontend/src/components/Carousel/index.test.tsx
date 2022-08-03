import React from 'react';
import { render } from '@testing-library/react';
import Carousel from './index';


describe('findByText Examples', () => {

  test('renders carousel', () => {
    const { getByText } = render(<Carousel><div>demo</div></Carousel>);
    const carouselElement = getByText(/demo/i);
    expect(carouselElement).toBeInTheDocument();
  });


  test('renders carousel empty', () => {
    const { getByTitle } = render(<div title="con"><Carousel /></div>);
    const content = getByTitle('con').innerHTML;
    expect(content).toBe('')
  });

})
