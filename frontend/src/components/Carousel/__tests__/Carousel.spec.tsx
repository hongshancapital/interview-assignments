import React from 'react';
import { render, screen } from '@testing-library/react';

import { Carousel } from '../Carousel';

describe('Carousel Component', () => {
  describe('When the Carousel has three children', () => {
    beforeEach(() => {
      render(
        <Carousel>
          <div>1</div>
          <div>2</div>
          <div>3</div>
        </Carousel>
      );
    });

    it('Should render three items', () => {
      expect(screen.getByText('1')).toBeInTheDocument();
      expect(screen.getByText('2')).toBeInTheDocument();
      expect(screen.getByText('3')).toBeInTheDocument();
    });

    it('Should render three indicators', () => {
      expect(screen.getAllByTestId('indicator-item')).toHaveLength(3);
    });
  });
});
