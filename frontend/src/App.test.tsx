import React from "react";
import { render } from "@testing-library/react";
import Carousel from "./components/Carousel";

const data = [
  {
    id: "xPhone",
    title: "xPhone",
    describe: "Lots to love. Less to spend.",
    price: "Starting at $399",
    picture: require("./assets/iphone.png").default,
    color: "#fff",
    backgroundColor: "#343131",
  },
  {
    id: "Tablet",
    title: "Tablet",
    describe: "Just the right amount of everything.",
    price: "",
    picture: require("./assets/tablet.png").default,
    backgroundColor: "#e7e5e5",
  },
  {
    id: "airPods",
    title: "Buy a Tablet or xPhone for college. Get airPods",
    describe: "",
    price: "",
    picture: require("./assets/airpods.png").default,
    backgroundColor: "#e7e5e5",
  },
];

describe("<Carousel />", () => {
  it("Carousel with children", () => {
    const wrapper = render(<Carousel dataSource={data} />);
    const children1 = wrapper.getAllByText("xPhone");
    expect(children1).toHaveLength(1);

    const children2 = wrapper.getAllByText("Tablet");
    expect(children2).toHaveLength(1);

    const children3 = wrapper.getAllByText(
      "Buy a Tablet or xPhone for college. Get airPods"
    );
    expect(children3).toHaveLength(1);
  });
});
