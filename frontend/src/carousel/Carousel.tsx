import React, {useEffect, useRef, useState} from "react";
import "./Carousel.css";
import Tick from "./tick/Tick";

function Carousel(props: any) {

    let timeout: any = useRef(null);//定时器变量
    const timeoutClear = () => {//dry 清楚定时器
        timeout.current && window.clearTimeout(timeout.current);
    };

    let open: any = useRef(props.open === false?false:true);//开关状态保存

    const [config, setConfig] = useState({//初始化配置状态
        current: props.current || 0,
        next: props.current || 0,
        showDuration: props.showDuration || 3,
        switchDuration: props.switchDuration || 0.5,
    });
    

    useEffect(() => {    //props变动检查
        if (open.current !== props.open) {//开关状态改变
            open.current = props.open;
            if (props.open === false) {
                timeoutClear();
            } else {
                setConfig(Object.assign({}, config));
            }
            return;
        }
        let obj = {//除开关外其余状态改变重置为第一页开始
            current: props.current || 0,
            next: props.current || 0,
            showDuration: props.showDuration || 2,
            switchDuration: props.switchDuration || 0.5,
        };
        setConfig(obj);
    }, [props]);


    useEffect(() => {//配置变动检查
        timeoutClear();
        if (!props.open) {return;}
        if (config.current === config.next) {//判断是等切页还是等展示，next用于控制切页
            timeout.current = window.setTimeout(() => {
                setConfig(Object.assign({}, config, {next: (config.current + 1 === props.children?.length?0:(config.current + 1))}));
            }, config.showDuration * 1000);
        } else {
            timeout.current = window.setTimeout(() => {
                setConfig(Object.assign({}, config, {current: (config.current + 1 === props.children?.length?0:(config.current + 1))}));
            }, config.switchDuration * 1000);
        }
    }, [config, props.open, props.children]);

    const tickClick = (index: number) => {//点击可以跳页
        timeoutClear();
        setConfig(Object.assign({}, config, {current: index, next: index}));
    };

    const tickList = Array.from({length: (props.children || []).length}, (v: any, index: number) => {//生成按钮
        return <Tick index={index} parent={config} click={() => tickClick(index)} key={index} open={props.open}/>;
    });

    const pageList = props.children?.map((v: any, index: number) => {//生成页队列
        return <div className="page" key={index}>{v}</div>;
    }) || [];

    return <div className="Carousel">
        <div className={'page-box ' + (props.open?'switching':'')} style={{transitionDuration: props.open?(config.switchDuration + 's'):'unset', left: config.next === 0?0:(config.next * -100 + '%')}}>{pageList}</div>
        <div className="tick-box">{tickList}</div>
    </div>;
}

export default Carousel;