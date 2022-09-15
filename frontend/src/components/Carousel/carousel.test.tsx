import React from "react";
import { render } from "@testing-library/react";
import Carousel from "./index";

test("Carousel has 2 children", (done) => {
  const datasource = [
    {
      id: "1",
      title: "Buy a Tablet or xPhone for college.<br/>Get arPods.",
      sub: "",
      image: "airpods",
      color: "#000",
    },
    {
      id: "2",
      title: "xPhone",
      sub: "Lots to love. Less to spend.<br/>Starting at $399.",
      image: "iphone",
      color: "#fff",
    },
  ];
  const result = render(<Carousel datasource={datasource} timeout={100} />);
  setTimeout(() => {
    expect(result.container.querySelector(".wrapper")?.children.length).toBe(2);
    expect(
      result.container.querySelector(".pagination-container")?.children.length
    ).toBe(2);
    done();
  }, 1000);
});

test("Carousel has no children ", (done) => {
  const result = render(<Carousel datasource={[]} timeout={100} />);
  setTimeout(() => {
    expect(result.container.querySelector(".wrapper")?.children.length).toBe(0);
    expect(
      result.container.querySelector(".pagination-container")?.children.length
    ).toBe(0);
    done();
  }, 1000);
});
