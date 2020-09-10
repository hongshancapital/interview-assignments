import React, { useEffect, useState } from "react";

import { createUseStyles } from "utils";

const useStyles = createUseStyles(({ css }, { ms, count }) => ({
    container: css`
        position: relative;
        width: 100%;
        height: 100%;
        overflow: hidden;
    `,
    panel: css`
        display: flex;
        position: absolute;
        left: 0%;
        top: 0;
        width: ${100 * count}%;
        height: 100%;
        white-space: nowrap;
        transition: left 0.3s ease-in;
        .item {
            flex: 1;
            overflow: hidden;
        }
    `,
    dots: css`
        position: absolute;
        bottom: 20px;
        left: 0;
        width: 100%;
        text-align: center;
        .dot {
            position: relative;
            display: inline-block;
            width: 40px;
            height: 2px;
            border-radius: 2px;
            background: rgba(0,0,0,0.2);
            cursor: pointer;
            &:not(:first-child) {
                margin-left: 10px;
            }
            &::after {
                content: "";
                position: absolute;
                left: 0;
                top: 0;
                width: 0%;
                height: 100%;
                background: rgba(255,255,255,1);
            }
            &.active::after {
                transition: width ${ms}ms linear;
                width: 100%;
            }
        }
    `
}));

interface CarouselProps {
    autoplay?: boolean;
    ms?: number;
    children: React.ReactNode;
}

const Carousel: React.FC<CarouselProps> = ({ children, autoplay = true, ms = 3000 }) => {

    const [page, setPage] = useState(1);

    useEffect(() => {
        if (!autoplay) {
            return;
        }

        const timeout = setTimeout(() => {
            setPage(page => page % React.Children.count(children) + 1);
        }, ms);

        return () => clearTimeout(timeout);
    }, [children, autoplay, ms, page]);

    const classes = useStyles({ ms, count: React.Children.count(children) });

    return (
        <div className={classes.container} >
            <div className={classes.panel} style={{ left: 0 - (100 * (page - 1)) + "%" }}>
                {
                    React.Children.map(children, (node, index) => (
                        <div className="item" key={index}>
                            {node}
                        </div>
                    ))
                }
            </div>
            <div className={classes.dots}>
                {
                    React.Children.map(children, (item, index) => (
                        <div className={index === page - 1 ? "dot active" : "dot"} key={index} onClick={() => setPage(index + 1)}></div>
                    ))
                }
            </div>
        </div>
    );
}

export default Carousel;