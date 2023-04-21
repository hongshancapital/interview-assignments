import Carousel, { ICarouselProps } from "./index";
import { fireEvent, render, renderHook, screen } from "@testing-library/react";
import { FC } from "react";
import { usePage } from "./hooks/page";
import { act } from "react-dom/test-utils";

const MOCK_CAROUSEL_LIST_DATA = [0, 1, 2];

const TestModule: FC<Partial<ICarouselProps>> = (props) => (
  <Carousel carouselIds={MOCK_CAROUSEL_LIST_DATA.map(id => id)} {...props}>
    {MOCK_CAROUSEL_LIST_DATA.map(num => <div key={num}>{num}</div>)}
  </Carousel>
)

describe("Carousel test", () => {
  test("Find the carousel-item", () => {
    render(<TestModule />)
    const app = screen.getByTestId("carousel-list")
    expect(app).toBeInTheDocument()
    expect(screen.getByText("1")).toBeInTheDocument()
  });

  test("Test animation", () => {
    render(<TestModule />)
    const indicator = screen.getAllByTestId('carousel-tab-item')[1]

    fireEvent.click(indicator)
    jest.useFakeTimers()
    jest.advanceTimersByTime(1000)
    expect(screen.getByTestId('carousel-list')?.getAttribute('style')?.replace(/\s/g, ''))
      .toEqual(expect.stringContaining("translateX(-100%)"))
  })

  // 新增分页hooks的单元测试
  test("Test custom hooks usePage", () => {
    const { result } = renderHook(() => usePage(3));
    const nextPage = result.current.nextPage;
    const toPage = result.current.toPage;
    expect(result.current.currentPage).toBe(0);

    act(() => nextPage());
    expect(result.current.currentPage).toBe(1);

    act(() => toPage(2));
    expect(result.current.currentPage).toBe(2);

    act(() => toPage(10));
    expect(result.current.currentPage).toBe(0);
  })

});
