import React, {useState, useEffect, useRef} from 'react';
import './index.scss';

export type Props = {
    frameData: {
        title: React.ReactNode,
        describe?: React.ReactNode,
        url: string,
        frameCls?: string
    }[]
}

const FRAME_INDEX_WIDTH = 30;
const FRAME_INDEX_MARGIN_RIGHT = 10;
const FRAME_INDEX_STYLE = {
    width: `${FRAME_INDEX_WIDTH}px`,
    marginRight: `${FRAME_INDEX_MARGIN_RIGHT}px`
};
const INIT_TRANSLATE = `-${FRAME_INDEX_WIDTH}px`;
function Carousel(props: Props) {

    const {frameData = []} = props;
    const framesRef = useRef<HTMLDivElement>(null);
    const [frameIndex, setFrameIndex] = useState(0);
    const [frameWidth, setFrameWidth] = useState(0);

    const TransitionProgressEl = useTransitionProgressEl({
        frameIndex,
        framesCount: frameData.length,
        onTransitionEnd(nextIndex) {
            setFrameIndex(nextIndex);
        }
    });

    useEffect(() => {
        const resizeObserver = new ResizeObserver((entries) => {
            if (entries.length) {
                setFrameWidth(entries[0].target.clientWidth);
            }

        });
       if (framesRef.current)
            resizeObserver.observe(framesRef.current);
        return () => {
            resizeObserver.disconnect()
        };
    }, [])


    return (
        <div className="carousel-wrap">
            <div
                ref={framesRef}
                className="carousel-frames"
                style={{
                    transform: `translateX(-${frameIndex * frameWidth}px)`
                }}
            >
                {
                    frameData.map(({title, describe, url, frameCls = ''}) => (
                        <div className={`carousel-frame ${frameCls}`.trim()}>
                            <div className="title">{title}</div>
                            <div className="text">{describe}</div>
                            <div className="img-wrap">
                                <img src={url} alt=""/>
                            </div>
                        </div>
                    ))
                }
            </div>
            <div className="carousel-indexs">
                {
                    frameData.map(() => (
                        <div className="frame-index" style={FRAME_INDEX_STYLE}/>
                    ))
                }
                {TransitionProgressEl}
            </div>
        </div>
    )
}

export type ProgressProps = {
    frameIndex: number,
    framesCount: number,
    onTransitionEnd?: (frameIndex: number) => void
}
function useTransitionProgressEl(props: ProgressProps) {
    const [frameActiveLeft, setFrameActiveLeft] = useState(0);
    const [frameActiveTranslate, setFrameActiveTranslate] = useState(INIT_TRANSLATE);
    const {framesCount, frameIndex, onTransitionEnd} = props;
    const onFrameTransitionEnd = () => {
        const nextIndex = framesCount > frameIndex + 1 ? frameIndex + 1 : 0;
        const isReset = nextIndex === 0;
        setFrameActiveTranslate(INIT_TRANSLATE)
        setFrameActiveLeft(isReset ? 0 :
            (FRAME_INDEX_WIDTH + FRAME_INDEX_MARGIN_RIGHT) * nextIndex);
        if (onTransitionEnd) {
            onTransitionEnd(nextIndex);
        }
    }

    useEffect(() => {
        requestAnimationFrame(() => {
            setFrameActiveTranslate('0px')
        })
    }, [frameIndex])

    return (
        <div
            className="frame-index-active"
            style={{
                width: `${FRAME_INDEX_WIDTH}px`,
                left: `${frameActiveLeft}px`
            }}
        >
            <div
                style={{
                    transition: frameActiveTranslate === INIT_TRANSLATE ? 'none' : '',
                    transform: `translateX(${frameActiveTranslate})`
                }}
                onTransitionEnd={framesCount > 1 ? onFrameTransitionEnd : ()=>{}}
            />
        </div>

    );
}

export default Carousel;