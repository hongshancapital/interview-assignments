import React from "react";
import { render } from "@testing-library/react";
import testImg from "../../../../assets/airpods.png";
import Banner from ".";

const testTitle = "测试标题";
const testText = "测试正文";
const bgColor = "#fff";

test("组件正常渲染", () => {
  const index = 0;

  const { getByText, getByRole } = render(
    <Banner
      imgUrl={testImg}
      backgroundColor={bgColor}
      text={testText}
      title={testTitle}
      index={index}
    />
  );
  /** 偏移量，背景图,背景色是否正常渲染 */
  const bannerElement = getByRole("listitem");
  expect(bannerElement).toHaveStyle({
    backgroundColor: bgColor,
    backgroundImage: `url(${testImg})`,
    left: `${index}00%`,
  });

  /** 文本内容是否正常渲染 */
  const titleElement = getByText(new RegExp(testTitle));
  expect(titleElement).toBeInTheDocument();
  const textElement = getByText(new RegExp(testText));
  expect(textElement).toBeInTheDocument();
});

test("组件偏移量正常渲染", () => {
  const index = 2;

  const { getByRole } = render(
    <Banner
      imgUrl={testImg}
      backgroundColor={bgColor}
      text={testText}
      title={testTitle}
      index={index}
    />
  );

  const bannerElement = getByRole("listitem");
  expect(bannerElement).toHaveStyle(`left: ${index}00%`);
});

test("多标题正常渲染", () => {
  const newTitle = ["1", "2"];

  const { getAllByRole } = render(
    <Banner
      imgUrl={testImg}
      backgroundColor={bgColor}
      text={"testText"}
      title={newTitle}
      index={0}
    />
  );

  const headElements = getAllByRole("heading");
  expect(headElements.length).toBe(newTitle.length);
});

test("多文本正常渲染", () => {
  const newText = ["1", "2"];

  const { getAllByRole } = render(
    <Banner
      imgUrl={testImg}
      backgroundColor={bgColor}
      text={newText}
      title={"newTitle"}
      index={0}
    />
  );

  const pElements = getAllByRole("row");
  expect(pElements.length).toBe(newText.length);
});

test("无text属性正常渲染", () => {
  const { queryAllByRole } = render(
    <Banner
      imgUrl={testImg}
      backgroundColor={bgColor}
      title={"newTitle"}
      index={0}
    />
  );

  const pElements = queryAllByRole("row");
  expect(pElements.length).toBe(0);
});
