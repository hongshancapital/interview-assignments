export type SlideType = {
	id: number,
	title: string,
	text: string | '',
	description: string | '',
	img: string
}

export type ProgressBarType = {
	isCurrent: boolean,
	time: number
}

export type CarouselType = {
	delay: number,
	slides: SlideType[]
}