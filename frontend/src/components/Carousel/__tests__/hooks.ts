import { act, renderHook } from "@testing-library/react-hooks";
import { useSlide } from "../hooks";
import TEST_SLIDES from "./data/slides.json";

test("should render ", async () => {
  const { result } = renderHook(() => useSlide(TEST_SLIDES, 2000));

  expect(result.current[0]).toBe(0);
  expect(result.current[1]).toBe(false);

  await act(() => new Promise((r) => setTimeout(r, 2100)));

  expect(result.current[0]).toBe(1);
});
