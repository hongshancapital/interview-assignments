//
//  SoftwareListViewModel.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/20.
//

import Foundation
import SwiftUI

@MainActor
class SoftwareListViewModel: ObservableObject {
    var softwareCount = 0
    let perPage = 20
    
    @Published var isLoading = false
    @Published var noMore = false
    
    /// 用例：获取软件列表
    let getSoftwareList: GetSoftwareList
    init(getSoftwareList: GetSoftwareList) {
        self.getSoftwareList = getSoftwareList
    }
    
    /// 软件列表数据
    @Published var softwareList: [Software] = []
        
    /// “喜欢”ID列表
    var likedIDList: [Int] = []
    
    func softwares() -> Binding<[Software]> {
        Binding<[Software]>(
            get: {
                self.softwareList
            },
            set: { softwareList in
                for software in softwareList {
                    if let index = self.softwareList.firstIndex(where: { $0.id == software.id }) {
                        if software.isLike {
                            if self.likedIDList.contains(where: { $0 == software.trackId }) == false {
                                self.likedIDList.append(software.trackId)
                            }
                        } else {
                            self.likedIDList.removeAll(where: { $0 == software.trackId })
                        }
                        self.softwareList[index] = software
                    }
                }
            }
        )
    }

    
    /// 重新加载软件列表
    func getSoftwareList() async {
        if self.isLoading {
            return
        }
        self.noMore = false
        
        self.softwareCount = 0
        self.softwareCount += self.perPage

        self.isLoading = true
        let result = await self.getSoftwareList.call(params: .init(count: self.softwareCount))
        self.isLoading = false

        switch result {
        case .success(let newList):
            self.softwareList = self.syncIsLike(newList: newList)
        case .failure:
            break
        }
    }
    
    /// 获取更多软件列表（self.perPage个）
    func getMoreSoftware() async {
        if self.isLoading {
            return
        }

        self.softwareCount += self.perPage

        self.isLoading = true
        let result = await self.getSoftwareList.call(params: .init(count: self.softwareCount))
        self.isLoading = false

        switch result {
        case .success(let newList):
            self.softwareList = self.syncIsLike(newList: newList)
        case .failure:
            self.noMore = true
            break
        }
    }
    
    /// 当前显示的软件列表下标，如果靠近末尾，需要加载更多
    func softwareOnAppear(software: Software) async {
        guard let index = self.softwareList.firstIndex(where: { $0.id == software.id }) else { return }
        if self.softwareList.count - index < 5 {
            await self.getMoreSoftware()
        }
    }
        
    /// 同步数据的“喜欢”状态
    private func syncIsLike(newList: [Software]) -> [Software] {
        var result: [Software] = []
        for i in 0..<newList.count {
            var new = newList[i]
            new.isLike = likedIDList.contains(where: { $0 == new.trackId })
            result.append(new)
        }
        return result
    }
}

