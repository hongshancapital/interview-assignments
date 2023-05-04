import { ComponentType, useRef } from "react";
import { useContainerResize } from '../hooks';
import { SliderWrapperContent } from '../context/sliderWrapper';

export const widthWrapper = (Compoent: ComponentType) => (props:Record<string,any>) => {
    const { className, ...rest } = props || {};
    const wrapperRef = useRef<HTMLDivElement>(null);
    const { slideWidth } = useContainerResize(wrapperRef);
    
    return (
        <div  ref={wrapperRef} className={className ?? ''}>
            <SliderWrapperContent.Provider value={{slideWidth }}>
                <Compoent {...rest} />
            </SliderWrapperContent.Provider>
        </div>
    )
}