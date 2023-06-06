import Foundation

class AppRunInfo {
    
    static let shared = AppRunInfo()
    
    func loadVendor() {
        initDI()
    }
    
    func loadData() {
        @Injection var dataProvider: DataProvider
        Task {
            await dataProvider.loadCardList()
        }
    }
    
    func initCache() {
        URLCache.shared.memoryCapacity = 10_000_000 // 10 MB memory space
        URLCache.shared.diskCapacity = 1_000_000_000 // 1GB disk cache space
    }
    
    private func initDI() {
        Resolver.shared.add(DataProvider.self, component: CardListDataProvider())
    }
}
