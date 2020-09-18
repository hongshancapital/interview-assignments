import React, { useState, useEffect } from "react";

var ProgressBar = ({ percentage, duration }) => {

    let [style, setStyle] = useState({
        "position": "relative",
        "width": "0px"
    })

    let cntStyle = {
        "display": "inline-block",
        "marginRight": "5px",
        "width": "5%",
        "backgroundColor": "grey",
        "height": "4px",
        "borderRadius": "2px"
    }

    useEffect(() => {
        setStyle({
            "top": "0px",
            "borderRadius": "2px",
            "position": "relative",
            "height": "100%",
            "backgroundColor": "white",
            "color": "lightgrey",
            "transition": `width ${duration}s linear 0s`,
            "width": percentage + "%"
        });
    }, [percentage])

    return (
        <div className="progressbar-container"
            style={cntStyle}>
            <div className="progress" style={style}></div>
        </div>
    );
}

export default ProgressBar;