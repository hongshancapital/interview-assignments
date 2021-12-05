import React from "react";

import "./App.css";
import { airpods, iphone, tablet } from "../../assets";
import Carousel, { CarouselItem } from "../Carousel";
import Text, { TextVariant } from "../Text";

const data = [
    {
        textParentDiv: {
            className: "center color--white",
        },
        textComponents: [
            {
                variant: "title",
                text: "xPhone",
            },
            {
                variant: "content",
                text: "Lots to love. Less to spend.",
            },
            {
                variant: "content",
                text: "Starting at $399.",
            },
        ],
        imageComponent: {
            src: iphone,
            className: "full-width",
            alt: "iphone",
        },
    },
    {
        textParentDiv: {
            className: "center",
        },
        textComponents: [
            {
                variant: "title",
                text: "Tablet",
            },
            {
                variant: "content",
                text: "Just the right amount of everything.",
            },
        ],
        imageComponent: {
            src: tablet,
            className: "full-width",
            alt: "tablet",
        },
    },
    {
        textParentDiv: {
            className: "center",
        },
        textComponents: [
            {
                variant: "title",
                text: "Buy a Tablet or xPhone for college.",
            },
            {
                variant: "title",
                text: "Get arPods.",
            },
        ],
        imageComponent: {
            src: airpods,
            className: "full-width",
            alt: "airpods",
        },
    },
];

function App() {
    return (
        <div className="App">
            <Carousel>
                {data.map(
                    ({ textParentDiv, textComponents, imageComponent }) => (
                        <CarouselItem>
                            <div className={textParentDiv.className}>
                                {textComponents.map((textComponent) => (
                                    <Text
                                        variant={
                                            textComponent.variant as TextVariant
                                        }
                                        text={textComponent.text}
                                    />
                                ))}
                            </div>
                            <img
                                src={imageComponent.src}
                                className={imageComponent.className}
                                alt={imageComponent.alt}
                            />
                        </CarouselItem>
                    )
                )}
            </Carousel>
        </div>
    );
}

export default App;
