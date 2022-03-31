import React from "react";
import {render} from "@testing-library/react";

import Carousel from "./Carousel";
import {CarouselConfig} from "../../config/Carousel";

describe("Carousel Component Test", () => {

    test("Component Carousel render", () => {
        const {container} = render(<Carousel {...CarouselConfig}/>);
        const carousel = container.querySelector(".carousel");
        expect(carousel).toBeInTheDocument();
    });

    test("Component hide indicator render", () => {
        const {container} = render(<Carousel {...{...CarouselConfig, indicatorIsShow: false}}/>);
        const progress = container.querySelector(".progress");
        expect(progress).toBe(null);
    });
});