import React from "react";
import { render } from "@testing-library/react";
import Progress from "./progrss";

test("reset progress stoped to execute", (done) => {
  let executed = false;
  // TODO: progress 动画考虑重构为 css keyframes
  render(
    <Progress
      index={1}
      activeIndex={2}
      onTimeout={() => {
        executed = true;
      }}
      timeout={100}
    />
  );
  setTimeout(() => {
    expect(executed).toBeFalsy();
    done();
  }, 1000);
});

test("active progress should executed", (done) => {
  let executed = false;
  // TODO: progress 动画考虑重构为 css keyframes
  render(
    <Progress
      index={1}
      activeIndex={1}
      onTimeout={() => {
        executed = true;
      }}
      timeout={100}
    />
  );
  setTimeout(() => {
    expect(executed).toBeTruthy();
    done();
  }, 1000);
});

test("active progress should be 100%", (done) => {
  const result = render(
    <Progress index={1} activeIndex={1} onTimeout={() => {}} timeout={100} />
  );
  setTimeout(() => {
    expect(result.getByRole("progress-percent").style.width).toBe("100%");
    done();
  }, 1000);
});

test("active progress should be 0px", (done) => {
  const result = render(
    <Progress index={1} activeIndex={2} onTimeout={() => {}} timeout={100} />
  );
  setTimeout(() => {
    expect(result.getByRole("progress-percent").style.width).toBe("0px");
    done();
  }, 1000);
});
