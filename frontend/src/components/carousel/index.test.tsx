import Carousel, { ICarouselProps } from "./index";
import { fireEvent, render, screen } from "@testing-library/react";
import { FC } from "react";

const MOCK_CAROUSEL_LIST_DATA = [0, 1, 2];

const TestModule: FC<Partial<ICarouselProps>> = (props) => (
  <Carousel carouselIds={MOCK_CAROUSEL_LIST_DATA.map(id => id)} {...props}>
    {MOCK_CAROUSEL_LIST_DATA.map(num => <div key={num}>{num}</div>)}
  </Carousel>
)

describe("Carousel-Page test", () => {
  test("find the carousel-item", () => {
    render(<TestModule />)
    const app = screen.getByTestId("carousel-list")
    expect(app).toBeInTheDocument()
    expect(screen.getByText("1")).toBeInTheDocument()
  });

  test("test animation", () => {
    render(<TestModule />)
    const indicator = screen.getAllByTestId('carousel-tab-item')[1]

    fireEvent.click(indicator)
    jest.useFakeTimers()
    jest.advanceTimersByTime(1000)
    expect(screen.getByTestId('carousel-list')?.getAttribute('style')?.replace(/\s/g, ''))
      .toEqual(expect.stringContaining("translateX(-100%)"))
  })

});
