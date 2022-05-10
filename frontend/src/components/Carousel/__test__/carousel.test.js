import { render, screen } from "@testing-library/react"
import "@testing-library/jest-dom"
import React from "react"
import {Carousel} from "../index"

describe("Configuration component - test", () => {
  test("Should to render the correct number of banners and progress", () => {
    const items = [
      <div>1</div>,
      <div>2</div>,
      <div>3</div>,
    ]

    render(
      <Carousel height="100vh" width="100vw" items={items} />
    )

    expect(screen.getAllByTestId('progress').length).toBe(3);
    expect(screen.getAllByTestId('item').length).toBe(3);
  })
})
