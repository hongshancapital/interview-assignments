/**
 * @description Carousel组件的测试用例
 * @author 郭益奥
 */
import { render, fireEvent } from "@testing-library/react";
import { resetWarned } from "rc-util/lib/warning";
import Carousel from "..";

/**
 * 验证组件渲染是否报错
 * @param Component react组件
 */
function _mountTest(Component: JSX.Element) {
  describe(`mount and unmount`, () => {
    it(`component could be updated and unmounted without errors`, () => {
      const { unmount, rerender } = render(Component);
      expect(() => {
        rerender(Component);
        unmount();
      }).not.toThrow();
    });
  });
}
describe("Button", () => {
  _mountTest(<Carousel></Carousel>);
  _mountTest(<Carousel autoplay></Carousel>);
  _mountTest(<Carousel showPagination></Carousel>);
  _mountTest(<Carousel waitTime={3e3}></Carousel>);
  _mountTest(<Carousel.Slider></Carousel.Slider>);

  it("renders correctly", () => {
    const { container } = render(
      <Carousel>
        <Carousel.Slider>1</Carousel.Slider>
      </Carousel>
    );
    expect(container.firstChild).toMatchSnapshot();
  });

  it("warns if autoplay is wrong", () => {
    resetWarned();
    const mockWarn = jest.spyOn(console, "error").mockImplementation(() => {});
    const autoplay = "yes";
    // @ts-expect-error: Type '"yes"' is not assignable to type 'autoplay'.ts(2322)
    render(<Carousel autoplay={autoplay}></Carousel>);
    expect(mockWarn).toHaveBeenCalledWith(
      "Warning: [Carousel] Invalid prop `autoplay`"
    );

    mockWarn.mockRestore();
  });
  it("warns if waitTime is wrong", () => {
    resetWarned();
    const mockWarn = jest.spyOn(console, "error").mockImplementation(() => {});
    const waitTime = "3s";
    // @ts-expect-error: Type '"yes"' is not assignable to type 'autoplay'.ts(2322)
    render(<Carousel waitTime={waitTime}></Carousel>);
    expect(mockWarn).toHaveBeenCalledWith(
      "Warning: [Carousel] Invalid prop `waitTime`"
    );

    mockWarn.mockRestore();
  });
  it("warns if showPagination is wrong", () => {
    resetWarned();
    const mockWarn = jest.spyOn(console, "error").mockImplementation(() => {});
    const showPagination = "yes";
    // @ts-expect-error: Type '"yes"' is not assignable to type 'autoplay'.ts(2322)
    render(<Carousel showPagination={showPagination}></Carousel>);
    expect(mockWarn).toHaveBeenCalledWith(
      "Warning: [Carousel] Invalid prop `showPagination`"
    );

    mockWarn.mockRestore();
  });
  //  点击切换之后，autoplay类名应消失
  it("click to change to non autoplay classname", () => {
    const wrapper = render(
      <Carousel showPagination autoplay>
        <Carousel.Slider>1</Carousel.Slider>
        <Carousel.Slider>2</Carousel.Slider>
        <Carousel.Slider>3</Carousel.Slider>
      </Carousel>
    );
    fireEvent.click(wrapper.container.querySelector(".pagination-bullet")!);
    expect(wrapper.container.querySelector(".pagination")).not.toHaveClass(
      "autoplay"
    );
  });
  //   只传一个slider，不显示pagination
  it("hide pagination when only one slider", () => {
    const wrapper = render(
      <Carousel showPagination autoplay>
        <Carousel.Slider>1</Carousel.Slider>
      </Carousel>
    );
    expect(
      wrapper.container.querySelector(".pagination")!.firstChild
    ).toBeFalsy();
  });
});
