import React from 'react';
import './CarouselItem.css';

interface Props {
	imageUrl: string;
	itemStyle: React.CSSProperties;
	titles?: string[];
	texts?: string[];
}

export default function CarouselItem(props: Props) {
	const { imageUrl, itemStyle, titles, texts } = props;
	return (
		<div className="carousel-item-container" style={itemStyle}>
			<div className="content">
				{titles &&
					titles.map((title, index) => (
						<div className="title" key={index}>
							{title}
						</div>
					))}
				{texts && (
					<div className="text">
						{texts.map((text, index) => (
							<p key={index}>{text}</p>
						))}
					</div>
				)}
			</div>
			<div
				className="image"
				style={{
					backgroundImage: `url(${imageUrl})`,
				}}
			></div>
		</div>
	);
}
