/**
 *  姓名：张克贵
 *  电话：18702711014
 *  邮箱：1090140795@qq.com
 *  坐标：武汉
 *  面试岗位：前端开发工程师
 */
import React from "react";
import Carousel, { CarouselOptionType } from './components/Carousel';
import "./App.css";
// const { CarouselPanel } = Carousel;
const carouselOptions: Array<CarouselOptionType> = [{
	render() {
		return (
			<>
				<h1 className="slide-title">xPhone</h1>
				<p>Lots to love. Less to spend.</p>
				<p>Starting at $399.</p>
			</>
		);
	},
	className: 'iphone-slide',
}, {
	title: 'Tablet',
	className: 'tablet-slide',
	content: 'Just the right amount of everything.',
}, {
	className: 'airpods-slide',
	content: <>Buy a Tablet or xPhone for college.<br />Get arPods.</>,
}];

function App() {
	return (
		<div className="App">
			<Carousel
				duration={5000}
				options={carouselOptions}
			/>
			{/* <Carousel duration={5000}>
				<CarouselPanel style={{ background: '#000', color: '#fff' }}>
					<h1>xPhone</h1>
					<p>Lots to love. Less to spend.</p>
					<p>Starting at $399.</p>
				</CarouselPanel>
				<CarouselPanel>
					<h1>Tablet</h1>
					<p>Just the right amount of everything.</p>
				</CarouselPanel>
				<CarouselPanel style={{ background: '#999' }}>
					<p>
						Buy a Tablet or xPhone for college. Get arPods
					</p>
				</CarouselPanel>
			</Carousel> */}
		</div>
	);
}

export default App;
