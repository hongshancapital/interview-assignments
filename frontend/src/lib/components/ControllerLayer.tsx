import React, { useRef, useState, useEffect, useContext, createContext, useMemo, useReducer } from 'react';
import { userLayerChildren } from '../hooks/useLayerChildren';
import { CarouselContext } from '../hooks/useCarouselContext';
import { useLayerContext } from '../hooks/useLayerContext';
import { LayerProps } from './Layer';
import { CarouselOptions } from './Carousel';

interface ControllerLayerProp extends LayerProps {
    options: CarouselOptions;
    // watchSideClick: number;
    // sideClickAt: React.MutableRefObject<string>;
}

export const ControllerContext = createContext({});

export default function ControllerLayer({ layer, children, className, options/* , watchSideClick, sideClickAt  */ }: ControllerLayerProp) {
    const selfRef = useRef(null);
    const [slideAt, setSlideAt] = useState(0);
    const lastAt = useRef<number>(0);
    const length = useRef<number>(0);
    const reversed = useRef(false);
    const timeId = useRef<any>();
    const autoRef = useRef(false);
    const [walkTo, setWalkTo] = useState(0);
    const [jumpTo, setJumpTo] = useState(0);
    const childNodes = userLayerChildren(children);
    const carouselContext = useContext(CarouselContext);
    const contextRender = useLayerContext({});
    const clickSide = useRef('');
    const [sideClickCount, setSideClick] = useReducer((state) => {
        return state + 1;
    }, 0);

    const forward = () => {
        carouselContext.forEach?.((api) => {
            api.toward();
        });
        reversed.current = false;
    };
    const backward = () => {
        carouselContext.forEach?.((api) => {
            api.toward();
            api.reverse();
        });
        reversed.current = true;
    };

    const toward = () => {
        if (clickSide.current === 'left') {
            if (slideAt === 0) {
                forward();
            } else {
                backward();
            }
        } else {
            if (slideAt === length.current) {
                backward();
            } else {
                forward();
            }
        }
    };

    const goto = (nextIndex: number) => {
        lastAt.current = slideAt;
        let goto = nextIndex < 0 ? length.current : nextIndex > length.current ? 0 : nextIndex;
        carouselContext.forEach?.((api) => {
            api.setSlideAt(goto);
        });
        setSlideAt(goto);
    };

    const autoRun = () => {
        clickSide.current = '';
        autoRef.current = true;
        clearTimeout(timeId.current);
        setWalkTo(slideAt + 1);
    };

    useEffect(() => {
        autoRun();
    }, [slideAt]);

    useEffect(() => {
        clearTimeout(timeId.current);
        timeId.current = setTimeout(() => {
            toward();
            goto(walkTo);
            autoRun();
        }, options.stayTime);
    }, [walkTo]);

    useEffect(() => {
        autoRef.current = false;
        clearTimeout(timeId.current);
        toward();
        goto(jumpTo);
        autoRun();
    }, [jumpTo]);

    useEffect(() => {
        if (selfRef.current) {
            length.current = (carouselContext.totalLength?.current ?? 1) - 1;
            autoRun();
        }
    }, []);

    const onSideClick = (side: string) => {
        clickSide.current = side;
        setJumpTo(slideAt + (side === 'left' ? -1 : 1));
        setSideClick();
    };

    const contextValue = {
        slideAt,
        onSideClick,
        length: length,
        options,
        sideClickCount
    };

    return (
        contextRender(<>{
            <div className={className + ' carousel-layer'} data-layer={layer} ref={selfRef}>
                <ControllerContext.Provider value={contextValue}>
                    {childNodes}
                </ControllerContext.Provider>
            </div>
        }</>)
    );
}
