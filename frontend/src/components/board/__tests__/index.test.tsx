import React from "react";
import { render, screen } from "@testing-library/react";

import Board from "../index";

describe("test Board", () => {
  test("render content correctly", () => {
    const { container } = render(
      <Board
        src="src"
        contents={[
          { text: "123", type: "title" },
          { text: "456", type: "text" },
        ]}
      />
    );
    expect(screen.getAllByRole("img")).toBeInTheDocument;
    expect(container.querySelectorAll(".text").length).toBe(1);
    expect(container.querySelectorAll(".title").length).toBe(1);
  });
});
