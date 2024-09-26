import { render, act } from '@testing-library/react';
import SlickDots from '../index';
describe('test Progress component', () => {
  it('renders correctly', () => {
    const { container } = render(<SlickDots length={4} activeIndex={0} />);
    expect(container.firstChild).toMatchSnapshot();
  });

  it('mount correctly', () => {
    expect(() =>
      render(<SlickDots length={4} activeIndex={0} />)
    ).not.toThrow();
  });

  it('test rendering properties', () => {
    const { container } = render(
      <SlickDots length={4} activeIndex={0} duration={3} />
    );

    expect(container.firstChild?.childNodes).toHaveLength(4);

    expect(
      container.querySelectorAll('.carousel-dots .progress-bar')[0]
    ).toHaveStyle({ transition: '3s all', width: '100%' });

    expect(
      container.querySelectorAll('.carousel-dots .progress-bar')[1]
    ).not.toHaveStyle({ transition: '3s all' });

    expect(
      container.querySelectorAll('.carousel-dots .progress-bar')[1]
    ).toHaveStyle({ width: '0%' });
  });

  it('test click event', () => {
    const onClickItem = jest.fn();

    const { container } = render(
      <SlickDots
        length={4}
        activeIndex={0}
        duration={3}
        onClick={onClickItem}
      />
    );
    const dot = container.querySelectorAll('.carousel-dots .progress-bar')[2];

    act(() => {
      dot.dispatchEvent(new MouseEvent('click', { bubbles: true }));
    });

    expect(onClickItem).toBeCalledTimes(1);
    expect(onClickItem).toBeCalledWith(2);

    act(() => {
      for (let i = 0; i < 5; i++) {
        dot.dispatchEvent(new MouseEvent('click', { bubbles: true }));
      }
    });

    expect(onClickItem).toHaveBeenCalledTimes(6);
  });
});
