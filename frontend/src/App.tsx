import React from "react"
import "./App.scss"
import { Carousel } from "./components/Carousel/Carousel"
import iphone from "./assets/iphone.png"
import airpods from "./assets/airpods.png"
import tablet from "./assets/tablet.png"
import { Banner } from "./components/Banner/Banner"
import { ProcessIndicators } from "./components/Indicator/ProcessIndicator"
import { Dot } from "./components/Indicator/Dot/Dot"

const banners = [
	{
		title: "xPhone",
		desc: "Lots to love. Less to spend.\n Starting at $399.",
		img: iphone,
		color: "#fff",
		background: `#111`,
	},
	{
		title: "Tablet",
		desc: "Just the right amount of everything.",
		img: tablet,
		color: "#000",
		background: "#fafafa",
	},
	{
		title: "Buy a tablet or xPhone for college.\n Get airPods",
		desc: "",
		img: airpods,
		color: "#000",
		background: "#f1f1f3",
	},
]
function App() {
	return (
		<div className='App'>
			<Carousel
				height='100vh'
				direction='horizontal'
				autoplay={true}
				generateIndicator={(props) => {
					return ProcessIndicators(props)
				}}>
				{banners.map((item, index) => {
					const { title, desc, img, color, background } = item
					return (
						<Banner
							title={title}
							color={color}
							desc={desc}
							img={img}
							background={background}
							key={index}></Banner>
					)
				})}
			</Carousel>
			<Carousel
				height='100vh'
				direction='vertical'
				autoplay={true}
				generateIndicator={(props) => {
					return ProcessIndicators({
						...props,
						Indicator: Dot,
					})
				}}>
				{banners.map((item, index) => {
					const { title, desc, img, color, background } = item
					return (
						<Banner
							title={title}
							color={color}
							desc={desc}
							img={img}
							background={background}
							key={index}></Banner>
					)
				})}
			</Carousel>
		</div>
	)
}

export default App
