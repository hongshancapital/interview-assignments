import React from 'react';
import { render, configure, getByTestId, getAllByTestId } from '@testing-library/react';
import Carousel from './index';


configure({ testIdAttribute: 'class' })

describe('render carousel component', () => {
  const carouselJsx = <Carousel
    items={
      [
        { content: 1, bgCover: 'pic 1' },
        { content: 2, bgCover: 'pic 2' },
        { content: 3, bgCover: 'pic 3' },
      ]
    }
  />

  test('check carousel item anchor count', () => {
    const { container } = render(carouselJsx);
    const anchors = getAllByTestId(container, 'carousel-item-anchor')
    expect(anchors.length).toBe(3);
  });
  
  test('click last item anchor', () => {
    const { container } = render(carouselJsx);
    const anchors = getAllByTestId(container, 'carousel-item-anchor')
    const lastItemIndex = anchors.length - 1
    const lastItem = anchors[lastItemIndex]
    lastItem.click()
    const itemWrapper = getByTestId(container, 'carousel-items-wrapper')

    expect(itemWrapper.getAttribute('style')).toBe(`transform: translate(${-lastItemIndex * 100}%, 0px);`)
  })
})
