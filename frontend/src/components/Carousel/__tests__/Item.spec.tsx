import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';

import { Item } from '../Item';

describe('Item Component', () => {
  const mockTransitionEndHandler = jest.fn();

  beforeEach(() => {
    mockTransitionEndHandler.mockClear();
  });

  describe('When the Item is the current Item', () => {
    it('should render the children', () => {
      render(
        <Item
          state="current"
          duration={3000}
          onTransitionEnd={mockTransitionEndHandler}
        >
          <div>Item 1</div>
        </Item>
      );

      expect(screen.getByText('Item 1')).toBeInTheDocument();
    });

    it('Should call transition end handler', () => {
      render(
        <Item
          state="current"
          duration={3000}
          onTransitionEnd={mockTransitionEndHandler}
        >
          <div>Item 1</div>
        </Item>
      );

      fireEvent.transitionEnd(screen.getByTestId('item'));

      expect(mockTransitionEndHandler).toHaveBeenCalledTimes(1);
    });
  });

  describe('When the Item is not the current Item', () => {
    it('Should not call transition end handler', () => {
      render(
        <Item
          state="next"
          duration={3000}
          onTransitionEnd={mockTransitionEndHandler}
        >
          <div>Item 1</div>
        </Item>
      );

      fireEvent.transitionEnd(screen.getByTestId('item'));

      expect(mockTransitionEndHandler).toHaveBeenCalledTimes(0);
    });
  });
});
