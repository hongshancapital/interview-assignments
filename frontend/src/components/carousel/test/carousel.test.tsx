import { render, fireEvent } from "@testing-library/react";
import { Carousel } from "../index";

const props = {
  duration: 3000,
  height: 500,
  autoplay: true,
};

const element = (
  <Carousel {...props}>
    <Carousel.Item>
      <div className="iphone">
        <div className="iphone-title">xPhone</div>
        <div>Lots to love. Less to spend.</div>
        <div>Starting at $399.</div>
      </div>
    </Carousel.Item>
    <Carousel.Item>
      <div className="tablet">
        <div className="tablet-title">Tablet</div>
        <div>Just the right amount of everything.</div>
      </div>
    </Carousel.Item>
    <Carousel.Item>
      <div className="airpods">
        <div className="airpods-title">Buy a Tablet or xPhone for College.</div>
        <div className="airpods-title">Get arPods.</div>
      </div>
    </Carousel.Item>
  </Carousel>
);

describe("Carousel", () => {
  test("render Carousel", async () => {
    const { container } = render(element);
    const componentDOM = container.children[0] as unknown as HTMLDivElement;
    expect(componentDOM).toHaveClass("carousel");
    expect(componentDOM.style.height).toBe(props.height + "px");
  });
  test("click dot", () => {
    const { container } = render(element);
    const componentDOM = container.children[0];
    const itemsContainer = componentDOM.querySelector(
      ".carousel-items-container"
    ) as unknown as HTMLDivElement;
    const dots = componentDOM.querySelectorAll(".carousel-dot-item");
    fireEvent.click(dots[1]);
    expect(itemsContainer.style.transform).toBe("translate3D(-100%, 0px, 0px)");
    fireEvent.click(dots[2]);
    expect(
      itemsContainer.style.transform === "translate3D(-100%, 0px, 0px)"
    ).toBe(false);
    fireEvent.click(dots[0]);
    expect(
      itemsContainer.style.transform === "translate3D(-0%, 0px, 0px)"
    ).toBe(true);
  });
});
