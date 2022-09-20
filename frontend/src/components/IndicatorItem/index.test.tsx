import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import IndicatorItem from './index';

describe('IndicatorItem', () => {
  test('IndicatorItem click', () => {
    const handleClick = jest.fn();
    const { container } = render(<IndicatorItem autoPlay active duration={200} onClick={handleClick} />);
    const domNode = container.getElementsByClassName('indicator-item')[0];
    fireEvent.click(domNode);
    expect(handleClick).toHaveBeenCalled();
  });

  test('IndicatorItem auto play and active', () => {
    const { container } = render(<IndicatorItem autoPlay active duration={200} />);
    const domNode = container.getElementsByClassName('indicator-item')[0];
    expect(domNode).toHaveClass('active');
    expect(domNode.firstChild).toHaveStyle('animation-iteration-count: infinite');
  });

  test('IndicatorItem close auto play', () => {
    const { container } = render(<IndicatorItem autoPlay={false} active duration={200} />);
    const domNode = container.getElementsByClassName('indicator-item')[0];
    expect(domNode).toHaveClass('active');
    expect(domNode.firstChild).toHaveStyle('animation-iteration-count: 1');
  });
});