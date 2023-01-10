import * as React from "react"

export const useMotion: (childrenRef: React.MutableRefObject<HTMLElement[]>, delay: number) => 
    [React.CSSProperties | undefined, number | undefined, React.Dispatch<React.SetStateAction<number | undefined>>] = (childrenRef, delay) => {
    const [activeIndex, setActiveIndex] = React.useState<number>();
    const [tranStyle, setTranStyle] = React.useState<React.CSSProperties>();

    React.useEffect(()=> {
        setActiveIndex(0);
      }, [setActiveIndex])

    React.useEffect(() => {
        const children = childrenRef.current;
        if (activeIndex === undefined) {
            return;
        }

        const timer = setTimeout(() => {
            const newActiveIndex = (activeIndex + 1) % children.length;

            if (children[newActiveIndex]) {
                const activeEle = children[newActiveIndex];
                const first = activeEle.getBoundingClientRect();
                activeEle.style.position = "absolute";
                activeEle.style.left = "0";
                const last = activeEle.getBoundingClientRect();
                const deltaX = last.left - first.left;

                activeEle.style.position = "";
                activeEle.style.left = "";
                setTranStyle({ transform: `translateX(${deltaX}px)` });
            }

            setActiveIndex(newActiveIndex);
        }, delay);

        return () => {
            clearTimeout(timer);
        };
    }, [activeIndex, setActiveIndex, setTranStyle, delay, childrenRef]);

    return [tranStyle, activeIndex, setActiveIndex];
}
