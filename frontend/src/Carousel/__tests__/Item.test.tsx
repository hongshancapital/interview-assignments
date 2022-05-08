import { render, screen } from "@testing-library/react";
import CarouselItem from "../Item";
import { DataItem } from "../../types";
import iphone from "../../assets/iphone.png";

describe("Carousel Item", () => {
  it("Should render correctly", () => {
    const data: DataItem = {
      id: 1,
      title: ["xPhone"],
      subTitle: ["Lots to love.Less to spend.", "Starting at$399."],
      color: "#ffffff",
      backgroundColor: "#000000",
      img: iphone,
    };
    const { container } = render(<CarouselItem data={data} />);
    const item = container.querySelector<HTMLElement>(".carousel-item");
    expect(item?.style.color).toBe("rgb(255, 255, 255)");
    expect(item?.style.backgroundColor).toBe("rgb(0, 0, 0)");
    expect(screen.getByText(/xPhone/)).toBeInTheDocument();
    expect(screen.getByText(/Lots to love.Less to spend./)).toBeInTheDocument();
    expect(screen.getByText(/Starting at\$399./)).toBeInTheDocument();
    expect(screen.getByRole("img")).toBeInTheDocument();
  });
});
