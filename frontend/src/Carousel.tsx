import React, { useState, useEffect } from "react"
import "./Carousel.scss"
interface Iprops {
    children?: any,
    width?: string,
    height?: string,
    css?: object,
    time?: number
}
export default function Carousel({ children, time = 3000 }: Iprops) {
    const [activeIndex, setActiveIndex] = useState(0)
    useEffect(() => {
        const timer: number = window.setInterval(() => {
            updateActiveIndex(activeIndex + 1)
        }, time)
        return () => {
            if (timer)
                window.clearInterval(timer)
        }
    })
    const updateActiveIndex = (newIndex: number) => {
        let count: number = React.Children.count(children)
        if (newIndex >= count) {
            newIndex = 0
        } else if (newIndex < 0) {
            newIndex = count - 1
        }
        setActiveIndex(newIndex)
        resetAnimation()
    }
    const resetAnimation = () => {
        document.getAnimations().forEach(animation => {
            animation.cancel()
            animation.play()
        })
    }
    const changeCarouselIndex = (index: number) => {
        updateActiveIndex(index);
        resetAnimation();
    };
    return (
        <div className="carousel-container" style={{ overflow: "hidden", background: "#000" }}>
            <div
                className="item-wrapper"
                style={{ transform: `translateX(-${activeIndex * 100}%)` }}
            >
                {React.Children.map(children, (child) => {
                    return React.cloneElement(child, { width: "100%", height: "100vh" });
                })}
            </div>
            <div className="progress">
                {React.Children.map(children, (child, index) => {
                    return (
                        <div
                            className="bar-wrapper"
                            onClick={() => changeCarouselIndex(index)}
                        >
                            <div
                                className="bar"
                                style={{
                                    animationDuration: index === activeIndex ? `${time}ms` : "0s",
                                    backgroundColor: index === activeIndex ? "#FFFFFF" : "unset",
                                }}
                            />
                        </div>
                    );
                })}
            </div>
        </div>
    )
}
export const CarouselItem = ({ width = "100%", height = "100%", css = {}, children }: Iprops) => {
    return (
        <div className="carousel-item" style={{ width, height, ...css }}>
            {children}
        </div>
    )
}

export interface CardInfo {
    title: string,
    content: string,
    img: string
}
export const CarouselCard = ({ title, content, img }: CardInfo) => {
    return (
        <div className="card">
            <p className="title">{title}</p>
            <p className="content">{content}</p>
            <div className="img-wrapper">
                <img src={img} alt="" />
            </div>
        </div>
    )
}