import React, { useRef } from "react";
import { render, cleanup, fireEvent } from "@testing-library/react";
import Carousel from "../Index";

import iphone from "../../../assets/iphone.png";
import tablet from "../../../assets/tablet.png";
import airpods from "../../../assets/airpods.png";
const demoData = [
  {
    id: 1,
    title: "xPhone",
    description: (
      <>
        Lots to love. Less to spend.
        <br />
        Starting at $399.{" "}
      </>
    ),
    style: {
      backgroundImage: `url(${iphone})`,
      backgroundSize: "cover",
      backgroundPosition: "50% 100%",
      color: "#fff",
    },
  },
  {
    id: 2,
    title: "Tablet",
    description: "Just the right amount of everything.",
    style: {
      backgroundImage: `url(${tablet})`,
      backgroundSize: "cover",
      backgroundPosition: "50% 15%",
    },
  },
  {
    id: 3,
    title: (
      <>
        Buy a Tablet or xPhone for college. <br />
        Get arPods
      </>
    ),
    style: {
      backgroundImage: `url(${airpods})`,
      backgroundSize: "cover",
      backgroundPosition: "50% 15%",
    },
  },
];
const getRandom = (n, m) => Math.floor(Math.random() * (m - n + 1)) + n;

describe("Carousel component", () => {
  afterAll(cleanup);
  it("Renders without crashing", () => {
    render(
      <Carousel>
        {demoData.map((item) => (
          <Carousel.Item key={item.id} {...item} />
        ))}
      </Carousel>
    );
  });
  it("Should have prev, next and go function", () => {
    const ref = React.createRef();
    render(
      <Carousel ref={ref}>
        {demoData.map((item) => (
          <Carousel.Item key={item.id} {...item} />
        ))}
      </Carousel>
    );
    const { prev, next, goTo } = ref.current;
    expect(typeof prev).toBe("function");
    expect(typeof next).toBe("function");
    expect(typeof goTo).toBe("function");
  });
  test("The onChange prop is called when a dot is clicked", () => {
    const onChange = jest.fn();
    const { container } = render(
      <Carousel onChange={onChange}>
        {demoData.map((item) => (
          <Carousel.Item key={item.id} {...item} />
        ))}
      </Carousel>
    );
    const randomIndex = getRandom(0, demoData.length - 1);
    const dotClick = container.querySelectorAll("li.carousel-dot-item")[randomIndex];
    fireEvent.click(dotClick);
    expect(onChange).toHaveBeenCalled();
  });

  it("Checking that the correct number of data is rendered", () => {
    const { container } = render(
      <Carousel>
        {demoData.map((item) => (
          <Carousel.Item key={item.id} {...item} />
        ))}
      </Carousel>
    );
    const cardLength = container.querySelectorAll("li.carousel-item");
    const dotLength = container.querySelectorAll("li.carousel-dot-item");
    expect(cardLength.length).toBe(demoData.length);
    expect(dotLength.length).toBe(demoData.length);
  });

  it("Verifying that the first panel and first dot is highlighted by default", () => {
    const { container } = render(
      <Carousel>
        {demoData.map((item) => (
          <Carousel.Item key={item.id} {...item} />
        ))}
      </Carousel>
    );
    const firstSlide = container.querySelectorAll(".carousel-list")[0];
    const { left } = firstSlide.style._values;
    expect(left).toEqual("calc(0 * 100vw)");
    const dotDefault = container.querySelectorAll("li.carousel-dot-item")[0];
    expect(dotDefault).toHaveClass("active");
  });

  it("Checking that the specified dot is highlighted when current prop is set", () => {
    const targetIndex = getRandom(0, demoData.length - 1);
    const { container } = render(
      <Carousel current={targetIndex}>
        {demoData.map((item) => (
          <Carousel.Item key={item.id} {...item} />
        ))}
      </Carousel>
    );
    const firstSlide = container.querySelectorAll(".carousel-list")[0];
    const { left } = firstSlide.style._values;
    expect(left).toEqual(`calc(${-1 * targetIndex} * 100vw)`);
    const dotCurrent = container.querySelectorAll("li.carousel-dot-item")[targetIndex];
    const dotActive = container.querySelector("li.carousel-dot-item.active");
    expect(dotCurrent).toEqual(dotActive);
  });

  it("The specified dot is highlighted when clicked", () => {
    const { container } = render(
      <Carousel>
        {demoData.map((item) => (
          <Carousel.Item key={item.id} {...item} />
        ))}
      </Carousel>
    );
    const targetIndex = getRandom(0, demoData.length - 1);
    const dotClick = container.querySelectorAll("li.carousel-dot-item")[targetIndex];
    fireEvent.click(dotClick);
    expect(dotClick).toHaveClass("active");

    const firstSlide = container.querySelectorAll(".carousel-list");
    const { left } = firstSlide[0].style._values;
    expect(left).toEqual(`calc(${-1 * targetIndex} * 100vw)`);
  });
});
