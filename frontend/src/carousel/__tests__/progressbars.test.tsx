import { render, screen, act, fireEvent } from '@testing-library/react';
import { Progressbars } from '../progressbars';

jest.useFakeTimers();

test('ProgressBars，渲染Progressbars，之后根据时间判断当前起效果的位置', () => {
  const onTimeout = jest.fn()
  const onClick = jest.fn()
  render(<Progressbars data-testid='progressbars' length={3} active={2} timeout={3000} onTimeout={onTimeout} onClick={onClick} />);
  const bars = screen.getByTestId('progressbars')
  expect(bars.childElementCount).toBe(3)
  // 根据是否有child判断是否active
  expect(bars.children[0].childElementCount).toBe(0)
  expect(bars.children[1].childElementCount).toBe(0)
  expect(bars.children[2].childElementCount).toBe(1)
  act(() => {
    jest.advanceTimersByTime(3100)
  })
  expect(onTimeout).toHaveBeenCalledTimes(1)
  fireEvent.click(bars.children[0])
  expect(onClick).toBeCalledWith(0)
  fireEvent.click(bars.children[1])
  expect(onClick).toBeCalledWith(1)
  fireEvent.click(bars.children[2])
  expect(onClick).toBeCalledWith(2)
});
