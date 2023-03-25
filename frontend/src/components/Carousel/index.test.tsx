import { act, render } from "@testing-library/react";
import { Carousel } from "../Carousel";
import { items } from "../../App";

test("render CarouselItem slider Count", () => {
  const { getAllByRole } = render(<Carousel items={items} stayTime={3} />);
  const renderItem = getAllByRole(".carousel-slider");
  expect(renderItem.length).toBe(3);
});

test("render Carousel Animation right", () => {
  const { getAllByRole } = render(<Carousel items={items} stayTime={3} />);
  const renderItem = getAllByRole(".carousel-slider");
  act(() => {
    renderItem[1].click();
  });

  expect(renderItem[1].parentElement).toHaveClass("active");
});
