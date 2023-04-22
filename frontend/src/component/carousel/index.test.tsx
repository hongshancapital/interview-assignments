import { render, renderHook, act, screen } from "@testing-library/react";
import Carousel from './carousel';
import { usePage } from '../../hooks';

describe("Carousel-component usePage test", () => {
  test("usePage test", () => {
    const { result } = renderHook(() => usePage(1, 3));
    expect(result.current.pageIndex).toBe(1);
    expect(result.current.length).toBe(2);

    const prevPage = result.current.prevPage;
    const nextPage = result.current.nextPage;
    const jumpPage = result.current.jumpPage;

    act(() => {
      prevPage();
    });
    expect(result.current.pageIndex).toBe(0);

    act(() => {
      nextPage();
    });
    expect(result.current.pageIndex).toBe(2);

    act(() => {
      jumpPage(0);
    });
    expect(result.current.pageIndex).toBe(0);

    act(() => {
      jumpPage(1);
    });
    expect(result.current.pageIndex).toBe(1);

    act(() => {
      jumpPage(999);
    });
    expect(result.current.pageIndex).toBe(0);
  })

  test("find carousel-wrapper", () => {
    render(<Carousel />);
    const app = screen.getByTestId("carousel-wrapper");
    expect(app).toBeInTheDocument();
  });
});