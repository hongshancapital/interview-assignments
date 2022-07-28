import { render } from "@testing-library/react";
import { StrictMode } from "react";
import Carousel from "./";
describe("Carousel", () => {
  describe("mount", () => {
    it("component mount", () => {
      const { unmount, rerender } = render(<Carousel />, {
        wrapper: StrictMode,
      });
      expect(() => {
        rerender(<Carousel />);
        unmount();
      }).not.toThrow();
    });
  });
});
