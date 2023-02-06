import './index.css';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import useViewSize from '../../hooks/ViewSize';

type CarouselProps = {
    speed: number;      // 切换时间，单位ms
    duration: number;   // 停留时间，单位ms
}

function Carousel(props: React.PropsWithChildren<CarouselProps>) {
    const {speed, duration, children} = props;
    if (duration < speed) {
        console.warn('停留时间过短');
    }
    const childrenCount = React.Children.count(children);
    const hasChildren = !!childrenCount;

    // 标记页码
    const [currentPage, setCurrentPage] = useState(0);

    // 获取可视区域宽高
    const viewSize = useViewSize();

    // 展示页相关：dom元素
    const pageRef = useRef(null);

    // 分页相关：dom元素，动画，事件
    const paginationRef = useRef(null);
    const progressStyleInit = useMemo(() => ({
        transitionDuration: '0ms',
        width: '0%'
    }), []);
    const progressStyle = useMemo(() => ({
        transitionDuration: `${duration}ms`,
        width: '100%'
    }), [duration]);
    const transitionendCb = useCallback((e: Event) => {
        const el = e.target as HTMLElement;
        el.style.transitionDuration = progressStyleInit.transitionDuration;
        el.style.width = progressStyleInit.width;
        setCurrentPage((currentPage) => {
            const page = currentPage + 1;
            return page === childrenCount ? 0 : page;
        });
    }, []);

    useEffect(() => {
        // 处理页面的宽高，位置
        if (pageRef.current && hasChildren) {
            const pageEl = pageRef.current as HTMLElement;
            pageEl.style.transitionDuration = `0ms`;
            pageEl.style.transform = `translate3d(${-1 * currentPage * viewSize.width}px, 0px, 0px)`;
            Array.from(pageEl.children).forEach(child => {
                const childEl = child as HTMLElement;
                childEl.style.width = `${viewSize.width}px`;
                childEl.style.height = `${viewSize.height}px`;
            });
        }
    }, [viewSize]);

    useEffect(() => {
        // 处理页面动画
        if (pageRef.current && hasChildren && childrenCount > 1) {
            const pageEl = pageRef.current as HTMLElement;
            pageEl.style.transitionDuration = `${speed}ms`;
            pageEl.style.transform = `translate3d(${-1 * currentPage * viewSize.width}px, 0px, 0px)`;
        }
        // 处理分页动画
        if (paginationRef.current && hasChildren && childrenCount > 1) {
            const progressEl = (paginationRef.current as HTMLElement).children[currentPage].children[0] as HTMLElement;
            progressEl.style.transitionDuration = progressStyle.transitionDuration;
            progressEl.style.width = progressStyle.width;
            progressEl.addEventListener('transitionend', transitionendCb);
        }

        return () => {
            if (paginationRef.current) {
                const progressEl = (paginationRef.current as HTMLElement).children[currentPage].children[0] as HTMLElement;
                progressEl.removeEventListener('transitionend', transitionendCb);
            }
        };
    }, [currentPage]);

    return !hasChildren ? <></> : (
        <div className='carousel'>
            <div className='carousel-wrapper' ref={pageRef}>{children}</div>
            {
                childrenCount > 1 ? (
                    <div className='pagination' ref={paginationRef}>
                        {
                            React.Children.map(children, (child, index) => (
                                <div className='indicator' key={index}>
                                    <span className='progress'/>
                                </div>
                            ))
                        }
                    </div>
                ) : <></>
            }
        </div>
    );
}

export default Carousel;