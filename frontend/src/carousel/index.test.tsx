import React from "react";
import { render, act } from "@testing-library/react";

import { Carousel } from "./index";
import { ICarouselProps } from "./type";
import iPhone from "../assets/iphone.png";
import tablet from "../assets/tablet.png";
import airPods from "../assets/airpods.png";

describe("<Carousel />", () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });
  test("should render the carousel", async () => {
    const data: ICarouselProps["data"] = [
      {
        title: ["testTitle1"],
        bgColor: "rgba(17,17,17,255)",
        description: ["testDescription1"],
        textColor: "#FAFAFA",
        image: iPhone,
      },
      {
        title: ["testTitle2"],
        description: ["testDescription2"],
        image: tablet,
        bgColor: "#FAFAFA",
        imageScaleRate: 2,
      },
      {
        title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
        image: airPods,
        bgColor: "#F0F0F2",
        imageScaleRate: 2,
      },
    ];

    const { container } = render(<Carousel data={data} delay={1000} />);
    expect(container).toBeInTheDocument();

    const slides = container.querySelectorAll("[class^=slide]");

    expect(slides[0]).toHaveStyle(`transform: translateX(0%)`);

    act(() => {
      jest.runOnlyPendingTimers();
    });

    expect(slides[1]).toHaveStyle(`transform: translateX(0%)`);
    act(() => {
      jest.runOnlyPendingTimers();
    });

    expect(slides[2]).toHaveStyle(`transform: translateX(0%)`);

    act(() => {
      jest.runOnlyPendingTimers();
    });

    expect(slides[0]).toHaveStyle(`transform: translateX(0%)`);
  });
});
