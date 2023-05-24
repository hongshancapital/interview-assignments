import { act, render, waitFor } from "@testing-library/react";
import Carousel from "./Carousel";

test("should autoplay", async () => {
  jest.useFakeTimers();

  const slideCount = 3;
  const interval = 2000;
  const { container } = render(
    <Carousel interval={interval}>
      {Array.from({ length: slideCount }, (_, i) => {
        return <div data-testid={i} key={i}></div>;
      })}
    </Carousel>
  );
  for (let i = 1; i < slideCount; i++) {
    await act(() => jest.advanceTimersByTime(interval));
    const slidesContainer = container.querySelector(".carousel__slides");
    const offset = `translateX(${-(i / slideCount) * 100}%)`;
    expect(slidesContainer).toHaveStyle({ transform: offset });
  }
});
