import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";

import { Carousel } from "./Carousel";
import { act } from "react-dom/test-utils";

test("Carousel with only one child", async () => {
  jest.useFakeTimers();

  render(
    <Carousel duration={2000} style={{ height: 100, width: 100 }}>
      <div>hello</div>
    </Carousel>
  );

  const div = await screen.findByText(/hello/);
  expect(div.parentElement?.parentElement?.style.transform).toBe(
    "translateX(000%)"
  );

  act(() => {
    jest.advanceTimersByTime(2100);
  });
  expect(div.parentElement?.parentElement?.style.transform).toBe(
    "translateX(000%)"
  );
});

test("Carousel with 3 child", async () => {
  jest.useFakeTimers();

  render(
    <Carousel duration={2000} style={{ height: 100, width: 100 }}>
      <div>hello</div>
      <div>world</div>
      <div>xxx</div>
    </Carousel>
  );

  const div_hello = await screen.findByText(/hello/);

  expect(div_hello.parentElement?.parentElement?.style.transform).toBe(
    "translateX(000%)"
  );

  act(() => {
    jest.advanceTimersByTime(2100);
  });
  expect(div_hello.parentElement?.parentElement?.style.transform).toBe(
    "translateX(-100%)"
  );

  act(() => {
    jest.advanceTimersByTime(2100);
  });
  expect(div_hello.parentElement?.parentElement?.style.transform).toBe(
    "translateX(-200%)"
  );

  act(() => {
    jest.advanceTimersByTime(2100);
  });
  expect(div_hello.parentElement?.parentElement?.style.transform).toBe(
    "translateX(000%)"
  );
});
