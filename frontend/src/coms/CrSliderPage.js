import React, { useState, useEffect } from "react";

var CrSliderPage = ({ title, subtitle, logo, theme }) => {
    let style = Object.assign({},{
        display: "inline-block",
        position: "relative",
        height: "100%",
        width: "100vw",
    }, theme);

    let txtStyle = {
        position: "absolute",
        top:"15%",
        left: "50%",
        transform:"translateX(-50%)"
    }

    let logoStyle = {
        maxHeight: "50%",
        position: "absolute",
        bottom: "100px",
        left: "50%",
        transform: "translateX(-50%)"
    }
    return (
        <div className="cr-slider-page" style={style}>
            <div>
                <img src={logo} alt="logo" style={logoStyle} />
            </div>
            <div className="txt-layer" style={txtStyle}>
                {
                    title.split("\n").map((ln) => {
                        return (
                            <h1>
                                {ln}
                            </h1>
                        )
                    })
                }
                {
                    subtitle.split("\n").map((ln) => {
                        return (
                            <p>
                                {ln}
                            </p>
                        )
                    })
                }
            </div>
        </div>
    )
}

export default CrSliderPage;