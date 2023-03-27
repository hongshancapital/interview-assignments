/** image data */
interface dataType {
    imgUrl: string;
    clickUrl?: string;
    altInfo?: string;
}

/** carousel type */
export interface CaruoselPropsType extends React.DetailedHTMLProps<React.HTMLAttributes<HTMLDivElement>, HTMLDivElement> {
    data: dataType[]; // 图片数据 
    playTiming?: number; // 图片停留时长
    duration?: number; // 滚动动画时间
    afterClickFn?: Function; // 图片点击事件回调
    isLinkJump?: boolean;  // 是否进行跳转
    itemStyle?: React.CSSProperties; // 自定义每一帧的样式
    itemImageClass?: string; //自定义图片样式
    container?: string; // 外层容器
    panigationProps?: PaginationPropsType; // 可以配置滚动标识的样式
}

export interface PaginationPropsType extends React.DetailedHTMLProps<React.HtmlHTMLAttributes<HTMLDivElement>, HTMLDivElement> {
    activeIndex?: number; //当前活动的帧id
    size?: number; // 多少帧
    etiming?: number; // 标注的动画时间
    onClickFn?: Function; // 标注的点击事件
    itemActivityStyle?: React.CSSProperties; // 自定义标注样式
    pageItemStyle?: React.CSSProperties; // 自定义滚动的样式
}


