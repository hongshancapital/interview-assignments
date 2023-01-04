import Hashids from 'hashids'

export type Id = number
export type HashId = string

const base58alphabet = '123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz'

const hashids = new Hashids("short-url", 8, base58alphabet);

export const encode = (id: Id) : HashId => hashids.encode(id)
export const decode = (hashId: HashId) => hashids.decode(hashId)[0]