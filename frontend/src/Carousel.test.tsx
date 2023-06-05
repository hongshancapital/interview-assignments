import React from "react";
import { render, screen } from "@testing-library/react";

import Item from "./Carousel/Item";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";
import Carousel from "./Carousel";
import { act } from "react-dom/test-utils";

test("Carousel item title", () => {
  const pic = "a.png";
  const titleContent = "title test";
  render(<Item imgSrc={pic} title={<h1>{titleContent}</h1>} />);
  const h1Element = screen.queryByText(titleContent);
  console.log(h1Element);
  expect(h1Element).toBeInTheDocument();
});

test("Carousel item subTitle", () => {
  const pic = "a.png";
  const subTitleContent = "subTitle test";
  render(<Item imgSrc={pic} subTitle={<h1>{subTitleContent}</h1>} />);
  const subTitleElement = screen.queryByText(subTitleContent);
  console.log(subTitleElement);
  expect(subTitleElement).toBeInTheDocument();
});

test("Carousel item background img", () => {
  const pic = iphone;
  const { baseElement } = render(<Item imgSrc={pic} />);
  const subTitleElement = (
    baseElement.getElementsByClassName("item")[0] as HTMLDivElement
  ).style.backgroundImage;
  expect(subTitleElement).toEqual(`url(${pic})`);
});

test("Carousel item", () => {
  const itemList = [
    {
      imgSrc: iphone,
      title: (
        <div
          style={{
            color: "white",
            fontSize: "60px",
            lineHeight: "60px",
            fontWeight: "bold",
          }}
        >
          xPhone
        </div>
      ),
      subTitle: (
        <div style={{ color: "white", fontSize: "30px" }}>
          Lots to love. Less to spend.
          <br />
          Starting at $399.
        </div>
      ),
    },
    {
      imgSrc: tablet,
      title: <h1 style={{ color: "black", fontSize: "50px" }}>Tablet</h1>,
      subTitle: (
        <h2 style={{ color: "black", fontSize: "30px" }}>
          Just the right amount of everything.
        </h2>
      ),
    },
    {
      imgSrc: airpods,
      title: (
        <h1 style={{ color: "black", fontSize: "50px" }}>
          Buy a Tablet or xPhone for college.
          <br />
          Get arPods.
        </h1>
      ),
      subTitle: null,
    },
  ];
  const { innerWidth, innerHeight } = window;
  const { baseElement } = render(
    <Carousel
      defaultCounter={2}
      duration={0.5}
      width={innerWidth}
      height={innerHeight}
    >
      {itemList.map((item, index) => (
        <Item key={index} {...item} />
      ))}
    </Carousel>
  );
  const items = baseElement.getElementsByClassName("item");
  expect(items.length).toEqual(itemList.length);
  for (let i = 0; i < items.length; i++) {
    expect((items[i] as HTMLDivElement).style.backgroundImage).toEqual(
      `url(${itemList[i].imgSrc})`
    );
  }
});
test("Carousel item timer", () => {
  const itemList = [
    {
      imgSrc: iphone,
      title: (
        <div
          style={{
            color: "white",
            fontSize: "60px",
            lineHeight: "60px",
            fontWeight: "bold",
          }}
        >
          xPhone
        </div>
      ),
      subTitle: (
        <div style={{ color: "white", fontSize: "30px" }}>
          Lots to love. Less to spend.
          <br />
          Starting at $399.
        </div>
      ),
    },
    {
      imgSrc: tablet,
      title: <h1 style={{ color: "black", fontSize: "50px" }}>Tablet</h1>,
      subTitle: (
        <h2 style={{ color: "black", fontSize: "30px" }}>
          Just the right amount of everything.
        </h2>
      ),
    },
    {
      imgSrc: airpods,
      title: (
        <h1 style={{ color: "black", fontSize: "50px" }}>
          Buy a Tablet or xPhone for college.
          <br />
          Get arPods.
        </h1>
      ),
      subTitle: null,
    },
  ];
  const { innerWidth, innerHeight } = window;
  jest.useFakeTimers();
  const callback = jest.fn();

  const { baseElement } = render(
    <Carousel
      callback={callback}
      defaultCounter={2}
      duration={0.5}
      width={innerWidth}
      height={innerHeight}
    >
      {itemList.map((item, index) => (
        <Item key={index} {...item} />
      ))}
    </Carousel>
  );
  act(() => {
    jest.runOnlyPendingTimers();
  });
  expect(callback).toBeCalled();
  const container = baseElement.getElementsByClassName("item-container");
  expect((container[0] as HTMLDivElement).style.left).toEqual(
    `-${innerWidth}px`
  );
});
