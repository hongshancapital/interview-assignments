import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("render titles", () => {
  const { queryAllByText } = render(<App />);
  [
    "xPhone",
    "Tablet",
    "Buy a Tablet or xPhone for college.",
    "Get arPods",
  ].forEach((text) => {
    expect(queryAllByText(text)).toBeTruthy();
  });
});

test("render subtitles", () => {
  const { queryAllByText } = render(<App />);
  [
    "Lots to love. Less to spend.",
    "Starting at $399.",
    "Just the right amount of everything.",
  ].forEach((text) => {
    expect(queryAllByText(text)).toBeTruthy();
  });
});

test("first item is visible", () => {
  const { queryByText } = render(<App />);
  expect(queryByText("xPhone")).toBeVisible();
});

test("other items are not visible", () => {
  const { queryByText } = render(<App />);
  ["Tablet", "Buy a Tablet or xPhone for college."].forEach((text) => {
    expect(
      +(
        queryByText(text)?.parentElement?.parentElement?.style.left || ""
      ).replace("%", "")
    ).toBeGreaterThanOrEqual(100);
  });
});
