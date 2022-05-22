// configuration which can modify process style of indicator
export interface indicatorBackgroundColor {
    backgroundColor: string;
    processColor: string;
}

/**
 * configuration which should get from configuration file of project and can modify carousl item
 * titles: carousel item title, each item occupy a line. Here can be empty.
 * descriptions: carousel item descriiption, each item occupy a line. Here can be empty.
 * icon: carousel item icon shown in bottom. 
 * backgroundColor: carousel item background.
 * indicator: indicator configuration.
 */
export interface carouselItem {
    titles: string[];
    descriptions: string[];
    icon?: string;
    fontColor?: string;
    backgroundColor?: string;
    indicator?: indicatorBackgroundColor | null;
}