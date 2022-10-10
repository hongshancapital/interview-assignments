import React from "react";
import { Carousel } from "./components/Carousel";

import "./App.css";

/**
 * Banner 数据
 * Banner 图片高度相同，宽度不同，需要特殊处理。通过 backgroundSize 来控制图片大小相同
 */
const datas = [
    {
        // 标题
        title: "xPhone",
        // 描述
        description: (
            <div>
                <div>Lots to love.Less to Spend. </div>
                <div>Starting at $399.</div>
            </div>
        ),
        // 自定义 Banner 样式
        styles: {
            backgroundImage: require("./assets/iphone.png"),
            backgroundSize: "50%",
            backgroundColor: "#111",
            color: "#fff",
        },
    },
    {
        title: "Tablet",
        description: "Just the right amount of everything.",
        styles: {
            backgroundImage: require("./assets/tablet.png"),
            backgroundSize: "100%",
            backgroundColor: "#fafafa",
            color: "#000",
        },
    },
    {
        title: (
            <div>
                <div>Buy a Tablet or xPhone for College. </div>
                <div>Get AirPods</div>
            </div>
        ),
        description: " ",
        styles: {
            backgroundImage: require("./assets/airpods.png"),
            backgroundSize: "150%",
            backgroundColor: "#f1f1f3",
            color: "#000",
        },
    },
];

function App() {
    return (
        <div className="App">
            <Carousel>
                {datas.map((item) => {
                    return (
                        <div
                            // title 当成唯一的 key
                            key={item.title as string}
                            className={`app-carousel`}
                            style={{
                                backgroundImage: `url(${item.styles.backgroundImage})`,
                                backgroundSize: item.styles.backgroundSize,
                                backgroundColor: item.styles.backgroundColor,
                            }}
                        >
                            <div style={{ color: item.styles.color }}>
                                <h1>{item.title}</h1>
                                <div>{item?.description}</div>
                            </div>
                        </div>
                    );
                })}
            </Carousel>
        </div>
    );
}

export default App;
