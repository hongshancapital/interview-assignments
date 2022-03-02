import React from "react";
import { render, } from "@testing-library/react";
import { IphoneImg, TabletImg, AirpodsImg } from "../../Demo";
import Carousel, { getDotWidth } from "../../Carousel";
import { Container } from "react-dom";

const DEFAULT_CONTAINER_WIDTH = 500;

type StyleObject = {
  [index: string]: any;
};

// 'width: 120px; left: 300px' => { width: '120', left: '300' }
function parseInlineStringStyle(stringString: string) {
  return stringString
    .split(";")
    .filter(Boolean)
    .reduce((preValue, curValue) => {
      const [key, value] = curValue.split(":");
      return {
        ...preValue,
        [key.trim()]: Number(value.replace(/[^(\d..\-)+]/g, "")),
      };
    }, {});
}

function getDotsStyle(container: Container) {
  const dotsWidth: any[] = [];
  (container.querySelectorAll(".dot > span") || []).forEach((node) => {
    const nodeStrStyles = node?.getAttribute("style") || "";
    dotsWidth.push(parseInlineStringStyle(nodeStrStyles));
  });
  return dotsWidth;
}

function getScrollChildrenStyles(container: Container): Record<string, any> {
  const scrollChildrenStyle = container
    .querySelector(".carousel-inner")
    ?.getAttribute("style");

  return scrollChildrenStyle ? parseInlineStringStyle(scrollChildrenStyle) : {};
}

const spyClientWidth = (width: number = DEFAULT_CONTAINER_WIDTH) => {
  Object.defineProperty(HTMLElement.prototype, "clientWidth", {
    writable: true,
    value: width,
  });
};

const CommonTestDemo = () => (
  <Carousel>
    <IphoneImg />
    <AirpodsImg />
    <TabletImg />
  </Carousel>
);

describe("Carousel", () => {
  beforeEach(() => {
    spyClientWidth();
  });

  afterAll(() => {
    spyClientWidth(0);
  });

  test("Should mount and unmount without errors", () => {
    const mountAndUnmount = ({ element }: { element: React.ReactElement }) => {
      expect(() => {
        const view = render(element);
        view.rerender(element);
        view.unmount();
      }).not.toThrow();
    };

    mountAndUnmount({ element: <CommonTestDemo /> });
  });

  test("Should render correctlly", async () => {
    const { container, rerender, unmount } = render(<CommonTestDemo />);

    expect((getScrollChildrenStyles(container) as StyleObject)?.width).toBe(
      3 * DEFAULT_CONTAINER_WIDTH
    );

    expect(container.querySelector(".carousel-dots")?.childElementCount).toBe(
      3
    );

    expect(container.querySelector(".carousel-inner")?.childElementCount).toBe(
      3
    );

    rerender(<CommonTestDemo />);

    expect(
      container.querySelectorAll(".carousel-child")?.forEach((ele) => {
        const styleObj = parseInlineStringStyle(
          ele.getAttribute("style") || ""
        ) as StyleObject;
        expect(styleObj.width).toBe(DEFAULT_CONTAINER_WIDTH);
      })
    );

    expect(container).toMatchSnapshot();

    unmount();
  });

  test("Should Be A Carousel !!!", async () => {
    const rafSpy = jest.spyOn(window, "requestAnimationFrame");

    function mockRafNTimes(count: number) {
      while (count > 0) {
        // @ts-ignore
        rafSpy.mockImplementationOnce((cb) => cb());
        count--;
      }
    }

    const dotWidth = getDotWidth({
      unitWidth: DEFAULT_CONTAINER_WIDTH,
      dotCount: 3,
    });

    // 3000 为默认cycleTime
    // 16.6 浏览器每帧时间
    const dotIncreaseStep = dotWidth / (3000 / 16.6);
    const perDotCount = Math.ceil(dotWidth / dotIncreaseStep);

    /**
     * @description 以轮播一张图的时间为一次cycle
     * @param cycleCount 从挂载到开始计算，经过的cycle次数
     * @param expectDistance 期望的轮播画布位置（表示当前轮播到了第几张）
     * @param inProgressingDotsIndex 正在展示动画的dot index
     */
    function expectCarouselDistance(
      cycleCount: number,
      expectDistance: number,
      inProgressingDotsIndex?: number,
    ) {
      mockRafNTimes(cycleCount * perDotCount);
      const { container, unmount } = render(<CommonTestDemo />);
      expect(getScrollChildrenStyles(container).left).toBe(expectDistance);
      if ( inProgressingDotsIndex !== undefined ) {
        const curDotWidth = (getDotsStyle(container)?.[inProgressingDotsIndex] as StyleObject)?.width;
        expect(curDotWidth).toBeGreaterThan(0);
        expect(curDotWidth).toBeLessThan(dotWidth);
      }
      expect(container).toMatchSnapshot();
      unmount();
    }

    // 第一张图播到一半
    expectCarouselDistance(0.5, 0, 0);

    // 第一张图播完，到达第二张
    expectCarouselDistance(1, -500);

    // 第二张图播到一半
    expectCarouselDistance(1.8, -500, 1);

    // 第二张图播完，到达第一张
    expectCarouselDistance(2, -1000);

    // 第三张图播到一半
    expectCarouselDistance(2.5, -1000, 2);

    // 第三张图播完，重回第一张
    expectCarouselDistance(3, 0);

    // 第二轮的第二张
    expectCarouselDistance(4, -500);
  });
});
