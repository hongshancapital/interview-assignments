//
//  AppListViewModel.swift
//  AppList
//
//  Created by Xiaojian Duan on 2022/11/13.
//

import Foundation
import Combine

enum RefreshState {
    case hasMoreData
    case loading
    case noMoreData
}

class AppListViewModel : ObservableObject {
    
    @Published var loadSuccess = false
    @Published var state: RefreshState = .hasMoreData
    @Published private var model = AppListModel()
    
    var apps: [AppInfo] {
        model.infoArray
    }
    
    private var pageNum: Int = 1
    private var total: Int = 0
    private let countPerPage = 20  // 每页显示个数
    
    /// 从本地读取 mock.json 数据，模拟网络请求返回的数据
    func readFromDisk() async throws -> [AppInfo]? {
        guard let file = Bundle.main.url(forResource: "mock.json", withExtension: nil) else {
            fatalError("mock.json 不存在")
        }

        let data: Data
        do {
            data = try Data(contentsOf: file)
        } catch {
            fatalError("无法读取 mock.json")
        }

        let result: ResultInfo
        do {
            result = try JSONDecoder().decode(ResultInfo.self, from: data)
        } catch let error {
            print("\(error.localizedDescription)")
            fatalError("解析 mock.json 失败")
        }

        guard let arr = result.results, arr.count > 0 else {
            fatalError("解析 results 失败")
        }
        
        total = result.resultCount
        return arr
    }
    
    func refreshData() {
        state = .loading
        Task {
            do {
                guard let arr = try await readFromDisk() else {
                    DispatchQueue.main.async { [weak self] in
                        guard let self = self else {
                            return
                        }
                        self.updateStateIfNeeded()
                    }
                    return
                }
                let infos = Array(arr[0..<self.countPerPage])
                DispatchQueue.main.async { [weak self] in
                    guard let self = self else {
                        return
                    }
                    self.loadSuccess = true
                    self.model.removeAll()
                    self.model.addAppInfos(infos)
                    self.pageNum = 0
                    self.updateStateIfNeeded()
                }
            } catch {
                // 加载失败
            }
        }
    }
    
    func loadMoreData() {
        if self.state == .loading || self.state == .noMoreData {
            return
        }
        state = .loading
        Task {
            do {
                guard let arr = try await readFromDisk() else {
                    DispatchQueue.main.async { [weak self] in
                        guard let self = self else {
                            return
                        }
                        self.updateStateIfNeeded()
                    }
                    return
                }
                let n = self.countPerPage
                let start = n * (self.pageNum + 1)
                let end = arr.count > start + n ? start + n : arr.count
                if arr.count > start {
                    let infos = Array(arr[start..<end])
                    DispatchQueue.main.async { [weak self] in
                        guard let self = self else {
                            return
                        }
                        self.model.addAppInfos(infos)
                        self.pageNum += 1
                        self.updateStateIfNeeded()
                    }
                } else {
                    DispatchQueue.main.async { [weak self] in
                        guard let self = self else {
                            return
                        }
                        self.updateStateIfNeeded()
                    }
                }
            } catch {
                // 加载失败
            }
        }
    }
    
    func updateStateIfNeeded() {
        if model.infoArray.count >= self.total {
            self.state = .noMoreData
        } else {
            self.state = .hasMoreData
        }
    }
    
    func toggleLike(_ id: Int) {
        model.toggleLike(id)
    }
    
}
