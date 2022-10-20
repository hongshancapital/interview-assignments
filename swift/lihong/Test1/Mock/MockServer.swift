//
//  MockServer.swift
//  Test1
//
//  Created by Hong Li on 2022/10/19.
//

import Foundation

///
/// Provide mock data for Demo App
/// Could be replaced easily in Production
///
class MockServer: BackendProtocol {
    var reloadDelay: Double = 1.0
    var loadMoreDelay: Double = 2.0
    var items: [AppRawItem] = []

    ///
    /// fileName - Test file name
    /// suffix - Test file suffix
    /// reloadDelay - Expected delay when reload API is called
    /// loadMoreDelay - Expected delay when loadMore API is called
    /// maxCount - Maximum number of AppRawItem to load from test file
    ///
    init(_ fileName: String, _ suffix: String, _ reloadDelay: Double, _ loadMoreDelay: Double, _ maxCount: Int = 50) {
        self.reloadDelay = reloadDelay
        self.loadMoreDelay = loadMoreDelay

        do {
            if let file = Bundle.main.url(forResource: fileName, withExtension: suffix) {
                let fileStr = try String(contentsOf: file, encoding: .utf8)
                if let jsonData = fileStr.data(using: .utf8) {
                    let decoder = JSONDecoder()

                    let jsonItems = try decoder.decode(AppRawItems.self, from: jsonData)

                    if jsonItems.results.count > maxCount {
                        items = Array(jsonItems.results[0..<maxCount])
                    } else {
                        items = jsonItems.results
                    }
                }
            }
        } catch {
            print(error.localizedDescription)
        }
    }

    func getApps(_ page: Int, _ count: Int) async -> [AppRawItem] {
        let delay = page == 1 ? reloadDelay : loadMoreDelay

        do {
            try await Task.sleep(nanoseconds: UInt64(delay * Double(NSEC_PER_SEC)))
        } catch {
            print(error.localizedDescription)
        }

        guard page > 0 else { return [] }

        guard items.count > (page - 1) * count else { return [] }

        let startIndex = (page - 1) * count
        let endIndex = min(items.count, page * count)  // 可以返回不足一页count数量的结果
        return Array(items[startIndex..<endIndex])
    }
}
