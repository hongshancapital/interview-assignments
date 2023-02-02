import Carousel from "./index";
import { render, screen } from "@testing-library/react";
import { CAROUSEL_MOCK_DATA } from '../../mock/data'

describe("test carouselView", () => {
  test("test carousel-wrapper", () => {
    render(<Carousel carouselData={[]} />)
    const app = screen.getByTestId("carousel-wrapper")
    expect(app).toBeInTheDocument()
  });

  test("test the carousel-item", () => {
    render(<Carousel carouselData={CAROUSEL_MOCK_DATA} />)

    expect(screen.getByTestId("carousel-wrapper")).toBeInTheDocument()
    expect(screen.getByText("Tablet")).toBeInTheDocument()
    // 多出的1个是为了无限向右自动轮播，在最后一张右边拼接了第一张
    expect(screen.queryAllByTestId("carousel-item")).toHaveLength(3 + 1)
    expect(screen.queryAllByTestId("carousel-tab-item")).toHaveLength(3)

  });

  test("test the spliced item in the carousel-wrapper", () => {
    render(<Carousel carouselData={CAROUSEL_MOCK_DATA} />)

    const items = screen.queryAllByTestId("carousel-item")
    // 最右边拼接的第一张内容是否和默认第一张相同
    expect(items[0].innerHTML).toBe(items[items.length - 1].innerHTML)
  });

});
