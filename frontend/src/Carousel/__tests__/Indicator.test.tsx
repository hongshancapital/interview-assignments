import { render } from "@testing-library/react";
import Indicator from "../Indicator";

describe("Indicator", () => {
  it("Should render correctly", () => {
    const onChange = jest.fn();
    expect(() => {
      render(<Indicator activeIndex={0} count={3} onChange={onChange} />);
    }).not.toThrow();
  });
});
