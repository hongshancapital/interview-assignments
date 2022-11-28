import { render, screen, cleanup } from '@testing-library/react';
import { Carousel } from '../carousel';
/**
 * 因为使用overflow:hidden, css transform来实现，测试比较困难
 * 只好采用判断transform具体值的方式来判断
 */
test('Base Carousel，渲染不同active值得Carousel，判断当前显示位置', () => {
  {
    render(<Carousel data-testid='carousel' active={0}>
      <div data-testid='1' />
      <div data-testid='2' />
      <div data-testid='3' />
    </Carousel>);
    const carousel = screen.getByTestId('carousel')
    expect(carousel.children[0]?.getAttribute('style')).toEqual('transform: translateX(-0%);')
    cleanup()
  }
  {
    render(<Carousel data-testid='carousel' active={1}>
      <div data-testid='1' />
      <div data-testid='2' />
      <div data-testid='3' />
    </Carousel>);
    const carousel = screen.getByTestId('carousel')
    expect(carousel.children[0]?.getAttribute('style')).toEqual('transform: translateX(-100%);')
    cleanup()
  }
  {
    render(<Carousel data-testid='carousel' active={2}>
      <div data-testid='1' />
      <div data-testid='2' />
      <div data-testid='3' />
    </Carousel>);
    const carousel = screen.getByTestId('carousel')
    expect(carousel.children[0]?.getAttribute('style')).toEqual('transform: translateX(-200%);')
  }
});

