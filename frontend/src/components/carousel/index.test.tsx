import { render } from "@testing-library/react";
import Carousel from "./index";

describe("Carousel", () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  it("carousel component is success rerender and unmount", () => {
    const { rerender, unmount } = render(
      <Carousel>
        <div>图片一占位</div>
        <div>图片二占位</div>
        <div>图片三占位</div>
      </Carousel>
    );
    expect(() => {
      rerender(
        <Carousel>
          <div>图片一占位</div>
          <div>图片二占位</div>
          <div>图片三占位</div>
        </Carousel>
      );
      unmount();
    }).not.toThrow();
  });
});
