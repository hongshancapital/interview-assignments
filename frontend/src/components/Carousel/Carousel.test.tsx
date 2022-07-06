import React, { FC, useState } from "react";
import {
  render,
  screen,
  act,
  waitFor,
  fireEvent,
} from "@testing-library/react";
import Carousel from "./index";

test("carousel autoplay", async () => {
  act(() => {
    render(
      <Carousel interval={1000}>
        <div data-testid="first">1</div>
        <div data-testid="second">2</div>
        <div data-testid="third">3</div>
      </Carousel>
    );
  });
  await waitFor(
    () =>
      expect(screen.getByTestId("first").parentElement).toHaveClass(
        "carousel-slides__item--active"
      ),
    { timeout: 200 }
  );
  await waitFor(() =>
    expect(screen.getByTestId("first").parentElement).toHaveClass(
      "carousel-slides__item--active"
    )
  );
  await waitFor(() =>
    expect(screen.getByTestId("second").parentElement).toHaveClass(
      "carousel-slides__item--active"
    )
  );

  await waitFor(() =>
    expect(screen.getByTestId("third").parentElement).toHaveClass(
      "carousel-slides__item--active"
    )
  );
  await waitFor(() =>
    expect(screen.getByTestId("first").parentElement).toHaveClass(
      "carousel-slides__item--active"
    )
  );
});

test("dynamic slides", async () => {
  const App: FC = () => {
    const [show, setShow] = useState(true);
    const onToogle = () => setShow(!show);
    return (
      <div>
        <button onClick={onToogle}>toogle</button>
        <Carousel interval={1000}>
          <div data-testid="first">1</div>
          <div data-testid="second">2</div>
          <div data-testid="third">3</div>
          {show && <div data-testid="fourth">4</div>}
        </Carousel>
      </div>
    );
  };
  act(() => {
    render(<App />);
  });
  const container = document.querySelector(".carousel-slides");
  const dotsContainer = document.querySelector(".carousel-dots");
  expect(container?.children.length).toBe(4);
  expect(dotsContainer?.children.length).toBe(4);
  const btn = screen.getByRole("button");
  fireEvent.click(btn);
  expect(container?.children.length).toBe(3);
  expect(dotsContainer?.children.length).toBe(3);
  fireEvent.click(btn);
  expect(container?.children.length).toBe(4);
  expect(dotsContainer?.children.length).toBe(4);
});

test("hide carousel dots", async () => {
  act(() => {
    render(
      <Carousel interval={1000}>
        <div data-testid="first">1</div>
      </Carousel>
    );
  });
  const dotsContainer = document.querySelector(".carousel-dots");
  expect(dotsContainer).toBeNull();
});
