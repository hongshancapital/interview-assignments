import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("renders learn react link", () => {
    const { getByText } = render(<App />);
    const ele1 = getByText(/Lots to love. Less to spend/i);
    expect(ele1).toBeInTheDocument();
    const ele2 = getByText(/Just the right amount of everything./i);
    expect(ele2).toBeInTheDocument();
    const ele3 = getByText(/Buy a Tablet or xPhone for college./i);
    expect(ele3).toBeInTheDocument();
});
