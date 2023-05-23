//
//  AppsViewModel.swift
//  Demo
//
//  Created by 方林威 on 2023/1/29.
//

import Foundation

class AppsViewModel: ObservableObject {
    
    /// 数据加载状态
    @Published private(set) var loadingState: LoadingState = .loading
    /// 数据源
    @Published var items: [Item] = []
    /// 是否加载更多
    @Published private(set) var noMore: Bool = false
    /// 数据
    private var datas: [[Item]] = []
    
    /// 加载数据
    func load() {
        loadingState = .loading
        do {
            guard let url = Bundle.main.url(forResource: "mock", withExtension: "json") else {
                return
            }
            let data = try Data(contentsOf: url)
            let model = try JSONDecoder().decode(Model.self, from: data)
            datas = model.results.map { .init(icon: $0.icon, name: $0.name, desc: $0.desc, isLike: false) }.grouped(by: 20)
            items = datas.first ?? []
            loadingState = .success
            
        } catch {
            loadingState = .failure(error)
        }
    }
    
    /// 加载更多
    func loadMoreData(page: Int) async throws {
        // 模拟网络延迟
        try await Task.sleep(nanoseconds: UInt64(2) * NSEC_PER_SEC)
        DispatchQueue.main.async {
            self.items += self.datas[page]
            self.noMore = page >= self.datas.count - 1
        }
    }
}

private struct Model: Codable {
    let resultCount: Int
    let results: [App]
    
    struct App: Codable {
        
        var id: String { name }
        
        let icon: String
        let name: String
        let desc: String
        
        enum CodingKeys: String, CodingKey {
            case icon       = "artworkUrl100"
            case name       = "trackName"
            case desc       = "description"
        }
    }
}

struct Item: Identifiable {
    
    var id: String { name }
    
    let icon: String
    let name: String
    let desc: String
    var isLike: Bool
}

enum LoadingState {
    case loading
    case success
    case failure(Swift.Error)
}
