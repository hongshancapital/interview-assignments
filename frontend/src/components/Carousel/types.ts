
export type CarouselItemType = {
    /**
     * 幻灯片唯一标识
     */
    id: number
    /**
     * 幻灯片标题名称
     */
    name: string,
    /**
     * 幻灯片地址
     */
    image: string,
    /**
     * 幻灯片描述
     */
    describe?: string,
    /**
     * 背景颜色
     * 默认：#f1f1f1
     */
    background?: string,
    /**
     * 字体颜色
     * dark：字体颜色：#111、加载条颜色：#aaa，light：字体颜色：#fff，加载条颜色：#eee
     * 默认：dark
     */
    theme?: 'dark' | 'light'
}