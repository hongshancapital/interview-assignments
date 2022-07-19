import React from "react";
import {render} from "@testing-library/react";
import Carousel from "../Carousel/Carousel";
import {CarouselBannerConfig, CarouselConfig} from "../../config/Carousel";

describe(" Carousel Component Test", () => {
    const len = CarouselBannerConfig.length;
    test("Carousel Slide render", () => {
        const {container} = render(<Carousel {...CarouselConfig}/>);
        const imageList = container.querySelectorAll(`.banner`);
        expect(imageList.length).toBe(len);
    });

});