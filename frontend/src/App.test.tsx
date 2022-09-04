import { act, render } from "@testing-library/react";
import React from "react";
import App from "./App";

describe("作业测试", function () {
  it("三张图展示", async function () {
    await act(async () => {
      const { getByText } = render(<App />);

      const xPhoneTitle = getByText("xPhone");
      const xPhoneDesc = getByText(/Lots to love. Less to spend./);
      const tabletTitle = getByText("Tablet");
      const tabletDesc = getByText("Just the right amount of everything.");
      const podsTitle = getByText(/Buy a Tablet or xPhone for college./);
      expect(xPhoneTitle).toBeInTheDocument();
      expect(xPhoneDesc).toBeInTheDocument();
      expect(tabletTitle).toBeInTheDocument();
      expect(tabletDesc).toBeInTheDocument();
      expect(podsTitle).toBeInTheDocument();
    });
  });
});
