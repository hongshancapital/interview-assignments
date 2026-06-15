const filterSet = new Set<string>()
export async function add(filter: string, value: string) {
  return await filterSet.add(filter + value)
}
export async function exists(filter: string, value: string) {
  return filterSet.has(filter + value)
}

export async function initializeBloomFilter() {
  return Promise.resolve()
}
