export interface IndicatorProps {
    /**
     * 自定义样式
     */
    style?: React.CSSProperties,
    /**
     * 激活自定义样式
     */
    activeStyle?: React.CSSProperties,
    /**
     * 传入类名
     */
    className?: string,
    /**
     * 类型
     */
    type?: 'line' | 'dot',

    /**
     * 当前选中
     */
    currentIndex?: number,
    /**
     * 当前进度
     */
    progress?: number,
    /**
     * 指示器个数
     * */
    count?: number,
    /**
     * 方向
     * */
    orientation?: 'vertical' | 'horizontal'
    /**
     * 点击第几个指示器
     * */
    onClick?: (currentIndex: number) => void

}
