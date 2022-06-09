import React, { useState } from "react";
import "./App.css";
import Carousel from "./carousel/Carousel";

function App() {

    let pageArray: any[] = [ //配置页面
        <div className="pages page1" key={0}>这是第一页</div>,
        <div className="pages page2" key={1}>这是第二页</div>,
        <div className="pages page3" key={2}>这是第三页</div>,
    ];

    const addPage = () => { //新增页面
        pageArray.push(<div className="pages" key={pageArray.length}>这是新加页</div>)
    };

    const [config, setConfig] = useState({ //配置状态
        current: 0,
        showDuration: 3,
        switchDuration: 0.5,
        open: true
    });

    const handleChange = (event: any) => {//配置更新
        let target = event.target;
        let newConfig: any = Object.assign({}, config);
        newConfig[target.name] = target.value - 0;
        setConfig(newConfig);
    }

    const switchBtn = () => {//开关切换
        setConfig(Object.assign({}, config, {open: !config.open}));
    };

    return <div className="App">
        <div className="window">
            <Carousel current={config.current} showDuration={config.showDuration} switchDuration={config.switchDuration} open={config.open}>{pageArray}</Carousel>
        </div>
        <div className="control">
            <form>
                <label>
                </label>
                <label>
                    当前页:
                    <input type="number" name="current" step={1} min={0} max={pageArray.length} value={config.current} onChange={handleChange}/>
                </label>
                <label>
                    展示间隔:
                    <input type="number" name="showDuration" step={1} min={1} value={config.showDuration} onChange={handleChange}/>
                </label>
                <label>
                    切换间隔:
                    <input type="number" name="switchDuration" step={1} min={0} value={config.switchDuration} onChange={handleChange}/>
                </label>
            </form>
            <label><button onClick={addPage}>增加一页</button></label>
            <label><button onClick={switchBtn}>{'当前：' + (config.open?'开':'关')}</button></label>
        </div>
        <div className="text">
            <p>封装Carousel组件，针对容器尺寸自适应生成跑马灯效果，可以暂停，可以设置切换速度，页面数目与内容可自定义，可点击跳页</p>
        </div>
    </div>;
}

export default App;
