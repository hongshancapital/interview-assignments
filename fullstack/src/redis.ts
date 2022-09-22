const cache = {} as any
const redis = {
    get: async (key: string) => cache[key],
    set: async (key: string, value: string) => {cache[key] = value}
}

export { redis }