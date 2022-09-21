import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";
import { render as TestRender } from "@testing-library/react";
import Carousel, { RefAttributes } from "../index";
import { CarouselInfoProps } from "../carousel-info";
import IphonePng from "../../../assets/iphone.png";
import TabletPng from "../../../assets/tablet.png";
import AirpodsPng from "../../../assets/airpods.png";
import { CarouselItem } from "../carousel-item";
import { CarouselInfo } from "../carousel-info";

interface DataItem extends CarouselInfoProps {
  id: string | number;
  backgroundColor: string;
}

// mock数据
const fakeData: Array<DataItem> = [
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
  describe(`测试mount和unmount`, () => {
    it(`component could be updated and unmounted without errors`, () => {
      const { unmount, rerender } = TestRender(
        <Carousel duration={3000}>
          {fakeData.map((item) => {
            return (
              <CarouselItem key={item.id}>
                <CarouselInfo
                  title={item.title || ""}
                  description={item.description || ""}
                  image={item.image}
                  color={item.color}
                />
              </CarouselItem>
            );
          })}
        </Carousel>
      );
      expect(() => {
        rerender(
          <Carousel duration={3000}>
            {fakeData.map((item) => {
              return (
                <CarouselItem key={item.id}>
                  <CarouselInfo
                    title={item.title || ""}
                    description={item.description || ""}
                    image={item.image}
                    color={item.color}
                  />
                </CarouselItem>
              );
            })}
          </Carousel>
        );
        unmount();
      }).not.toThrow();
    });
  });

  let container: HTMLElement | null = null;
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
    const ref: React.RefObject<RefAttributes> = React.createRef();

    act(() => {
      render(
        <Carousel duration={3000} ref={ref}>
          {fakeData.map((item) => {
            return (
              <CarouselItem key={item.id}>
                <CarouselInfo
                  title={item.title || ""}
                  description={item.description || ""}
                  image={item.image}
                  color={item.color}
                />
              </CarouselItem>
            );
          })}
        </Carousel>,
        container
      );
    });

    const dom: HTMLElement = container?.querySelector(
      ".carousel-wrapper"
    ) as HTMLElement;
    // 初始化位移-0%
    expect(ref.current?.active).toEqual(0);
    expect(dom.style.transform).toEqual("translateX(-0%)");

    // 1s后位移-100%
    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(ref?.current?.active).toEqual(1);
    expect(dom.style.transform).toEqual("translateX(-100%)");

    // 2s后位移-200%
    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(ref.current?.active).toEqual(2);
    expect(dom.style.transform).toEqual("translateX(-200%)");

    // 3s后循环回来，位移-0%
    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(ref.current?.active).toEqual(0);
    expect(dom.style.transform).toEqual("translateX(-0%)");

    // 4s位移-100%
    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(dom?.style.transform).toEqual("translateX(-100%)");
  });

  it("测试渲染Carousel数据", async () => {
    // 使用异步的 act 应用执行成功的 promise
    await act(async () => {
      render(
        <Carousel duration={3000}>
          {fakeData.map((item) => {
            return (
              <CarouselItem key={item.id}>
                <CarouselInfo
                  key={item.id}
                  title={item.title || ""}
                  description={item.description || ""}
                  image={item.image}
                  color={item.color}
                />
              </CarouselItem>
            );
          })}
        </Carousel>,
        container
      );
    });

    for (let i = 0; i < fakeData.length; i++) {
      expect(
        container?.getElementsByTagName("img")[i].getAttribute("src")
      ).toBe(fakeData[i].image);
    }
  });
});
