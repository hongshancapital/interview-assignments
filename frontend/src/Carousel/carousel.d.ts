declare namespace Carousel {
    type subjectItem = {
        text: string,
        fontSize: string,
        fontWeight: number
    }
    type slideItem = {
        backgroundColor:string,
        color:string,
        subject:subjectItem[],
        goodsImgURL:string
    }
}