import { useEffect } from 'react';

const useResize = (listener: () => void) => {
    useEffect(() => {
        window.addEventListener('resize', listener);
        return () => {
            window.removeEventListener('resize', listener);
        };
    }, [listener])
}

export default useResize;