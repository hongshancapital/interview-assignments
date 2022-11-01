import { act, render } from "@testing-library/react";
import * as React from "react";
import "../../App.css";
import Carousel from "./Carousel";
import Paper from "./Paper";
import Provider from "./Provider";

jest.mock("react", () => {
  const originReact = jest.requireActual("react");
  const useRef = jest.fn();
  return {
    ...originReact,
    useRef,
  };
});

describe("Carousel 组件测试", function () {
  beforeEach(function () {
    jest.useFakeTimers();
    (React.useRef as any).mockReturnValue({
      get current() {
        return { clientWidth: 400 };
      },
      set current(_v) {},
    });
  });
  afterEach(function () {
    jest.clearAllMocks();
  });

  it("轮播图自动播放但不循环", async function () {
    await act(async () => {
      const { getByTestId } = render(
        <Provider auto>
          <Carousel>
            <Paper>
              <div>paper 1</div>
            </Paper>
            <Paper>
              <div>paper 2</div>
            </Paper>
            <Paper>
              <div>paper 3</div>
            </Paper>
          </Carousel>
        </Provider>
      );

      const carouselContent = getByTestId("carousel-content");

      expect(carouselContent.style.transform).toBe("translate3d(0px, 0, 0)");

      jest.advanceTimersByTime(3001);

      expect(carouselContent.style.transform).toBe("translate3d(-400px, 0, 0)");

      jest.advanceTimersByTime(6001);

      expect(carouselContent.style.transform).toBe("translate3d(-800px, 0, 0)");

      jest.advanceTimersByTime(9500);

      // 因为是不循环的，到第三张 paper 就停下了
      expect(carouselContent.style.transform).toBe("translate3d(-800px, 0, 0)");
    });
  });

  it("轮播图自动播放且循环", async function () {
    await act(async () => {
      const { getByTestId } = render(
        <Provider auto cycle>
          <Carousel>
            <Paper>
              <div>paper 1</div>
            </Paper>
            <Paper>
              <div>paper 2</div>
            </Paper>
            <Paper>
              <div>paper 3</div>
            </Paper>
          </Carousel>
        </Provider>
      );

      const carouselContent = getByTestId("carousel-content");

      expect(carouselContent.style.transform).toBe("translate3d(0px, 0, 0)");

      jest.advanceTimersByTime(3001);

      expect(carouselContent.style.transform).toBe("translate3d(-400px, 0, 0)");

      jest.advanceTimersByTime(6001);

      expect(carouselContent.style.transform).toBe("translate3d(-800px, 0, 0)");

      jest.advanceTimersByTime(9500);

      // 因为是循环的，一次展示结束返回第一张
      expect(carouselContent.style.transform).toBe("translate3d(0px, 0, 0)");
    });
  });
});
