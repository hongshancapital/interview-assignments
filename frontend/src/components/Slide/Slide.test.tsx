import React from "react";
import {render} from "@testing-library/react";
import Carousel from "../Carousel/Carousel";
import {CarouselBannerConfig, CarouselConfig} from "../../config/Carousel";

describe(" Carousel Component Test", () => {
    const len = CarouselBannerConfig.length;
    test("Component Indicator render", () => {
        const {container} = render(<Carousel {...CarouselConfig}/>);
        const indicatorList = container.querySelectorAll(".long");
        expect(indicatorList.length).toBe(len);
    });
});
