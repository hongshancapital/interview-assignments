import React from "react";
import { render } from "@testing-library/react";
import Banner from ".";

test("renders learn react link", () => {
  const { getByText } = render(<Banner imgUrl={""} backgroundColor={""} title={""} index={0}/>);
  const linkElement = getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
