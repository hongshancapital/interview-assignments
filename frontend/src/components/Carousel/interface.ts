/**
 * 指示器显示位置参数
 * @param top
 * @param bottom
 * @param left
 * @param right
 * @param showLocation
 */
export interface IndicatorPositionProps {
    /**
     * 顶部距离
     */
    top?: number,
    /**
     * 顶部距离
     */
    bottom?: number,
    /**
     * 左侧距离
     */
    left?: number,
    /**
     * 右侧距离
     */
    right?: number,

    /**
     * 显示方位
     */
    showLocation: 'top' | 'left' | 'bottom' | 'right'
}
