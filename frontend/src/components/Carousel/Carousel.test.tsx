import { act, render } from "@testing-library/react";
import Carousel from "./Carousel";

const carousels = ["1", "2", "3", "4", "5"];
let currentIndex = 2;
const setCurrentIndex = (index: number) => {
  currentIndex = index;
};
const autoplay = 3000;
const transitionOptions = {
  duration: 1000,
  timingFunction: "cubic-bezier(0.16, 1, 0.3, 1)",
  delay: 0,
};
test("renders Carousel Component", () => {
  jest.useFakeTimers();
  expect(currentIndex).toBe(2);
  const { container } = render(
    <Carousel
      carousels={carousels}
      currentIndex={currentIndex}
      setCurrentIndex={setCurrentIndex}
      autoplay={autoplay}
      transitionOptions={transitionOptions}
    />
  );
  expect(container.querySelectorAll(".carousel__wrapper__item").length).toBe(
    carousels.length
  );
  expect(container.querySelectorAll(".carousel__pagination__item").length).toBe(
    carousels.length
  );
  act(() => {
    jest.runOnlyPendingTimers();
  });
  expect(
    container.querySelectorAll(".carousel__pagination__item__bar--moving")
      .length
  ).toBe(1);
  expect(currentIndex).not.toBe(2);
});
