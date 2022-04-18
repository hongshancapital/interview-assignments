import {useEffect, useRef, useState} from "react";

function useTrack() {
    const listRef = useRef<HTMLDivElement>(null);
    const [winWidth, setWinWidth] = useState(0);

    useEffect(()=>{
        // 获取视口大小
        setWinWidth(listRef.current?.clientWidth || 0);
        function windowReset() {
            setWinWidth(listRef.current?.clientWidth || 0);
        }
        // 监听窗口resize。调整视口大小
        window.addEventListener('resize', windowReset);
        return () => {
            window.removeEventListener('resize', windowReset);
        }
    }, []);

    return {listRef, winWidth}
}

export default useTrack;