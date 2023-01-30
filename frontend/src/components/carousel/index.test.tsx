import '@testing-library/jest-dom'
import React from 'react';
import { act, render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Carousel from "./index";

let interval = 3000
const testEl = <Carousel interval={interval} style={{ width: 400, }}>
  {
    [
      { title: 'title1', imgURL: 'https://youimg1.c-ctrip.com/target/100i12000000rth5vAE6E.jpg' },
      { title: 'title2', imgURL: 'https://pic3.zhimg.com/v2-2ca5b73dd2c5b58850b9a74a025da13a_r.jpg' },
      { title: 'title3', imgURL: 'https://pic3.zhimg.com/v2-71eaff66299d9d788f2285e62ba0d84e_r.jpg' },
    ].map(el => <div key={el.title} style={{ height: 400, backgroundImage: `url(${el.imgURL})`, backgroundSize: 'cover' }}>
      {el.title}
    </div>)
  }
</Carousel>
describe("Carousel component", () => {
  it("正常渲染到文档", () => {
    const { container } = render(testEl);
    expect(container).toBeInTheDocument();
  });

  it("点击标记时，切换当前帧", async () => {
    render(testEl);
    const element = screen.getAllByRole("mark");
    await waitFor(() => userEvent.click(element[1]))
    expect(element[1]).toHaveClass("mp-carousel-mark-container-item--active");
  });

  const getActiveKey = (element: HTMLElement[]) => {
    for (const key in element) {
      if (element[key].className.includes('mp-carousel-mark-container-item--active')) {
        return +key
      }
    }
    return -1
  }
  it("检测轮播是否正常运行", async () => {
    jest.useFakeTimers()
    render(testEl)
    const element = screen.getAllByRole("mark");
    let endIndex = 0
    await act(async () => {
      jest.advanceTimersByTime(interval)
    })
    endIndex = getActiveKey(element)
    expect(Math.abs(endIndex)).toBe(1);
  });

  it("在当前帧中，重复触发hover事件", async () => {
    jest.useFakeTimers()
    const { container } = render(testEl);
    const element = screen.getAllByRole("mark");
    let startIndex = 0, endIndex = 0

    jest.advanceTimersByTime(10);
    startIndex = getActiveKey(element)
    // 触发次数
    let testCount = 200
    for (let index = 0; index < testCount; index++) {

      await act(async () => {
        userEvent.hover(container,)
        jest.advanceTimersByTime(10);
      })
    }
    expect(Math.abs(endIndex - startIndex)).toBe(0);

    // 移动到下一帧
    await act(async () => {
      jest.advanceTimersByTime(interval);
    })
    endIndex = getActiveKey(element)
    expect(Math.abs(endIndex - startIndex)).toBe(1);
  });
});