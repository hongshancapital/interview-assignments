export interface IPathRoute {
    methods: string[];
}

export interface Ipath{
    [route: string]: IPathRoute
}