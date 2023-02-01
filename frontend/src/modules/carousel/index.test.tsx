import Carousel from "./index";
import { fireEvent, render, screen } from "@testing-library/react";
import { mockData } from '../../mock/data'

describe("Carousel-Page test", () => {
  test("find carousel-wrapper", () => {
    render(<Carousel carouselData={[]} />)
    const app = screen.getByTestId("carousel-wrapper")
    expect(app).toBeInTheDocument()
  });

  test("find the carousel-item in the carousel-wrapper", () => {
    render(<Carousel carouselData={mockData} />)

    expect(screen.getByTestId("carousel-wrapper")).toBeInTheDocument()
    expect(screen.getByText("Tablet")).toBeInTheDocument()
    // 多出的1个是为了无限向右自动轮播，在最后一张右边拼接了第一张
    expect(screen.queryAllByTestId("carousel-item")).toHaveLength(3 + 1)
    expect(screen.queryAllByTestId("carousel-tab-item")).toHaveLength(3)

  });

  test("find the spliced item in the carousel-wrapper", () => {
    render(<Carousel carouselData={mockData} />)

    const items = screen.queryAllByTestId("carousel-item")
    // 最右边拼接的第一张内容是否和默认第一张相同
    expect(items[0].innerHTML).toBe(items[items.length - 1].innerHTML)
  });

});
