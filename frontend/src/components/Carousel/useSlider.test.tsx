import { renderHook, act } from "@testing-library/react-hooks";
import useSlider from "./useSlider";

jest.setTimeout(10000);
test("should increment counter", async () => {
  const { result } = renderHook(() => useSlider(2, 1000));
  await act(() => new Promise((rs) => setTimeout(rs, 1000)));
  expect(result.current[0]).toBe(0);
  await act(() => new Promise((rs) => setTimeout(rs, 1200)));
  expect(result.current[0]).toBe(1);
});
