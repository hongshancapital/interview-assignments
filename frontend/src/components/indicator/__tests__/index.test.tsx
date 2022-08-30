import React from 'react';
import { render } from '@testing-library/react';
import Indicator from 'src/components/indicator';

const animationProps = {
  animationDuration: '3s',
  animationMoveDuration: '0.5s',
  activeIndex: 0,
  currentIndex: 0,
}

describe('indicator test', () => {
  test('appear', () => {

    const onAnimationEnd = jest.fn();

    const { getByTestId } = render(<Indicator {...animationProps} onAnimationEnd={onAnimationEnd} />);

    const indicatorDOM = getByTestId("indicator");

    expect(indicatorDOM).toBeInTheDocument();
  })

  test('onAnimationEnd called', (done) => {

    const onAnimationEnd = jest.fn();

    render(<Indicator {...animationProps} onAnimationEnd={onAnimationEnd} />);

    expect(onAnimationEnd).toHaveBeenCalledTimes(0);

    setTimeout(() => {
      expect(onAnimationEnd).toHaveBeenCalledTimes(1);
      done();
    }, 4000);
  })
});
