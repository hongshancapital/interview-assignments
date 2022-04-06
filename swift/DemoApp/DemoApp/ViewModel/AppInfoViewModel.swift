//
//  AppInfoViewModel.swift
//  DemoApp
//
//  Created by xiongmin on 2022/4/2.
//

import Foundation
import Combine

struct AppInfoListResponse: Decodable {
    let resultCount: Int
    let results: [AppInfoModel]
}

class AppInfoViewModel: ObservableObject {
    @Published var appInfoList: [AppInfoModel] = []
    @Published var hasMore: Bool = true
    
    let path = "https://itunes.apple.com/search"
    
    private var pageSize: Int = 10 // 每页数量
    private var limit: Int = 50 // 单次请求总数量
    private var response: AppInfoListResponse?
    
    func refresh(pageSize: Int = 10, limit: Int = 50) async throws {
        guard pageSize > 0, limit > 0 else {
            let msg = "pageSize or limit should not less than or equal to 0"
            print("invalid parmas \(msg)")
            let error = NSError(domain: "com.app.app_info", code: -999, userInfo: ["msg": msg])
            throw error
        }
        self.pageSize = pageSize
        self.limit = limit
        let parmas: [String: AnyHashable] = ["entity": "software", "limit": limit, "term": "chat"]
        self.response = try await Network.shared.fetch(path, paramas: parmas)
        await MainActor.run {
            // 主线程回调UI进行更新
            guard let response = self.response else { return }
            self.appInfoList.removeAll()
            if response.resultCount > pageSize {
                self.hasMore = true
                self.appInfoList.append(contentsOf: response.results.prefix(pageSize))
            } else {
                self.hasMore = false
                self.appInfoList.append(contentsOf: response.results)
            }
        }
    }
    
    func loadMore() async throws {
        guard let response = self.response else { return }
        let currentCount = appInfoList.count
        await MainActor.run { 
            if currentCount < response.resultCount  {
                // 获取的数据数量还够，那么增加一页数量
                if currentCount + self.pageSize < response.resultCount {
                    // 增加一页数量后如果还小于总数量，那么可以继续loadMore
                    self.appInfoList.append(contentsOf: response.results[currentCount..<(currentCount + self.pageSize)])
                    self.hasMore = true
                } else {
                    self.appInfoList.append(contentsOf: response.results[currentCount..<response.resultCount])
                    self.hasMore = false
                }
            } else {
                // 需要拿新的数据， 接口没有获取后续数据的机制，这里就直接置为没更多数据
                self.hasMore = false
            }
        }
    }
}
