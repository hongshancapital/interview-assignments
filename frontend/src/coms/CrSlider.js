import React, { useState, useEffect } from "react";

import CrSliderPage from "./CrSliderPage"

var CrSlider = ({ slides, current, duration }) => {
    var offset = -current * 100;
    let style = {
        display: "block",
        position: "relative",
        transform: `translateX(${offset}vw)`,
        height: "100%",
        width: (slides.length * 100) + "vw",
        transition: `transform ${duration / 2}s ease-out 0s`
    }

    return (
        <div className="cr-slider" style={style}>
            {
                slides.map((slide, idx) => {
                    return (
                        <CrSliderPage
                            key={idx}
                            logo={slide.logo}
                            subtitle={slide.subtitle}
                            theme={slide.theme}
                            title={slide.title} />
                    );
                })
            }
        </div>
    );
}

export default CrSlider;