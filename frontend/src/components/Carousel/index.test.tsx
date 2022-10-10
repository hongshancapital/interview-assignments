import React from "react";
import { render } from "@testing-library/react";
import { Carousel } from "./index";

describe("Carousel test", () => {
    it("base component rendering ", () => {
        const { queryByText, container } = render(
            <Carousel>
                <div>1</div>
                <div>2</div>
            </Carousel>
        );
        const emement1 = queryByText("1");
        const emement2 = queryByText("2");
        const el = container.querySelectorAll(".progress");

        expect(el.length).toBe(2);
        expect(emement1).toBeInTheDocument();
        expect(emement2).toBeInTheDocument();
    });

    it("rendering components with parameters", () => {
        const { container } = render(
            <Carousel dots={false}>
                <div>1</div>
                <div>2</div>
            </Carousel>
        );
        const el = container.querySelectorAll(".progress");
        expect(el.length).toBe(0);
    });

    it("rendering components with parameters2", () => {
        const { queryByText } = render(
            <Carousel dots={false}>
                children
            </Carousel>
        );
        const emement1 = queryByText("children");
        expect(emement1).toBeInTheDocument();
    });
});
