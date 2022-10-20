import React from "react";
import { render } from "@testing-library/react";
import { Carousel } from "./index";

describe("Carousel test", () => {

    test("Carousel component rendering", () => {
        const { queryByText, container } = render(
            <Carousel>
                <div>test1</div>
                <div>test2</div>
            </Carousel>
        );
        const emement1 = queryByText("test1");
        const emement2 = queryByText("test2");
        const el = container.querySelectorAll(".slick-dots li");
        expect(el.length).toBe(2);
        expect(emement1).toBeInTheDocument();
        expect(emement2).toBeInTheDocument();
    });

    it("rendering components with parameters", () => {
        const { container } = render(
            <Carousel autoplay>
                <div>1</div>
                <div>2</div>
            </Carousel>
        );
        const el = container.querySelectorAll(".slick-active");
        expect(el.length).toBe(1);
    });
});