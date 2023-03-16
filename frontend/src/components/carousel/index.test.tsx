import { render, act, waitFor, fireEvent } from "@testing-library/react";
import Carousel from "./Carousel";

describe("carousel component test", () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  test("renders component", () => {
    const ins = render(
      <Carousel>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    expect(ins).toBeDefined();
    const oneElement = ins.getByText("1");
    expect(oneElement).toBeInTheDocument();
  });

  test("autoplay", async () => {
    const ins = render(
      <Carousel autoplay={true} previewDuration={2}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    const itemList = ins.getAllByTestId("carousel-item");
    expect(itemList[0]).toHaveAttribute("data-selected", "true");
    expect(itemList[1]).toHaveAttribute("data-selected", "false");
    expect(itemList[2]).toHaveAttribute("data-selected", "false");

    act(() => {
      jest.advanceTimersByTime(2000);
    });
    await waitFor(() => {
      expect(itemList[0]).toHaveAttribute("data-selected", "false");
      expect(itemList[1]).toHaveAttribute("data-selected", "true");
      expect(itemList[2]).toHaveAttribute("data-selected", "false");
    });
  });

  test("indicator click", () => {
    const ins = render(
      <Carousel>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    const itemList = ins.getAllByTestId("carousel-item");
    expect(itemList[0]).toHaveAttribute("data-selected", "true");
    expect(itemList[2]).toHaveAttribute("data-selected", "false");

    const indicator2Element = ins.getByTestId("indicator-2");
    fireEvent.click(indicator2Element);

    expect(itemList[0]).toHaveAttribute("data-selected", "false");
    expect(itemList[2]).toHaveAttribute("data-selected", "true");
  });
  test("onPageSelected callback", () => {
    const onPageSelected = jest.fn();
    const ins = render(
      <Carousel onPageSelected={onPageSelected}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );

    const indicator2Element = ins.getByTestId("indicator-2");
    fireEvent.click(indicator2Element);

    expect(onPageSelected).toHaveBeenCalledTimes(1);
    expect(onPageSelected).toHaveBeenCalledWith(2);
  });
});
