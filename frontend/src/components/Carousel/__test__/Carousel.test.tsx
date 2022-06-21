import React from "react";
import Carousel from "..";
import { render } from "@testing-library/react";

const CarouselSlides = ({ duration = 0, startWithIndex = 0 }) => {
  return (
    <Carousel duration={duration} startWithIndex={startWithIndex}>
      <p key="slide1">slide 1</p>
      <p key="slide2">slide 2</p>
      <p key="slide3">slide 3</p>
    </Carousel>
  );
};

describe("Carousel", () => {
  test("Carousel rendered", () => {
    const { getByText, getByTestId, getAllByTestId } = render(
      <CarouselSlides duration={0} />
    );

    const slide1 = getByText("slide 1");
    expect(slide1).toBeVisible();
    const slide2 = getByText("slide 2");
    expect(slide2).toBeVisible();
    const slide3 = getByText("slide 3");
    expect(slide3).toBeVisible();

    const progressBar = getByTestId("progress-bars");
    expect(progressBar).toBeVisible();

    const slides = getByTestId("slides");
    expect(slides.style.transform).toBe("translateX(-0%)");

    const allSlides = getAllByTestId("slide");
    allSlides.forEach((slide) => {
      expect(slide.style.width).toBe("100%");
      expect(slide.style.height).toBe("100%");
      expect(slide.style.display).toBe("flex");
      expect(slide.style.justifyContent).toBe("center");
      expect(slide.style.alignItems).toBe("center");
      expect(slide.style.flexShrink).toBe("0");
    });
  });

  test("Carousel with start index", () => {
    const { getByTestId } = render(
      <CarouselSlides duration={2} startWithIndex={1} />
    );

    const slides = getByTestId("slides");
    expect(slides.style.transform).toBe("translateX(-100%)");
  });

  test("Carousel with wrong start index less then 0", () => {
    const { getByTestId } = render(
      <CarouselSlides duration={2} startWithIndex={-2} />
    );

    const slides = getByTestId("slides");
    expect(slides.style.transform).toBe("translateX(-0%)");
  });

  test("Carousel with wrong start index grater then children count", () => {
    const { getByTestId } = render(
      <CarouselSlides duration={2} startWithIndex={3} />
    );

    const slides = getByTestId("slides");
    expect(slides.style.transform).toBe("translateX(-0%)");
  });
});
