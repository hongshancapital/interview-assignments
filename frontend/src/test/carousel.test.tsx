import React from 'react';
import { render, screen } from '@testing-library/react';
import Carousel from '../components/Carousel'

describe('<Carousel />', () => {
  describe('rendering and mounting', () => {
    it('should correctly mount with children', () => {
      const wrapper = render(
        <Carousel>
          <p>Slide 1</p>
          <p>Slide 1</p>
          <p>Slide 1</p>
        </Carousel>
      );
      const children = wrapper.getAllByText('Slide 1');
      expect(children).toHaveLength(3);
    });
  });
});