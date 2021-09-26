import React from "react";
import { create, act } from "react-test-renderer";
import { cleanup, fireEvent, render, waitFor, prettyDOM } from "@testing-library/react";
import { Caroussel } from "../components/Carousel";
import { getConstantValue } from "typescript";

afterEach(() => {
  cleanup();
});

test("render empty page, if no item was passed", () => {
  const component = create(<Caroussel items={[]} />);
  let tree = component.toJSON();
  expect(tree).toMatchSnapshot();
});

test("render right tree structure when passed items", () => {
  const getSlides = () => {
    const descriptions = [];
    for (let i = 0; i < 3; i += 1) {
      descriptions.push({
        text: `test-${i}`,
        bgColor: "red",
      });
    }
    return descriptions.map(({ text, bgColor }, i) => (
      <div
        key={i}
        style={{
          backgroundColor: bgColor,
        }}
      >
        {text}
      </div>
    ));
  };
  let root;
  act(() => {
    root = create(<Caroussel items={getSlides()} />);
  });
  expect(root.toJSON()).toMatchSnapshot();

  act(() => {
    root.update(<Caroussel items={getSlides()} indicators />);
  });
  expect(root.toJSON()).toMatchSnapshot();

  act(() => {
    root.update(<Caroussel items={getSlides()} control />);
  });
  expect(root.toJSON()).toMatchSnapshot();

  act(() => {
    root.update(<Caroussel items={getSlides()} control indicators />);
  });
  expect(root.toJSON()).toMatchSnapshot();
});

test("do interaction when user slide the page manually", () => {
  const getSlides = () => {
    const descriptions = [];
    for (let i = 1; i <= 3; i += 1) {
      descriptions.push({
        text: `test-${i}`,
        bgColor: "red",
      });
    }
    return descriptions.map(({ text, bgColor }, i) => (
      <div
        key={i}
        style={{
          backgroundColor: bgColor,
        }}
      >
        {text}
      </div>
    ));
  };
  const { queryAllByRole, queryByTestId, getByText } = render(
    <Caroussel items={getSlides()} control />
  );
  expect(queryAllByRole("link").length).toBe(2);

  const prev = queryByTestId("prev");
  fireEvent.click(prev);
  expect(
    getByText("test-3")?.parentElement?.classList.contains("active")
  ).toBeTruthy();

  const next = queryByTestId("next");
  fireEvent.click(next);
  expect(
    getByText("test-1")?.parentElement?.classList.contains("active")
  ).toBeTruthy();

  fireEvent.click(next);
  fireEvent.click(next);
  fireEvent.click(next);
  expect(
    getByText("test-1")?.parentElement?.classList.contains("active")
  ).toBeTruthy();
});

test("Cycle the slides", async () => {
  const getSlides = () => {
    const descriptions = [];
    for (let i = 1; i <= 3; i += 1) {
      descriptions.push({
        text: `test-${i}`,
        bgColor: "red",
      });
    }
    return descriptions.map(({ text, bgColor }, i) => (
      <div
        key={i}
        style={{
          backgroundColor: bgColor,
        }}
      >
        {text}
      </div>
    ));
  };
  const { container, getByText } = render(
    <Caroussel items={getSlides()} cycle interval={500} />
  );
  const test1 = getByText('test-1').parentElement;
  const test2 = getByText('test-2').parentElement;
  const test3 = getByText('test-3').parentElement;

  await waitFor(() => {
    expect(test1?.classList.contains('active')).toBeTruthy();
    expect(test2?.classList.contains('next')).toBeTruthy();
    expect(test3?.classList.contains('next')).toBeTruthy();
  }, { timeout: 400 })

  await waitFor(() => {

    expect(test1?.classList.contains('previous')).toBeTruthy();
    expect(test2?.classList.contains('active')).toBeTruthy();
    expect(test3?.classList.contains('next')).toBeTruthy();
  }, {
    timeout: 600,
  });

  await waitFor(() => {

    expect(test1?.classList.contains('previous')).toBeTruthy();
    expect(test2?.classList.contains('previous')).toBeTruthy();
    expect(test3?.classList.contains('active')).toBeTruthy();
  }, {
    timeout: 1200,
  });

  await waitFor(() => {
    expect(test1?.classList.contains('active')).toBeTruthy();
    expect(test2?.classList.contains('next')).toBeTruthy();
    expect(test3?.classList.contains('next')).toBeTruthy();
  }, {
    timeout: 1500,
  });
});
