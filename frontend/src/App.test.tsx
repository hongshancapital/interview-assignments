import { render, waitFor } from "@testing-library/react";
import React from "react";
import { act } from "react-dom/test-utils";

import App from "./App";
import { CAROUSEL_DURATION_DEFAULT } from "./components";
import { carouselItems } from "./mock";

jest.setTimeout(
  (carouselItems.length + 1) * CAROUSEL_DURATION_DEFAULT * 1000 * 2 + 5000,
);

test("renders without crashing", () => {
  render(<App carouselItems={carouselItems} />);
});

test("renders without crashing when carouselItems is empty ", () => {
  render(<App carouselItems={[]} />);
});

test("renders correct text infos", () => {
  const { getByText } = render(<App carouselItems={carouselItems} />);

  carouselItems.forEach(({ title, subTitle }) => {
    const titles = title?.split("\n").filter(v => !!v.trim()) || [];
    const subTitles = subTitle?.split("\n").filter(v => !!v.trim()) || [];

    titles.concat(subTitles).forEach(text => {
      const span = getByText(text);
      expect(span).toBeInTheDocument();
    });
  });
});

test("carousel content has correct offset at correct time", async () => {
  const { getByTestId } = render(<App carouselItems={carouselItems} />);
  const itemsEl = getByTestId("carousel-items");

  let times = 0;
  const asyncCheck = async () => {
    const current = carouselItems.length ? times % carouselItems.length : 0;
    const translateX = current === 0 ? "0" : `-${100 * current}%`;
    await waitFor(
      () => {
        expect(itemsEl).toHaveStyle({
          transform: `translate3d(${translateX}, 0, 0)`,
        });
      },
      { timeout: CAROUSEL_DURATION_DEFAULT * 1000 },
    );

    times++;
    if (times <= carouselItems.length) {
      await new Promise(resolve =>
        setTimeout(resolve, CAROUSEL_DURATION_DEFAULT * 1000),
      );
      await asyncCheck();
    }
  };

  await act(asyncCheck);
});

test("carousel legends have correct active state at correct time", async () => {
  const { getByTestId } = render(<App carouselItems={carouselItems} />);
  const legendsEl = getByTestId("carousel-legends");

  let times = 0;
  const asyncCheck = async () => {
    await waitFor(
      () => {
        const current = carouselItems.length ? times % carouselItems.length : 0;
        const active = legendsEl.querySelector(
          `.carousel-legend:nth-child(${current + 1})`,
        );
        expect(active).toHaveClass("active");
      },
      { timeout: CAROUSEL_DURATION_DEFAULT * 1000 },
    );

    times++;
    if (times <= carouselItems.length) {
      await new Promise(resolve =>
        setTimeout(resolve, CAROUSEL_DURATION_DEFAULT * 1000),
      );
      await asyncCheck();
    }
  };

  if (carouselItems.length > 0) {
    await act(asyncCheck);
  }
});
