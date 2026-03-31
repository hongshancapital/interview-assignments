import Carousel from "./index";
import { render, screen } from "@testing-library/react";
// import { useCarousel } from "./useCarousel";

describe("Carousel-Page test", () => {
  test("find page-carousel", () => {
    render(<Carousel sliderList={[]} />);
    const app = screen.getByTestId("page-carousel");
    expect(app).toBeInTheDocument();
  });

  test("find zero carousel item in carousel-wrapper", () => {
    render(<Carousel sliderList={[]} />);
    expect(screen.getByTestId("carousel-container")).toBeInTheDocument();
    expect(screen.queryAllByTestId("carousel-item")).toHaveLength(0);
    expect(screen.queryAllByTestId("carousel-indicator")).toHaveLength(0);
  });

  test("find zero carousel item in carousel-wrapper", () => {
    render(<Carousel sliderList={[
      { id: 1, title: "test1" },
      { id: 2, title: "test2" },
    ]} />);
    expect(screen.getByTestId("carousel-container")).toBeInTheDocument();
    expect(screen.queryAllByTestId("carousel-item")).toHaveLength(2);
    expect(screen.queryAllByTestId("carousel-indicator")).toHaveLength(2);
    expect(screen.getByText("test1")).toBeInTheDocument();
  });
});