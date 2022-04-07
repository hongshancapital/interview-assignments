import React from "react";
import { render } from "@testing-library/react";
import demoConfig from "./demoConfig";
import Carousel from "./index";

describe("Carousel test", () => {
  it("render every item's title and desc", () => {
    const { getByText } = render(<Carousel {...demoConfig} />);

    demoConfig.items.forEach((i) => {
      i.title.forEach((c) => {
        const title = getByText(c);
        expect(title).toBeInTheDocument();
      });
      i.desc?.forEach((c) => {
        const desc = getByText(c);
        expect(desc).toBeInTheDocument();
      });
    });
  });

  it("render progress component", () => {
    const { container } = render(<Carousel {...demoConfig} />);
    const progressElem = container.querySelector(".carousel-progress");
    expect(progressElem).toBeInTheDocument();

    const itemElem = progressElem?.querySelector(".item");
    expect(itemElem).toBeInTheDocument();
  });
});
