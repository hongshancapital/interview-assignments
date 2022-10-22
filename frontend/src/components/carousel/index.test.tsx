import React from 'react';
import { render, configure, getAllByTestId } from '@testing-library/react';
import Carousel from './index';

import { carouselList } from '../../mock'


configure({ testIdAttribute: 'class' })


test("renders without crashing when carouselItems is empty ", () => {
  render( <Carousel sourceList={[]} duration={3000} />);
});


describe('render carousel component', () => {
  const carouselJsx = <Carousel
  sourceList={ carouselList }
  />

  test('check carousel item anchor count', () => {
    const { container } = render(carouselJsx);
    const anchors = getAllByTestId(container, 'carousel-item_anchor')
    expect(anchors.length).toBe(carouselList.length);
  });

  
  
})


