import React from "react";
import {render} from "@testing-library/react";
import CarouselSlice from "./index";

test("renders carousel slice", () => {
  const {queryByText} = render(
    <CarouselSlice>
      <div>slice</div>
    </CarouselSlice>
  );
  // the carousel slice should be rendered
  const sliceElement = queryByText("slice");
  expect(sliceElement).toBeInTheDocument();
});

test("renders carousel slice lazily", () => {
  const {queryByText} = render(
    <CarouselSlice lazy={true}>
      <div>slice</div>
    </CarouselSlice>
  );
  // the carousel slice should be rendered
  const sliceElement = queryByText("slice");
  expect(sliceElement).not.toBeInTheDocument();
});
