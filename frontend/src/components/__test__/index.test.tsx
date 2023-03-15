import { render, screen } from "@testing-library/react"
import Carousel, { Props as CarouselProps } from "../Carousel"

const demoDataListOne: CarouselProps["dataList"] = [
  {
    title: "title",
    id: "1",
  },
  {
    title: "title1",
    description: "description",
    id: "2",
  },
]

test("渲染正常状态 Carousel 组件", () => {
  render(<Carousel dataList={demoDataListOne} />)

  const titleText = screen.getByText("title")
  const title1Text = screen.getByText("title1")

  expect(titleText).toBeInTheDocument()
  expect(title1Text).toBeInTheDocument()

  const descriptionText = screen.getByText("description")

  expect(descriptionText).toBeInTheDocument()
})

const demoDataList: CarouselProps["dataList"] = []

test("渲染空状态 Carousel 组件", () => {
  render(<Carousel dataList={demoDataList} />)

  const emptyText = screen.getByText("Empty!")

  expect(emptyText).toBeInTheDocument()
})
