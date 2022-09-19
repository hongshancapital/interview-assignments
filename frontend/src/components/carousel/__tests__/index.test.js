import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";
import { render as TestRender } from "@testing-library/react";
import Carousel from "../index";
import IphonePng from "../../../assets/iphone.png";
import TabletPng from "../../../assets/tablet.png";
import AirpodsPng from "../../../assets/airpods.png";

// mock数据
const fakeData = [
  {
    id: "1",
    title: "xPhone",
    description: "Lots to love.Less to spend.\nStarting at $399",
    image: IphonePng,
    backgroundColor: "rgba(14,14,14)",
    color: "#fff",
  },
  {
    id: "2",
    title: "Tablet",
    description: "Just the right amount of everything.",
    image: TabletPng,
    backgroundColor: "#fff",
    color: "#000",
  },
  {
    id: "3",
    title: "Buy a Tablet or xPhone for college.\n",
    description: "",
    image: AirpodsPng,
    backgroundColor: "#eee",
    color: "#000",
  },
];

describe("Carousel", () => {
  // 测试mount和unmount
  describe(`mount and unmount`, () => {
    it(`component could be updated and unmounted without errors`, () => {
      const { unmount, rerender } = TestRender(<Carousel />);
      expect(() => {
        rerender(<Carousel />);
        unmount();
      }).not.toThrow();
    });
  });

  let container = null;
  beforeEach(() => {
    // 创建一个 DOM 元素作为渲染目标
    container = document.createElement("div");
    document.body.appendChild(container);
    jest.useFakeTimers();
  });

  afterEach(() => {
    if (container) {
      // 退出时进行清理
      unmountComponentAtNode(container);
      container.remove();
      container = null;
      jest.useRealTimers();
    }
  });

  it("测试定时器与react state数据准确性以及ui css准确性", async () => {
    const ref = React.createRef(null);
    const callback = jest.fn();
    act(() => {
      render(
        <Carousel ref={ref} onChange={callback} duration={1000} />,
        container
      );
    });

    // 初始化位移-0%
    expect(ref.current.slickNum).toEqual(0);
    expect(
      container.querySelector(".carousel-wrapper").style.transform
    ).toEqual("translateX(-0%)");

    // 1s后位移-100%
    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(ref.current.slickNum).toEqual(1);
    expect(
      container.querySelector(".carousel-wrapper").style.transform
    ).toEqual("translateX(-100%)");
    expect(callback).toBeCalled();

    // 2s后位移-200%
    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(ref.current.slickNum).toEqual(2);
    expect(
      container.querySelector(".carousel-wrapper").style.transform
    ).toEqual("translateX(-200%)");

    // 3s后循环回来，位移-0%
    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(ref.current.slickNum).toEqual(0);
    expect(
      container.querySelector(".carousel-wrapper").style.transform
    ).toEqual("translateX(-0%)");

    // 4s位移-100%
    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(
      container.querySelector(".carousel-wrapper").style.transform
    ).toEqual("translateX(-100%)");
  });

  it("渲染Carousel数据", async () => {
    // 使用异步的 act 应用执行成功的 promise
    await act(async () => {
      render(<Carousel data={fakeData} duration={1000} />, container);
    });

    for (let i = 0; i < fakeData.length; i++) {
      expect(
        container.getElementsByClassName("carousel-item_title")[i].textContent
      ).toBe(fakeData[i].title);

      expect(
        container.getElementsByClassName("carousel-item_description")[i]
          .textContent
      ).toBe(fakeData[i].description);

      expect(container.getElementsByTagName("img")[i].getAttribute("src")).toBe(
        fakeData[i].image
      );
    }
  });
});
