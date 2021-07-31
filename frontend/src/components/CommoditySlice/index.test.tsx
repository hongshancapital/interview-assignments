import React from "react";
import { render } from "@testing-library/react";
import CommoditySlice from "./index";
import iphonePic from "../../assets/iphone.jpg";

test("renders commodity", () => {
  const { getByText, getByAltText } = render(
    <CommoditySlice title="testTitle" desc="testDesc" pic={iphonePic} />
  );
  const titleElement = getByText("testTitle");
  expect(titleElement).toBeInTheDocument();
  const descElement = getByText("testDesc");
  expect(descElement).toBeInTheDocument();
  const picElement = getByAltText("testTitle");
  expect(picElement).toBeInTheDocument();
});

test("renders commodity with multi-line title", () => {
  const { getByText } = render(
    <CommoditySlice title={["testTitleLine1", "testTitleLine2"]} />
  );
  const titleLineElement1 = getByText("testTitleLine1");
  expect(titleLineElement1).toBeInTheDocument();
  const titleLineElement2 = getByText("testTitleLine2");
  expect(titleLineElement2).toBeInTheDocument();
});

test("renders commodity with multi-line desc", () => {
  const { getByText } = render(
    <CommoditySlice title="testTitle" desc={["testDesc1", "testDesc2"]} />
  );
  const descLineElement1 = getByText("testDesc1");
  expect(descLineElement1).toBeInTheDocument();
  const descLineElement2 = getByText("testDesc2");
  expect(descLineElement2).toBeInTheDocument();
});

test("renders commodity without desc", () => {
  const { getByText } = render(
    <CommoditySlice title="testTitle" />
  );
  const descElements = document.getElementsByClassName("commodity-desc");
  expect(descElements.length).toBe(0);
});

test("renders commodity without pic", () => {
  const { getByText } = render(
    <CommoditySlice title="testTitle" />
  );
  const picElements = document.getElementsByClassName("commodity-pic");
  expect(picElements.length).toBe(0);
});
