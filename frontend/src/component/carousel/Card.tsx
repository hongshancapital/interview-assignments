import React from 'react';

export interface CardData {
	imgUrl: string;
	titles: string[];
	desc?: string[];
	backgroundColor?: string;
	color?: string;
}

export const Card: React.FC<{data: CardData }> = ({data}) => {
	const { imgUrl, titles, desc = [], backgroundColor, color } = data;
	return (
		<div className='card' style={{ backgroundColor, color }}>
			<h1 className='card-title'>
				{titles.map((title, index) => <p key={`title-${index}`}>{title}</p>)}
			</h1>
			{desc.map((des, index) => <p key={`desc-${index}`}>{des}</p>)}
			<div className='card-img' style={{backgroundImage: `url(${imgUrl})`}}></div>
		</div>
	);
}
