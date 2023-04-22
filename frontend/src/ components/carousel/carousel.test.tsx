import React from "react";
import { render, act } from "@testing-library/react";
import Carousel from "./index";

describe("Carousel", () => {

    it("should render correctly", () => {
        const images = ["image1.jpg", "image2.jpg", "image3.jpg"];
        const { container } = render(<Carousel images={images} intervalTime={3000} />);
        expect(container.querySelector(".carousel-images")).toBeInTheDocument();
        expect(container.querySelector(".carousel-indicators")).toBeInTheDocument();
    });

    it("should switch to the next image after the specified interval time", async () => {
        jest.useFakeTimers();
        const images = ["image1.jpg", "image2.jpg", "image3.jpg"];
        const intervalTime = 2000;
        const { container } = render(<Carousel images={images} intervalTime={intervalTime} />);
        const indicators = container.querySelectorAll(".progress-bar");
        expect(indicators.length).toBe(images.length);
        expect(container.querySelector(`img[src="${images[0]}"]`)).toBeInTheDocument();

        act(() => {
            jest.advanceTimersByTime(intervalTime);
        });
        expect(container.querySelector(`img[src="${images[1]}"]`)).toBeInTheDocument();
        expect(indicators[1]).toHaveClass("progress-bar");

        act(() => {
            jest.advanceTimersByTime(intervalTime);
        });
        expect(container.querySelector(`img[src="${images[2]}"]`)).toBeInTheDocument();
        expect(indicators[2]).toHaveClass("progress-bar");

        act(() => {
            jest.advanceTimersByTime(intervalTime);
        });
        expect(container.querySelector(`img[src="${images[0]}"]`)).toBeInTheDocument();
        expect(indicators[0]).toHaveClass("progress-bar");

        jest.useRealTimers();
    });

});
