import React from 'react';
import './App.css';
import Carousel from './Carousel';

function App() {
	return (
		<div className="App">
			<Carousel loop={false}>
				<div className="carousel-item-container" style={{ backgroundColor: '#111111', color: '#fff' }}>
					<div className="content">
						<div className="title">xPhone</div>
						<div className="text">
							<p>Lots to love.Less to spend.</p>
							<p>Starting at $399.</p>
						</div>
					</div>
					<div
						className="image"
						style={{
							backgroundImage: `url(${require('./assets/iphone.png')})`,
						}}
					></div>
				</div>
				<div className="carousel-item-container" style={{ backgroundColor: '#FAFAFA' }}>
					<div className="content">
						<div className="title">Tablet</div>
						<div className="text">
							<p>Just the right amount of everything.</p>
						</div>
					</div>
					<div
						className="image"
						style={{
							backgroundImage: `url(${require('./assets/tablet.png')})`,
						}}
					></div>
				</div>
				<div className="carousel-item-container" style={{ backgroundColor: '#F1F1F3' }}>
					<div className="content">
						<div className="title">Buy a Tablet or xPhone for college.</div>
						<div className="title">Get arPods.</div>
					</div>
					<div
						className="image"
						style={{
							backgroundImage: `url(${require('./assets/airpods.png')})`,
						}}
					></div>
				</div>
			</Carousel>
		</div>
	);
}

export default App;
