import React from "react";
import "./App.css";
import Carousel from "./Carousel";
const airpods = require("./assets/airpods.png").default;
const iphone = require("./assets/iphone.png").default;
const tablet = require("./assets/tablet.png").default;

/**
 * Carousel组件说明
 * width {Number} - 外层容器宽度
 * height {Number} - 外层容器高度
 * delay {Number} - 每次轮播的时间
 * speed {Number} - 每次轮播的速度
 * data {IData} - 内容数据配置
 *
 */

interface IStyle {
	color: string;
	backgroundImage: string;
}
export interface IData {
	title: string[];
	desc: string[];
	style: IStyle;
}
const data: IData[] = [
	{
		title: ["Buy a Tablet or xPhone for college.", "Get airPods."],
		desc: [],
		style: {
			color: "#212121",
			backgroundImage: airpods,
		},
	},
	{
		title: ["xPhone"],
		desc: ["Lots to love. Less to spend.", "Starting at $399"],
		style: {
			color: "#fff",
			backgroundImage: iphone,
		},
	},
	{
		title: ["Tablet"],
		desc: ["Just the right amount of everything"],
		style: {
			color: "#212121",
			backgroundImage: tablet,
		},
	},
  {
		title: ["常恩会"],
		desc: ["电话: 15810967170", "邮箱: changenhui@outlook.com"],
		style: {
			color: "#fff",
			backgroundImage: iphone,
		},
	},
];
function App() {
	return (
		<div className="App">
			<Carousel data={data} width={700} height={400} delay={3000} speed={500} />
		</div>
	);
}

export default App;
