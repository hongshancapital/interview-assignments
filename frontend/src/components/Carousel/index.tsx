import React, {useEffect, useMemo, useRef, useState, useImperativeHandle} from 'react'

import './index.scss'

import Indicators from "../Indicators";

export type CarouselRefs = {
	goTo: (slide: number) => void,
	next: () => void,
	prev: () => void
 }

type CarouselProps = {
//	是否自动切换
	autoPlay?: Boolean,
//	多少毫秒切换一次
	interval?: number,
	children: React.ReactNode
}

const Carousel = React.forwardRef<CarouselRefs, CarouselProps>((props, ref) => {
	const [current, setCurrent] = useState(0);
	const [width, setWidth] = useState(0);
	const [scrollStyle, setScrollStyle] = useState({})
	const {autoPlay = true, interval = 3000} = props;
	const count = useMemo(() => React.Children.count(props.children), [props.children]);
	const container = useRef<HTMLDivElement>(null)
	const scrollRef = useRef<HTMLDivElement>(null);
	let timer: ReturnType <typeof setTimeout> | null= null;

	const changeWidth = () => {
		const size = scrollRef.current?.getBoundingClientRect();
		size && setWidth(size.width)
	}

	const goTo = (index: number) => {
		if(count <= 1) return;
		if(index < 0 || index > count){
			console.log("超出范围");
			setCurrent(0)
			return
		}
		setCurrent(index);
		setScrollStyle({
			transform: `translateX(-${index * 100}%)`
		})
	}

	const prev = () => {
		goTo(current - 1)
	}

	const next = () => {
		goTo(current + 1 > count - 1 ? 0 : current + 1)
	}

	const play = () => {
		if(!autoPlay || (count <= 1)) return
		timer = setTimeout(() => {
			next()
		}, interval)
	}

	const pause = () => {
		timer && clearTimeout(timer);
		timer = null;
	}

	useEffect(()  => {
		if(scrollRef){
			changeWidth()
		}
		if(width) play()
		return () => pause()
	}, [scrollRef, width, current])

	useImperativeHandle(ref, ()=> ({
		goTo,
		prev,
		next,
	}))

	return (
			<div className="carousel-container" ref={container}>
				<div className="carousel-scroll" ref={scrollRef} style={scrollStyle}>
					{
						React.Children.map(props.children, (child,index) => {
							if(!React.isValidElement(child)) return null
							return child
						})
					}
				</div>
				<Indicators
						autoPlay={autoPlay}
						interval={interval}
						current={current}
						count={count}
						handleClick={goTo}
				></Indicators>
			</div>
	)
});

export default Carousel