import { render, RenderResult, fireEvent } from "@testing-library/react"
import Carousel from "./index"

let wrapper: RenderResult

describe("test Carousel component", () => {
  beforeEach(() => {
    wrapper = render(
      <Carousel autoPlay>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    )
  })
  afterEach(() => {
    jest.clearAllMocks()
  })
  it("should render to document", () => {
    let div = wrapper.queryByText("1")
    expect(div).toBeInTheDocument()
    div = wrapper.queryByText("2")
    expect(div).toBeInTheDocument()
  })
  it("dot element should in the page", () => {
    const dotElement = wrapper.container.querySelector(".carousel__dot")
    expect(dotElement).toBeInTheDocument()
    const items = dotElement?.querySelectorAll(".carousel__item")
    expect(items?.length).toBe(3)
    expect(items?.[0]).toHaveClass("carousel__item carousel__item--active")
  })

  it("click dot element", () => {
    //
    const items = wrapper.container.querySelectorAll(".carousel__item")
    fireEvent.click(items[1])
    expect(items[1]).toHaveClass("carousel__item carousel__item--active")
    fireEvent.click(items[2])
    expect(items[2]).toHaveClass("carousel__item carousel__item--active")
  })
})
