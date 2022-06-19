import React from "react";
import ProgressBar from "..";
import { render } from "@testing-library/react";

describe("ProgressBar style", () => {
  test("default style rendered", () => {
    const { getByTestId } = render(<ProgressBar />);
    const progressBarEl = getByTestId("progress-bar");
    const progressBarBarEl = getByTestId("progress-bar-bar");
    const progressBarProgressEl = getByTestId("progress-bar-progress");

    expect(progressBarEl.style.width).toBe("100%");
    expect(progressBarEl.style.height).toBe("2px");

    expect(progressBarEl.style.borderRadius).toBe("1px");
    expect(progressBarBarEl.style.borderRadius).toBe("1px");
    expect(progressBarProgressEl.style.borderRadius).toBe("1px");

    expect(progressBarBarEl.style.backgroundColor).toBe("rgb(170, 170, 170)");
    expect(progressBarProgressEl.style.backgroundColor).toBe(
      "rgb(238, 238, 238)"
    );

    expect(progressBarProgressEl.style.transition).toBe("width 0s linear");
  });

  test("style props rendered", () => {
    const { getByTestId } = render(
      <ProgressBar
        width="40px"
        height="5px"
        borderRadius="2px"
        barColor="rgb(0, 0, 0)"
        progressColor="rgb(50, 50, 50)"
        duration={5}
        run={false}
      />
    );
    const progressBarEl = getByTestId("progress-bar");
    const progressBarBarEl = getByTestId("progress-bar-bar");
    const progressBarProgressEl = getByTestId("progress-bar-progress");

    expect(progressBarEl.style.width).toBe("40px");
    expect(progressBarEl.style.height).toBe("5px");

    expect(progressBarEl.style.borderRadius).toBe("2px");
    expect(progressBarBarEl.style.borderRadius).toBe("2px");
    expect(progressBarProgressEl.style.borderRadius).toBe("2px");

    expect(progressBarBarEl.style.backgroundColor).toBe("rgb(0, 0, 0)");
    expect(progressBarProgressEl.style.backgroundColor).toBe("rgb(50, 50, 50)");

    expect(progressBarProgressEl.style.transition).toBe("width 0s linear");
  });

  test("transition style applied", async () => {
    const { getByTestId } = render(<ProgressBar duration={2} run={true} />);

    const progressBarProgressEl = getByTestId("progress-bar-progress");
    expect(progressBarProgressEl.style.transition).toBe("width 2s linear");
  });
});
