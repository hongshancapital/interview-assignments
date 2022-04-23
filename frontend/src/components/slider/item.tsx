import React from "react"
import './item.css'

export interface ISliderItemProps {
    title: string;
    subtitle: string
    backgroundImage: string;
    backgroundColor: string;
}

export const Item = (props: ISliderItemProps) => {
    return (
        <div
            className="slider-wrapper"
            style={{
                backgroundColor: props.backgroundColor
            }}
        >
            <div
                className="heading"
            >
                <h1>{props.title}</h1>
                <span>{props.subtitle}</span>
            </div>
            <div
                className="footer"
                style={{
                    backgroundImage: `url(${props.backgroundImage})`,
                }}
            >
            </div>
        </div>
    )
}