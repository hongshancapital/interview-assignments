// TODO more tests

import React from "react";
import Carousel, { ItemProps, SliderItem } from "./Carousel";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";

describe("SliderItem", () => {
  describe("Render cover", () => {
    it("Render SliderItem ", () => {
      const mock_data: ItemProps = {
        titles: ["Test change dom", "Test create thing"],
        src: "This is src.",
      };
      render(<SliderItem {...mock_data} />);
      expect(screen.getByText("Test change dom")).toBeInTheDocument();
    });

    it("Render Carousel", () => {
      render(
        <Carousel>
          <div>
            <label>test</label>
          </div>
        </Carousel>
      );

      expect(screen.getByText("test")).toBeInTheDocument();
    });

    it("Carousel inner reactive", () => {
      render(<Carousel />);
      //...
    });
  });
});
