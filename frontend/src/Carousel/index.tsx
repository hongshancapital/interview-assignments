import './index.css';

export interface CarouselOptionType {
	className?: string,
    id: string | number,
	render?: () => React.ReactNode,
	title?: string | React.ReactNode,
	content?: string | React.ReactNode,
}

export interface CarouselProps {
	duration?: number,
	options?: Array<CarouselOptionType>
};

function Carousel({ duration = 10000, options = [] }: CarouselProps) {
    const carouselStyle: Object = {
        '--duration-time':  `${duration}ms`,
    };
    const delayUnitTime = duration / options.length;

    return (
        <div className="carousel-wrapper" style={carouselStyle}>
            <div className="inner-content">
                {
                    options?.map(({ id, className, render, title, content }) => {
                        const classNameQueue: Array<string> = ['carousel-item'];
                        className && classNameQueue.push(className);
                        let children:React.ReactNode = null;
                        if (render) {
                            children = render();
                        } else {
                            children = (
                                <>
                                    <h1 className="slide-title">{title}</h1>
                                    <p className="slide-content">{content}</p>
                                </>
                            );
                        }
                        return (
                            <div key={id} className={classNameQueue.join(' ')}>
                                {children}
                            </div>
                        );
                    })
                }
            </div>
            <div className="bottom-bar">
                {
                    options?.map(({ id }, i) => {
                        const stickStyle:Object = {
                            '--delay-time': `${delayUnitTime * i}ms`,
                        };
                        return (
                            <div
                                key={id}
                                className="stick"
                                style={stickStyle}
                            />
                        );
                    })
                }
            </div>
        </div>
    );
}

export default Carousel;
