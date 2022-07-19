import React from "react";
import { render, screen, act } from "@testing-library/react";
import Carousel, { CarouselRef } from "./index";

jest.useFakeTimers();
jest.setTimeout(20000);

describe("Carousel Test", () => {
  test("Carousel runs well", (done) => {
    const ref = React.createRef<CarouselRef>();
    const demo = (
      <div style={{ width: "800px", height: "400px" }}>
        <Carousel ref={ref}>
          <div>1</div>
          <div>2</div>
          <div>3</div>
          <div>4</div>
          <div>5</div>
        </Carousel>
      </div>
    );
    render(demo);

    setTimeout(() => {
      expect(screen.getByText("1")).toBeInTheDocument();
      expect(screen.getByText("2")).toBeInTheDocument();
      expect(screen.getByText("3")).toBeInTheDocument();
      expect(screen.getByText("4")).toBeInTheDocument();
      expect(screen.getByText("5")).toBeInTheDocument();

      expect(ref.current).toBeDefined();
      expect(ref.current!.prev).toBeDefined();
      expect(ref.current!.next).toBeDefined();
      expect(ref.current!.play).toBeDefined();
      expect(ref.current!.pause).toBeDefined();
      expect(ref.current!.change).toBeDefined();
      expect(ref.current!.getIndex).toBeDefined();

      act(() => {
        ref.current!.prev();
        ref.current!.next();
        ref.current!.play();
        ref.current!.pause();
        ref.current!.change(1);
      });
      ref.current!.getIndex();

      done();
    }, 2000);
    jest.runOnlyPendingTimers();

    // screen.debug();
  });

  test("Carousel loop、autoplay is ok", (done) => {
    const ref = React.createRef<CarouselRef>();
    const demo = (
      <div style={{ width: "800px", height: "400px" }}>
        <Carousel ref={ref} autoplay loop duration={1000}>
          <div>1</div>
          <div>2</div>
        </Carousel>
      </div>
    );
    render(demo);

    setTimeout(() => {
      expect(ref.current?.getIndex()).toBe(0);
    }, 500);
    jest.runOnlyPendingTimers();

    setTimeout(() => {
      expect(ref.current?.getIndex()).toBe(1);
    }, 1000);
    jest.runOnlyPendingTimers();

    setTimeout(() => {
      expect(ref.current?.getIndex()).toBe(0);
      done();
    }, 1000);
    jest.runOnlyPendingTimers();
  });

  test("Carousel pause、 start is ok", (done) => {
    const ref = React.createRef<CarouselRef>();
    const demo = (
      <div style={{ width: "800px", height: "400px" }}>
        <Carousel ref={ref} autoplay loop duration={500}>
          <div>1</div>
          <div>2</div>
          <div>3</div>
        </Carousel>
      </div>
    );
    render(demo);

    setTimeout(() => {
      ref.current!.pause();

      expect(ref.current?.getIndex()).toBe(0);
    }, 300);
    jest.runOnlyPendingTimers();

    setTimeout(() => {
      expect(ref.current?.getIndex()).toBe(0);

      act(() => {
        ref.current!.play();
      });
    }, 600);
    jest.runOnlyPendingTimers();

    setTimeout(() => {
      expect(ref.current?.getIndex()).toBe(2);
      done();
    }, 600);
    jest.runOnlyPendingTimers();
  });

  test("Carousel prev、next is ok", (done) => {
    const ref = React.createRef<CarouselRef>();
    const demo = (
      <div style={{ width: "800px", height: "400px" }}>
        <Carousel ref={ref} autoplay={false} duration={500}>
          <div>1</div>
          <div>2</div>
          <div>3</div>
        </Carousel>
      </div>
    );
    render(demo);

    setTimeout(() => {
      expect(ref.current?.getIndex()).toBe(0);

      act(() => {
        ref.current!.next();
      });
      expect(ref.current?.getIndex()).toBe(1);
      act(() => {
        ref.current!.next();
      });
      expect(ref.current?.getIndex()).toBe(2);
      act(() => {
        ref.current!.next();
      });
      expect(ref.current?.getIndex()).toBe(0);

      act(() => {
        ref.current!.prev();
      });
      expect(ref.current?.getIndex()).toBe(2);
      act(() => {
        ref.current!.prev();
      });
      expect(ref.current?.getIndex()).toBe(1);
      act(() => {
        ref.current!.prev();
      });
      expect(ref.current?.getIndex()).toBe(0);

      done();
    }, 1000);
    jest.runOnlyPendingTimers();
  });

  test("Carousel change is ok", (done) => {
    const ref = React.createRef<CarouselRef>();
    const demo = (
      <div style={{ width: "800px", height: "400px" }}>
        <Carousel ref={ref} autoplay={false} duration={1000}>
          <div>1</div>
          <div>2</div>
          <div>3</div>
        </Carousel>
      </div>
    );
    render(demo);

    setTimeout(() => {
      expect(ref.current?.getIndex()).toBe(0);

      act(() => {
        ref.current!.change(1);
      });
      expect(ref.current?.getIndex()).toBe(1);
      act(() => {
        ref.current!.change(2);
      });
      expect(ref.current?.getIndex()).toBe(2);
      act(() => {
        ref.current!.change(0);
      });
      expect(ref.current?.getIndex()).toBe(0);

      done();
    }, 1500);
    jest.runOnlyPendingTimers();
  });
});
