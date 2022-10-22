

export type HtmlsJsonSchema = {
    contentType: keyof HTMLElementTagNameMap,
    className?: string,
    contentList?: Array<string> | []
}


export type CarouselList = Array<{
    bgUrl: string,
    data: Array<HtmlsJsonSchema>
}>

