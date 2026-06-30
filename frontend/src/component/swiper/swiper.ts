export interface dataType {
    banner: string,
    text: string,
    color: string
}
export interface Props {
    data: dataType[],
    timeObj: {
        playTime: number,//播放时间
        lazyTime: number,//等待时间
    }
}