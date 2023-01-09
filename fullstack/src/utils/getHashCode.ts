import hash from 'object-hash'

export default (str: string) => hash.MD5(str)
