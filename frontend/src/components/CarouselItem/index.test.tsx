import React from 'react';
import { render } from '@testing-library/react';
import CarouselItem from './index';

describe('CarouselItem', () => {
  test('IndicatorItem has sub title', () => {
    const { container } = render(<CarouselItem title='xPhone' subTitle='xxx' />);
    const domNode = container.getElementsByClassName('sub-title')[0];
    expect(domNode).toBeDefined();
  });
});