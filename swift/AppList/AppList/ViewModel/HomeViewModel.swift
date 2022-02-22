//
//  HomeViewModel.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import Foundation
import Combine
import Reachability

class HomeViewModel: ObservableObject {
    
    @Published private(set) var listDatas: [ListItem] = []
    
    var allCancellables = [AnyCancellable]()
    var page: Page?
    var loadingStatus: LoadingStatus = .loading
    
    var noMoreData: Bool { page != nil && page!.page == page!.totalPage + 1 }
    
    private let reachability = try! Reachability()

    init() {
        notifyNetwork()
    }
    
    func getData() {
        // 网络不可用
        if reachability.connection == .unavailable {
            self.loadingStatus = .idle
            return
        }
        
        var isRefreshing = false
        
        if page == nil {
            isRefreshing = true
            page = Page(total: 10)
        }
        
        if noMoreData { return }
        
        if self.loadingStatus == .idle {
            self.loadingStatus = .loading
        }
        
        Just(DataLoader().getData(of: page!))
            .sink { (total: Int, list: [ListItem]) in
                // 模拟网络延迟
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                    self.page!.total = total
                    if isRefreshing {
                        self.listDatas.removeAll()
                    }
                    self.listDatas.append(contentsOf: list)
                    self.page!.page = self.page!.nextPage
                    self.loadingStatus = .loaded
                }
            }
            .store(in: &allCancellables)
    }
    
    func reload() {
        page = nil
        getData()
    }
    
    func toggleFavor(for item: ListItem) {
        if let index = listDatas.firstIndex(where: { $0.id == item.id }) {
            toggleFavorInJSON(for: item) {
                let favor = self.listDatas[index].favored
                if favor == 1 {
                    self.listDatas[index].favored = 0
                }else {
                    self.listDatas[index].favored = 1
                }
            }
        }
    }
    
    // 模拟点赞数据更新
    private func toggleFavorInJSON(for item: ListItem, completion: @escaping () -> Void) {
        var json = DataLoader().getAllJSON().json
        
        for (index, listItem) in json.enumerated() {
            if (listItem["trackId"] as! Int) == item.id {
                let favor = (json[index]["favored"] as? Int) ?? 0
                if favor == 1 {
                    json[index]["favored"] = 0
                }else {
                    json[index]["favored"] = 1
                }
                break
            }
        }
        
        guard let docPath = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first else { return }
        let filePath = docPath.appending("/data.json")
        
        if let data = json.data,
           let url = URL(string: filePath) {
            do {
                let writeHandler = try FileHandle(forWritingTo: url)
                writeHandler.write(data)
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
                    completion()
                }
            } catch {
                print("=== Write Disk Error ===: \(error)")
            }
        }
    }
    
    private func notifyNetwork() {
        reachability.whenReachable = { reachability in
            if reachability.connection == .unavailable {
                self.loadingStatus = .idle
            } else {
                if self.loadingStatus == .idle {
                    self.loadingStatus = .loading
                }
            }
        }
        do {
            try reachability.startNotifier()
        } catch {
            print("Unable to start notifier")
        }
    }
    
    deinit {
        reachability.stopNotifier()
    }
    
    enum LoadingStatus {
        case idle
        case loading
        case loaded
    }
}
