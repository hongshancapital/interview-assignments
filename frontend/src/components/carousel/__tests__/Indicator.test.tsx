import { render } from '@testing-library/react';
import Indicator from '../Indicator';

describe('Indicator', () => {
  it('should render Indicator component', () => {
    const { container, asFragment } = render(<Indicator currentIndex={0} total={3} duration={1000} />);
    expect(asFragment()).toMatchSnapshot();
    expect(container.querySelectorAll('.indicator__item')[0]).toHaveClass('indicator__item_active');
  });

  it('should shows the progress when indicator is active', () => {
    const { container, rerender } = render(<Indicator currentIndex={0} total={3} duration={1000} />);
    expect(container.querySelectorAll('.indicator__item')[0]).toHaveClass('indicator__item_active');
    expect(container.querySelectorAll('.indicator__item .indicator__progress')[0]).toHaveStyle({ animationDuration: `1000ms` });
    rerender(<Indicator currentIndex={1} total={3} duration={1000} />);
    expect(container.querySelectorAll('.indicator__item')[1]).toHaveClass('indicator__item_active');
  });
});
