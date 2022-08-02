import React from "react";
import { render } from "@testing-library/react";
import App from "./App";
import { SLIDES } from "./const";

const titles = SLIDES.reduce((acc, cur) => {
  if (typeof cur.title === "string") {
    return acc.concat(cur.title);
  }
  return acc.concat(...cur.title);
}, [] as string[]);

const subTitles = SLIDES.filter((each) => Boolean(each.subTitle)).reduce(
  (acc, cur) => {
    if (typeof cur.subTitle === "string") {
      return acc.concat(cur.title);
    }
    return acc.concat(...cur.subTitle!);
  },
  [] as string[]
);

test("render titles", () => {
  const { queryAllByText } = render(<App />);
  titles.forEach((text) => {
    expect(queryAllByText(text)).toBeTruthy();
  });
});

test("render subtitles", () => {
  const { queryAllByText } = render(<App />);
  subTitles.forEach((text) => {
    expect(queryAllByText(text)).toBeTruthy();
  });
});

test("first item is visible", () => {
  const { queryByText } = render(<App />);
  expect(queryByText(titles[0])).toBeVisible();
});

test("other items are not visible", () => {
  const { queryByText } = render(<App />);
  [titles.at(-1), subTitles.at(-1)].forEach((text) => {
    expect(
      +(
        queryByText(text!)?.parentElement?.parentElement?.style.left || ""
      ).replace("%", "")
    ).toBeGreaterThanOrEqual(100);
  });
});
