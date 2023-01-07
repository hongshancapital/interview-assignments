import { render } from "@testing-library/react";
import App from "./App";
import { itemList } from "./mock";

test("renders App", () => {
  const { getByText, getAllByAltText } = render(<App />);

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
