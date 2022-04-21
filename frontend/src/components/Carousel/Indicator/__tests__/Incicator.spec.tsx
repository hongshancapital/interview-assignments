import React from 'react';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { Indicator } from '../Indicator';

describe('Indicator Component', () => {
  const mockClickHandler = jest.fn();

  beforeEach(() => {
    mockClickHandler.mockClear();
  });

  describe('When the indicator is the current indicator', () => {
    it('Should render a progress bar', () => {
      render(
        <Indicator
          index={0}
          state="current"
          duration={3000}
          onClick={mockClickHandler}
        />
      );

      expect(screen.queryByTestId('progress-bar')).toBeInTheDocument();
    });
  });

  describe('When the indicator is not the current indicator', () => {
    it('Should not render a progress bar', () => {
      render(
        <Indicator
          index={0}
          state="next"
          duration={3000}
          onClick={mockClickHandler}
        />
      );

      expect(screen.queryByTestId('progress-bar')).not.toBeInTheDocument();
    });
  });

  describe('When the indicator is clicked', () => {
    it('Should call the click handler', () => {
      const indicatorIndex = 0;
      render(
        <Indicator
          index={indicatorIndex}
          state="current"
          duration={3000}
          onClick={mockClickHandler}
        />
      );

      const indicatorElement = screen.getByTestId('indicator-item');
      userEvent.click(indicatorElement);

      expect(mockClickHandler).toHaveBeenCalled();
      expect(mockClickHandler).toHaveBeenCalledTimes(1);
      expect(mockClickHandler).toHaveBeenCalledWith(indicatorIndex);
    });
  });
});
