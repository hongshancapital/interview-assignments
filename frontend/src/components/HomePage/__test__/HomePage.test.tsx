import React from "react";
import { render } from "@testing-library/react";
import HomePage, { slides } from "../";

test("home page slide rendered", () => {
  const { getByTestId } = render(<HomePage />);

  slides.forEach((slide) => {
    const slideElm = getByTestId(slide.testId);
    expect(slideElm).toBeVisible();
    expect(slideElm.style.backgroundColor).toBe(slide.backgroundColor);
    expect(slideElm.style.color).toBe(slide.color);

    expect(slideElm.textContent).toContain(slide.title);
    expect(slideElm.textContent).toContain(slide.text);
  });
});
