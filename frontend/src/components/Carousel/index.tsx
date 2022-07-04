import React from 'react';

import './index.css';

interface CarouselProps {
    items: { src: any; title: string; href: string; descs?: string[]; textColor: string; }[];
    speed: number;
}

interface CarouselState {
    index: number;           // 当前轮播项索引
    offset: number;          // 轮播组的偏移量
    itemWidth: number;       // 轮播项宽度
}

class Carousel extends React.Component<CarouselProps, CarouselState> {
    static defaultProps = {
        items: [],
        speed: 4000,
    };

    $root: any;
    $items: any[] = [];
    player: any;

    constructor(props: CarouselProps) {
        super(props);
        this.state = {
            index: 0,           // 当前轮播项索引
            offset: 0,          // swipe组的偏移量
            itemWidth: 0,       // 轮播项宽度
        };
    }

    componentDidMount() {
        if (this.$root) {
            this.setState({
                itemWidth: this.$root.getBoundingClientRect().width,
            }, () => {
                this.autoplay();
            })
        };
    }

    componentWillUnmount() {
        clearInterval(this.player);
    }

    setRootRef = (ref: any) => {
        this.$root = ref;
    }

    // 向左/右方向切换 indexOffset 个 item
    next(indexOffset: number) {
        const { items } = this.props;
        const { index, itemWidth } = this.state;

        const total = items.length;
        let curtIndex = index + indexOffset;
        // 修正
        if (curtIndex < 0) {
            curtIndex += total;
        } else if (curtIndex > total - 1) {
            curtIndex -= total;
        }

        this.setState({
            index: curtIndex,
            offset: -curtIndex * itemWidth,
        });
    }

    autoplay() {
        const { speed } = this.props;
        this.player = setInterval(() => {
            this.next(1);
        }, speed);
    }

    changeIndex = (i: number) => {
        const { index } = this.state;
        if (i === index) return;
        this.next(i - index);
    }

    render() {
        const { items, speed } = this.props;
        const { index, offset } = this.state;

        const duration = Math.min(speed, 500);

        const listStyle = {
            transform: `translateX(${offset}px)`,
            transitionDuration: `${duration}ms`,
        };

        return (
            <div className="m-carousel" ref={this.setRootRef}>
                <ul className="img-list" style={listStyle}>
                    {items.map((item, i: number) => (
                        <li className="img-item" key={i}>
                            <div className="img-content" style={{ color: item.textColor }}>
                                {
                                    item.title && (<a className="img-link" href={item.href} target="_blank" rel="noreferrer">{item.title}</a>)
                                }
                                {
                                    (item.descs || []).map((text) => (
                                        <div className="img-desc">{text}</div>
                                    ))
                                }
                            </div>
                            <img src={item.src} />
                        </li>
                    ))}
                </ul>
                <ul className="process-list">
                    {items.map((item, i: number) => (
                        <li className="process-item" key={i} onClick={() => this.changeIndex(i)}>
                            <div className="process-inner" style={{
                                width: index === i ? '100%' : '0',
                                transitionDuration: index === i ? `${speed}ms` : '0ms',
                            }} />
                        </li>
                    ))}
                </ul>
            </div>
        )
    }
}

export default Carousel;