interface CarouselProps {
  children: JSX.Element[],
  interval?: number
}

interface CarouselItemProps {
  children: JSX.Element[],
  bgColor: string
}

interface CarouselNavProps {
  active: boolean
}

interface CarouselCaptionProps {
  title: string,
  subtitle: string,
  color: string
}