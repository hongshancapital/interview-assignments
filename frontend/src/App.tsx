import React from 'react';
import './App.css';
import Carousel, { CarouselProps } from './components/Carousel';

const carouselProps: CarouselProps = {
	items: [
		<div className="carousel-item iphone-bg">
			<div className="text-wrapper" style={{ color: '#fff' }}>
				<div className="text-title">xPhone</div>
				<div className="text-body">Lots to Love. Less to spend.<br/>Starting at $399.</div>
			</div>
		</div>,
		<div className="carousel-item tablet-bg">
			<div className="text-wrapper" style={{ color: '#000' }}>
				<div className="text-title">Tablet</div>
				<div className="text-body">Just the Right amount of everything</div>
			</div>
		</div>,
		<div className="carousel-item airpods-bg">
			<div className="text-wrapper" style={{ color: '#000' }}>
				<div className="text-title">Buy a Tablet of XPhone for college <br/>Get arPods.</div>
			</div>
		</div>
	]
};

function App() {
	return <Carousel items={carouselProps.items}/>;
}

export default App;
