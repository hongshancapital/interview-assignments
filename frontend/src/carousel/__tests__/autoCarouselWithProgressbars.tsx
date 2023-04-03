import { render, screen, cleanup, act, fireEvent } from '@testing-library/react';
import { AutoCarouselWithProgressbars } from '../autoCarouselWithProgressbars';

jest.useFakeTimers();

/**
 * 因为使用overflow:hidden, css transform来实现，测试visibility比较困难
 * 只好采用判断transform具体值的方式来判断
 */
test('Carousel, 渲染AutoCarouselWithProgressbars， 之后根据时间判断当前切换的位置', () => {
  render(
    <AutoCarouselWithProgressbars timeout={3000} data-testid='carousel' progressDataTestId='progress'>
      <div data-testid='1' />
      <div data-testid='2' />
      <div data-testid='3' />
    </AutoCarouselWithProgressbars>);
  const carousel = screen.getByTestId('carousel')
  const slider = carousel.children[0] 
  expect(slider.getAttribute('style')).toEqual('transform: translateX(-0%);')
  act(() => {
    jest.advanceTimersByTime(3100)
  })
  expect(slider.getAttribute('style')).toEqual('transform: translateX(-100%);')
  act(() => {
    jest.advanceTimersByTime(3100)
  })
  expect(slider.getAttribute('style')).toEqual('transform: translateX(-200%);')
  act(() => {
    jest.advanceTimersByTime(3100)
  })
  expect(slider.getAttribute('style')).toEqual('transform: translateX(-0%);')

  const bars = screen.getByTestId('progress')
  fireEvent.click(bars.children[1])
  expect(slider.getAttribute('style')).toEqual('transform: translateX(-100%);')
});
