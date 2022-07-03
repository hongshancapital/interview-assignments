import React from "react";
import Carousel from "./";
import userEvent from '@testing-library/user-event';
import { screen, render } from "@testing-library/react";

describe("Performance", () => {
  test("Page 1", () => {
    render(<Carousel />);

    expect(screen.getByText("xPhone")).toBeInTheDocument();
    expect(screen.getByText("xPhone")).toHaveClass("title");
    expect(
      screen.getByText("Lots to love. Less to spend.")
    ).toBeInTheDocument();
    expect(screen.getByText("Lots to love. Less to spend.")).toHaveClass(
      "text"
    );
    expect(screen.getByText("Starting at $399.")).toBeInTheDocument();
    expect(screen.getByText("Starting at $399.")).toHaveClass("text");
  });

  test("Page 2", () => {
    render(<Carousel />);

    expect(screen.getByText("Tablet")).toBeInTheDocument();
    expect(screen.getByText("Tablet")).toHaveClass("title");
    expect(
      screen.getByText("Just the right amount of everything.")
    ).toBeInTheDocument();
    expect(
      screen.getByText("Just the right amount of everything.")
    ).toHaveClass("text");
  });

  test("Page 3", () => {
    render(<Carousel />);

    expect(
      screen.getByText("Buy a Tablet or xPhone for college.")
    ).toBeInTheDocument();
    expect(
      screen.getByText("Buy a Tablet or xPhone for college.")
    ).toHaveClass("title");
    expect(screen.getByText("Get airPods.")).toBeInTheDocument();
    expect(screen.getByText("Get airPods.")).toHaveClass("title");
  });
});

describe("Functional", () => {
  test("Switch Page", async () => {
    render(<Carousel />);
    expect(screen.getByTestId('list')).toHaveStyle('transform: translateX(100vw)')
    await userEvent.click(screen.getByTestId('footer-1'));
    expect(screen.getByTestId('list')).toHaveStyle('transform: translateX(0vw)')
    await userEvent.click(screen.getByTestId('footer-2'));
    expect(screen.getByTestId('list')).toHaveStyle('transform: translateX(-100vw)')
    await userEvent.click(screen.getByTestId('footer-0'));
    expect(screen.getByTestId('list')).toHaveStyle('transform: translateX(100vw)')
  });
});
