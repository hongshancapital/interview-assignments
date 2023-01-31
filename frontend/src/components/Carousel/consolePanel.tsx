import useControlled from "@/hooks/useControlled";
import "./consolePanel.css"

const ConsolePanel: ReactFC<{
    autoplay: boolean;
    autoplaySet: (autoplay: boolean) => void;
    duration: number;
    durationSet: (duration: number) => void;
    currentIndex?: number;
}> = (props) => {
    const { autoplay, autoplaySet, duration, durationSet, currentIndex } = props
    useControlled(autoplay, autoplaySet)
    useControlled(duration, durationSet)
    return <div className="console-panel">
        <table>
            <tbody>
            <tr>
                <td>自动轮播</td> 
                <td>
                     <button onClick={() => { autoplaySet(!autoplay) }}>{autoplay ? '开' : '关'} </button> 
                </td>
            </tr>
            <tr>
                <td>自动轮播间隔</td>
                <td>
                    <input type="text" value={duration} onChange={value => durationSet(parseInt(value.target.value || '0'))} />
                </td>
            </tr>
                {currentIndex &&<tr>
                <td>当前位置</td> 
                <td>{currentIndex}</td>
            </tr>}
            </tbody>
        </table>
    </div>
}
export default ConsolePanel