import React, {useState, useEffect} from "react";
import "./Tick.css";

function Tick(props: any) {

    const [active, setActive] = useState(false);

    useEffect(() => {//配置改变检查
        if (props.open && props.index === props.parent.current) {//为当前按钮则激活动画
            setActive(true);
        } else {
            setActive(false);
        }
    }, [props.index, props.parent, props.open]);
    return <div className={`Tick ${active && props.open?'active':''}`} onClick={props.click}>
        <div className="tick-bar" style={{transitionDuration: active && props.open?(props.parent.showDuration + 's'):'unset'}}></div>
    </div>;
}

export default Tick;