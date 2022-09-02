import React from 'react';
import { render } from '@testing-library/react';
import Indicator from 'src/components/indicator';
import { fireEvent } from '@testing-library/react';

const animationProps = {
  animationDuration: '3s',
  animationMoveDuration: '0.5s',
  activeIndex: 0,
  currentIndex: 0,
}

describe('Indicator test', () => {
  test('Indicator should display', () => {

    const onAnimationEnd = jest.fn();

    const { getByTestId } = render(<Indicator {...animationProps} onAnimationEnd={onAnimationEnd} />);

    const indicatorDOM = getByTestId("indicator");

    expect(indicatorDOM).toBeInTheDocument();
  })

  test('onAnimationEnd called', () => {

    const onAnimationEnd = jest.fn();

    const { getByTestId } = render(<Indicator {...animationProps} onAnimationEnd={onAnimationEnd} />);

    expect(onAnimationEnd).toHaveBeenCalledTimes(0);

    fireEvent.animationEnd(getByTestId('indicator-item'));

    expect(onAnimationEnd).toHaveBeenCalledTimes(1);
  })
});
