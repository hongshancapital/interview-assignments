import React, { useState, useEffect } from "react";

import CrSlider from "./CrSlider"
import CrIndicator from "./CrIndicator"

import airpods from "../assets/airpods.png";
import iphone from "../assets/iphone.png";
import tablet from "../assets/tablet.png";

// 因为时间关系，直接将数据固化在子组件里面
let slides = [
    {
        title: "xPhone",
        subtitle: "Lots to love. Less to spend.\nStarting at $399.",
        logo: iphone,
        theme: {
            color:"white",
            backgroundColor: "#111"
        }
    },
    {
        title: "Tablet",
        subtitle: "Just the right amount of everything.",
        logo: tablet,
        theme: {
            color: "black",
            backgroundColor:"#fafafa"
        }
    },
    {
        title: "Buy a Tablet or xPhone for college.\nGet airpods.",
        subtitle: "",
        logo: airpods,
        theme: {
            color: "black",
            backgroundColor: "#efefef"
        }
    },
]

var CrContainer = (props) => {
    let [current, setCurrent] = useState(1);
    useEffect(() => {
        setInterval(() => {
        setCurrent((current++) % slides.length);
        }, 2500)
    }, [])

    let style = {
        width: "100%",
        height: "100%",
        overflow: "hidden"
    }
    let duration = 2
    return (
        <div className="cr-container" style={style}>
            <CrSlider slides={slides}
                current={current}
                duration={duration}>
            </CrSlider>
            <div
                style={{ "position": "absolute", "bottom": "10px", "height": "50px", "width": "100%" }}>

                {
                    slides.map((slide, idx) => {
                        return <CrIndicator
                            key={idx}
                            percentage={(idx == current) ? 100 : 0}
                            duration={(idx == current) ? duration : 0} />
                    })
                }
            </div>

        </div>
    );
}

export default CrContainer;