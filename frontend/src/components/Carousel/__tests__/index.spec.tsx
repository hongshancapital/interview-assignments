import * as React from 'react'
import { render, screen } from "@testing-library/react"

import { Carousel } from "../index"

const { Slide } = Carousel

describe("test Carousel component", function () {
  it("should display two slide in page", function () {
    render(
      <Carousel>
        <Slide
          title={"title1"}
          src={'"https://picsum.photos/id/1/200/300"'}
          content={"content"}
        />
        <Slide
          title={"title2"}
          src={'"https://picsum.photos/id/2/200/300"'}
          content={"content"}
        />
      </Carousel>
    )

    expect(screen.getAllByText("content")).toHaveLength(2)
  })
})

describe("test Slide component", function () {
  it("should display correctly", function () {
    render(
      <Slide
        title={"title1"}
        src={"https://picsum.photos/id/1/200/300"}
        content={"content1"}
      />
    )

    expect(screen.getByText("title1")).toBeInTheDocument()
    expect(screen.getByText("content1")).toBeInTheDocument()
  })
})
