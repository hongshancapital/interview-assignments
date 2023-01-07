import { render, fireEvent } from "@testing-library/react";
import Carousel from "./index";
import { itemList } from "../mock";

test("renders carousel", () => {
  const { getByText, getAllByAltText } = render(<Carousel items={itemList} />);

  itemList?.forEach((item) => {
    item.titleList.forEach((title) => {
      expect(getByText(title)).toBeInTheDocument();
    });
    item.descList?.forEach((desc) => {
      expect(getByText(desc)).toBeInTheDocument();
    });
  });

  getAllByAltText("carousel").forEach((el) => {
    expect(el).toBeInTheDocument();
  });
});

test("test carousel click event", () => {
  const { container } = render(<Carousel items={itemList} />);

  expect(
    container.querySelector(
      ".carousel-process-item:nth-of-type(1) .carousel-process-duration-inner"
    )
  ).toBeInTheDocument();

  fireEvent.click(
    container.querySelector(".carousel-process-item:nth-of-type(3)")!
  );
  expect(
    container.querySelector(
      ".carousel-process-item:nth-of-type(3) .carousel-process-duration-inner"
    )
  ).toBeInTheDocument();
});
