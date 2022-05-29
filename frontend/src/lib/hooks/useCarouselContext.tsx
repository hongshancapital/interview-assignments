import React, { createContext, useMemo } from 'react';

export interface LayerApi { setSlideAt: any; reverse: any; toward: any; }

export interface contextProperties {
    forEach?: (callback: (api: LayerApi, layer: number) => void) => void;
    find?: (index: number) => ({ layer: number, api: LayerApi; });
    expose?: (layer: number, api: LayerApi, length: number) => void;
    totalLength?: React.MutableRefObject<number>;
    setTotalLength?: (length: number) => void;
}
class initValue implements contextProperties {
    forEach?: (callback: (api: LayerApi, layer: number) => void) => void;
    find?: (index: number) => ({ layer: number, api: LayerApi; });
    expose?: (layer: number, api: LayerApi, length: number) => void;
    totalLength?: React.MutableRefObject<number>;
    setTotalLength?: (length: number) => void;
}

export const CarouselContext = createContext(new initValue());

export const useCarouselContext = (contextValue: contextProperties) => {
    return useMemo(() => {
        return (Child: React.ReactNode): JSX.Element => {
            return (
                <CarouselContext.Provider value={contextValue}>
                    {Child}
                </CarouselContext.Provider>
            );
        };
    }, []);
};