namespace SCDT {
    interface ICarouselItemOption {
        slogan: JSX.Component,
        backgroundImage: BackgroundImage
    }

    interface ICarouselOption {
        items: Array<ICarouselItemOption>,
        duration: number,
        index?: number
    }

    interface IIndicatorOption {
        count: number,
        index?: number,
        duration: number,
        onNext: (index: number) => void
    }
}