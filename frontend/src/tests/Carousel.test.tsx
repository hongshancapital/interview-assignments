import React from "react";
import { render, act } from "@testing-library/react";
import Carousel from "../components/Carousel";
import { SLIDES_DATA } from "../common/constants";

describe('Carousel Component', () => {
  const TIME = 2000;

  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  test('测试正常渲染slide', () => {
    const { queryAllByTestId, unmount } = render(<Carousel slides={SLIDES_DATA} delay={TIME} />);
    
    expect(queryAllByTestId('slide')).toBeTruthy();
    unmount();
  });

  test('测试slides可以自定义style', () => {
    const { container, unmount } = render(<Carousel slides={SLIDES_DATA} delay={TIME} style={{width: 100}} />);
    
    expect(container.firstChild).toHaveStyle('width: 100px;');
    unmount();
  });

  test('测试是否可以自定义nav组件', () => {
    const NavNode = (props: { key?: string }) => (<div {...props}>Bottom Nav</div>);
    const { queryAllByText, unmount } = render(<Carousel slides={SLIDES_DATA} delay={TIME} renderNavItem={({ key }) => <NavNode key={key} />}  />);
    
    expect(queryAllByText('Bottom Nav')).toBeTruthy();
    unmount();
  });

  test('测试slide是否正常滚动', () => {
    const { getByTestId } = render(<Carousel slides={SLIDES_DATA} delay={TIME} />);

    const styleWidth = `width: ${SLIDES_DATA.length * 100}%;`;
    
    let styleTransform = (index: number) => (`transform: translateX(${-100/SLIDES_DATA.length * index}%);`);

    const node = getByTestId('slides-item');
    expect(node).toHaveAttribute('style', `${styleWidth} ${styleTransform(0)}`);
    
    // 等待2秒
    act(() => {
      jest.advanceTimersByTime(TIME)
    });
    expect(node).toHaveAttribute('style', `${styleWidth} ${styleTransform(1)}`);
  
    // 再等待2秒
    act(() => {
      jest.advanceTimersByTime(TIME)
    });
    expect(node).toHaveAttribute('style', `${styleWidth} ${styleTransform(2)}`);

    // 再再等待2秒
    act(() => {
      jest.advanceTimersByTime(TIME)
    });
    expect(node).toHaveAttribute('style', `${styleWidth} ${styleTransform(0)}`);

    // 再再再等待2秒
    act(() => {
      jest.advanceTimersByTime(TIME)
    });
    expect(node).toHaveAttribute('style', `${styleWidth} ${styleTransform(1)}`);
  });

});