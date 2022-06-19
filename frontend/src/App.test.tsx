import React from "react";
import { render } from "@testing-library/react";
import App, { slides } from "./App";

test("app slide rendered", () => {
  const { getByTestId } = render(<App />);

  slides.forEach((slide) => {
    const slideElm = getByTestId(slide.testId);
    expect(slideElm).toBeVisible();
    expect(slideElm.style.backgroundColor).toBe(slide.backgroundColor);
    expect(slideElm.style.color).toBe(slide.color);

    expect(slideElm.textContent).toContain(slide.title);
    expect(slideElm.textContent).toContain(slide.text);
  });
});
