import React from "react";
import Carousel from ".";
import { mount } from "enzyme";
import { act } from 'react-dom/test-utils';

describe("<Carousel />", () => {
  it("should has prev, next and go function", () => {
    const ref: any = React.createRef();
    mount(
      <Carousel ref={ref}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    );
    const { prev, next, goTo } = ref.current;
    expect(typeof prev).toBe("function");
    expect(typeof next).toBe("function");
    expect(typeof goTo).toBe("function");
    expect(ref.current.offset).toBe(0);
    act(() => {
      ref.current.goTo(2);
    });
    expect(ref.current.offset).toBe(2);
    act(() => {
      ref.current.prev();
    });
    expect(ref.current.offset).toBe(1);
    act(() => {
      ref.current.next();
    });
    expect(ref.current.offset).toBe(2);
  });
});
