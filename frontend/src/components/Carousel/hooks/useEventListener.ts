import { useEffect } from 'react';

const useEventListener = (
    type: string, 
    listener: EventListener, 
    options: AddEventListenerOptions & { target: any }
) => {
    const { target, ...listenerOptions } = options;
    
    useEffect(() => {
        target?.addEventListener(type, listener, listenerOptions);
        return () => {
            target?.removeEventListener(type, listener, listenerOptions);
        };
    }, [target]);
}

export default useEventListener;