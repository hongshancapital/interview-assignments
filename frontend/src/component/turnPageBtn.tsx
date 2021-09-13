import React from 'react';
import ReactDOM from 'react-dom';
import './turnPageBtn.css';
interface IProps {
    items: JSX.Element[]
}
interface iStatus {
    items: item[]
    endIndex: number
}
interface item {
    id: number,
    html: JSX.Element,
    isShow: boolean,
    isEnd: boolean,

}
export default class TurnPageBtn extends React.Component {
    state: iStatus;
    constructor(props: IProps) {
        super(props)
        this.state = {
            items: [],
            endIndex: -1
        }
        React.Children.map(this.props.children, (child, index) => { //page-content主体内容传入
            this.state.items.push({
                id: index,
                html: child as JSX.Element,
                isShow: false,
                isEnd: false,
            })
        })
        this.state.items[0].isShow = true;
    }
    /**
     * 切换动画
     *  index要切换进入的 页面编号
     * @param {number} index
     * @memberof TurnPageBtn
     */
    animationend(index: number) {
        index = (index) % this.state.items.length;
        if (this.state.items[index].isShow) {
            return
        }
        let items = [...this.state.items];
        let endIndex = -1
        for (let i = 0; i < items.length; i++) {
            let item = items[i];
            if (i == index) {
                item.isShow = true
            } else {
                if (item.isShow) {
                    endIndex = i
                }
                item.isShow = false
            }
        }
        this.setState({
            items,
            endIndex
        })
    }
    /**
     * 生成html
     *  page-content 由父传入主体内容
     *  turn-page 按钮区域
     * @returns
     * @memberof TurnPageBtn
     */
    render() {
        let contentHtmls = this.state.items.map((item, index) => {
            let classNames = ["page-content-item"];
            if (index == this.state.endIndex) {
                classNames.push("animation-left-end")
            } else if (item.isShow) {
                classNames.push("animation-left-start")
            }
            return <div key={item.id} className={classNames.join(' ')}>{item.html}</div>
        })
        let turnPageBtn = this.state.items.map((item, index) => {
            let classNames = item.isShow ? "turn-page-btn animation" : "turn-page-btn"
            return (<span key={item.id} className={classNames} onMouseEnter={() => { this.animationend(index) }} >
                <span onAnimationEnd={() => this.animationend(index + 1)}></span>
            </span>)
        });
        return (<div className='page'>
            <div className='page-content'>
                {contentHtmls}
            </div>
            <div className='turn-page'>
                {turnPageBtn}
            </div>
        </div>);
    }
}