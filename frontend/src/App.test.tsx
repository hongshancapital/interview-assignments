import { cleanup, render } from "@testing-library/react";
import App from "./App";
import { act } from "react-dom/test-utils";

afterEach(() => cleanup());

const getCarouselSlides = () => {
  return document.querySelector<HTMLElement>(".carousel-slides");
};

test("should render required doms", () => {
  render(<App />);
  const slides: HTMLElement | null = getCarouselSlides();
  const carouselItems =
    document.querySelectorAll<HTMLDivElement>(".carousel-item");

  expect(slides?.style["left"]).toEqual("0px");
  expect(carouselItems.length).toEqual(3);

  const [iphone, tablet, airpods] =
    carouselItems as unknown as Array<HTMLDivElement>;

  expect(iphone.classList.contains("dark"));
  expect(iphone.querySelector(".title")?.textContent).toEqual("xPhone");
  expect(iphone.querySelector(".text")?.textContent).toContain(
    "Lots to love. Less to spend. Starting at $399.",
  );

  expect(tablet.classList.contains("light"));
  expect(tablet.querySelector(".title")?.textContent).toEqual("Tablet");
  expect(tablet.querySelector(".text")?.textContent).toContain(
    "Just the right amount of everything",
  );

  expect(airpods.classList.contains("light"));
  expect(airpods.querySelector(".title")?.textContent).toContain(
    "Buy a Tablet or xPhone for college. Get airPods.",
  );
  expect(airpods.querySelector(".text")?.textContent).toEqual("");

  expect(document.querySelector(".indicators")).toBeTruthy();
});

test("should animate correctly", async () => {
  jest.useFakeTimers();
  await act(() => {
    render(<App />);
  });

  const slides: HTMLElement | null = getCarouselSlides();
  const indicators = document.querySelectorAll<HTMLDivElement>(".indicator");
  // first carousel item should be in viewport
  expect(slides?.style["left"]).toEqual("0px");
  expect(indicators[0].classList.contains("active")).toBeTruthy();
  expect(indicators[1].classList.contains("active")).toBeFalsy();
  expect(indicators[2].classList.contains("active")).toBeFalsy();

  // second carousel item should be in viewport
  await act(() => {
    jest.runOnlyPendingTimers();
  });
  expect(slides?.style["left"]).toEqual("-100%");
  expect(indicators[1].classList.contains("active")).toBeTruthy();
  expect(indicators[0].classList.contains("active")).toBeFalsy();
  expect(indicators[2].classList.contains("active")).toBeFalsy();

  // third carousel item should be in viewport
  await act(() => {
    jest.runOnlyPendingTimers();
  });
  expect(slides?.style["left"]).toEqual("-200%");
  expect(indicators[2].classList.contains("active")).toBeTruthy();
  expect(indicators[1].classList.contains("active")).toBeFalsy();
  expect(indicators[0].classList.contains("active")).toBeFalsy();

  // first carousel item should be in viewport again
  await act(() => {
    jest.runOnlyPendingTimers();
  });
  expect(slides?.style["left"]).toEqual("0px");
  expect(indicators[0].classList.contains("active")).toBeTruthy();
  expect(indicators[1].classList.contains("active")).toBeFalsy();
  expect(indicators[2].classList.contains("active")).toBeFalsy();

  jest.useRealTimers();
});
