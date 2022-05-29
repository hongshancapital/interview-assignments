import React, { createContext, useMemo } from 'react';
import { AnimationActions } from './useAnimator';

interface contextProperties {
    layer?: number;
    from?: number;
    to?: number;
    animations?: AnimationActions;
}
class initValue implements contextProperties {
    layer?: number;
    from?: number;
    to?: number;
    animations?: AnimationActions;
}

export const LayerContext = createContext(new initValue());

export const useLayerContext = (contextValue: contextProperties) => {
    const { from, to } = contextValue;
    return useMemo(() => {
        return (Child: React.ReactNode): JSX.Element => {
            return (
                <LayerContext.Provider value={contextValue}>
                    {Child}
                </LayerContext.Provider>
            );
        };
    }, [from, to]);
};