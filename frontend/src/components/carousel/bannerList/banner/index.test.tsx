import React from "react";
import { render } from "@testing-library/react";
import Banner from ".";

const testTitle = "测试标题";
const testText = "测试正文";

test("组件正常渲染", () => {
  const { getByText } = render(
    <Banner
      imgUrl={""}
      backgroundColor={"#fff"}
      text={testText}
      title={testTitle}
      index={0}
    />
  );

  const titleElement = getByText(new RegExp(testTitle));
  expect(titleElement).toBeInTheDocument();
  const textElement = getByText(new RegExp(testText));
  expect(textElement).toBeInTheDocument();
});

test("依据索引正确渲染偏移量", () => {
  const { queryByRole } = render(
    <Banner imgUrl={""} backgroundColor={"#fff"} title={testTitle} index={0} />
  );
  
  const textElement = queryByRole("paragraph");
  expect(textElement).toBeNull();
});
