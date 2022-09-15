import React from "react";
import { render } from "@testing-library/react";
import Pagination from "./index";

test("Pagination on active change work", (done) => {
  let execute = false;
  render(
    <Pagination
      size={3}
      timeout={100}
      onActiveChange={(i) => {
        execute = true;
      }}
    />
  );
  setTimeout(() => {
    expect(execute).toBeTruthy();
    done();
  }, 1000);
});

test("Pagination has 2 children", (done) => {
  const result = render(
    <Pagination size={2} timeout={100} onActiveChange={(i) => {}} />
  );
  setTimeout(() => {
    expect(
      result.container.querySelector(".pagination-container")?.children.length
    ).toBe(2);
    done();
  }, 1000);
});

test("Pagination has no children", (done) => {
  const result = render(
    <Pagination size={0} timeout={100} onActiveChange={(i) => {}} />
  );
  setTimeout(() => {
    expect(
      result.container.querySelector(".pagination-container")?.children.length
    ).toBe(0);
    done();
  }, 1000);
});
