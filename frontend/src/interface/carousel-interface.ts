/** configuration of the carousel */
export interface CarouselConfig {
    /** lasting time for each slide */
    duration?: number
    /** transition time for each slide */
    transitionDuration?: number
    /** list of slide  */
    slides: SlideContent[]
}

/** the content of slide */
export interface SlideContent {
    /** backgorund color */
    bgColor: string
    /** descriptions */
    descContent: DescConfig[]
    /** pictures */
    pic: PicConfig
}

/** setting of the descriptions inside the slide */
export interface DescConfig {
    content: string
    type: string
}

/** configuration of the picture */
export interface PicConfig {
    url: string
}