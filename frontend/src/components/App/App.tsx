import React from "react";

import "./App.css";
import { airpods, iphone, tablet } from "../../assets";
import Carousel, { CarouselItem } from "../Carousel";
import Text, { TextVariant } from "../Text";
import Box from "../Box";

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
            <Box maxWidth="l">
                <Carousel>
                    {data.map(
                        (
                            { textParentDiv, textComponents, imageComponent },
                            carouselItemIndex
                        ) => (
                            <CarouselItem key={`${carouselItemIndex}`}>
                                <div className={textParentDiv.className}>
                                    {textComponents.map(
                                        (textComponent, textIndex) => (
                                            <Text
                                                variant={
                                                    textComponent.variant as TextVariant
                                                }
                                                text={textComponent.text}
                                                key={`${carouselItemIndex}-${textIndex}`}
                                            />
                                        )
                                    )}
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
            </Box>
        </div>
    );
}

export default App;
