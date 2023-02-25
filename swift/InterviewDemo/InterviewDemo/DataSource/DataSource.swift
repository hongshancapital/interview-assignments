//
//  Model.swift
//  InterviewDemo
//
//  Created by Peng Shu on 2023/2/24.
//

import Foundation
import Combine

let pageCount = 10
let apiURL = URL(string: "https://itunes.apple.com/search?entity=software&limit=\(pageCount)&term=chat")!

class DataSource: ObservableObject {
    @Published var items = [Item]()
    var pageRemain = 3 // 模拟3次load next
    
    @Published var isLoadingPage = false
    
    var hasMoreData: Bool {
        return pageRemain > 0
    }
    
    func reset() {
        items = []
        pageRemain = 3
    }
    
    // MARK: 使用 Combine 模式
    func loadNextUsingCombine(currentItem: Item?) {
        guard hasMoreData else {
            return
        }
        
        if let currentItem = currentItem {
            guard items.last == currentItem else {
                return
            }
        }
        
        guard !isLoadingPage else {
            return
        }
        
        isLoadingPage = true
        
        URLSession.shared.dataTaskPublisher(for: apiURL)
        #if DEBUG
        // 暂停1秒. 方便查看异步效果.
            .delay(for: 1, scheduler: RunLoop.main)
        #endif
            .map(\.data)
            .decode(type: ItemWrapper.self, decoder: JSONDecoder())
            .receive(on: RunLoop.main)
            .handleEvents(receiveOutput: { response in
                self.isLoadingPage = false
                self.pageRemain -= 1
            })
            .map({ response in
                return self.items + response.results
            })
            .catch({ _ in Just(self.items) })
            .assign(to: &$items)
    }
    
    // MARK: 直接使用 Swift Concurrency 模式, 比较直观简单.
    func loadNextUsingConcurrency(currentItem: Item?) async {
        guard hasMoreData else {
            return
        }
        
        if let currentItem = currentItem {
            guard items.last == currentItem else {
                return
            }
        }
        
        await MainActor.run(body: {
            isLoadingPage = true
        })
        
        #if DEBUG
        // 暂停1秒. 方便查看异步效果.
        try? await Task.sleep(nanoseconds: 1 * NSEC_PER_SEC)
        #endif
        
        if let (fetchedItemWrapperData, _) = try? await URLSession.shared.data(from: apiURL),
           let itemWrapper = try? JSONDecoder().decode(ItemWrapper.self, from: fetchedItemWrapperData) {
            
            await MainActor.run {
                isLoadingPage = false
                    
                items.append(contentsOf: itemWrapper.results)
                pageRemain -= 1
            }
        }
    }
}
