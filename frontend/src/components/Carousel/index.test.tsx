import React from "react";
import { render } from "@testing-library/react";
import defaultConfig from "./defaultConfig";
import Carousel from "./index";

describe("Carousel", () => {
  it("check every item's title and desc", () => {
    const { getByText } = render(<Carousel {...defaultConfig} />);

    defaultConfig.items.forEach((item) => {
      item.title.forEach((c) => {
        const title = getByText(c);
        expect(title).toBeInTheDocument();
      });
      item.desc?.forEach((c) => {
        const desc = getByText(c);
        expect(desc).toBeInTheDocument();
      });
    });
  });

  it("check progress component", () => {
    const { container } = render(<Carousel {...defaultConfig} />);
    const progressElem = container.querySelector(".carousel-progress");
    expect(progressElem).toBeInTheDocument();

    const itemElem = progressElem?.querySelector(".item");
    expect(itemElem).toBeInTheDocument();
  });
});
