//
//  MListViewModel.swift
//  SwiftUI_Demo
//
//  Created by mazb on 2022/9/2.
//

import Foundation
import SwiftUI

class MListViewModel: ObservableObject {
    
    @Published var viewInfos:[MViewInfo] = []
    ///当前页
    private var currentPage: Int = 1
    ///每页数量
    private let pageNum = 10
    
    var isAllLoaded: Bool = false
    var isLoading: Bool {
        get{
            self.dataClient.isLoading
        }
    }
    
    lazy var dataClient = MAppDataClient { (appInfos) in
        self.reMapViewInfos(from: appInfos)
    }
    
    init() {
        reloadData()
    }
    
    func reMapViewInfos(from appInfos: [MAppInfo]) {
        if appInfos.count <=  viewInfos.count && self.currentPage > 1 {
            self.isAllLoaded = true
        }
        viewInfos = appInfos.map({ (appInfo) -> MViewInfo in
            MViewInfo(
                trackId: appInfo.trackId,
                trackName: appInfo.trackName,
                artworkUrl100: appInfo.artworkUrl100,
                description: appInfo.description ?? "",
                isSelected: false
            )
        })
    }
    
    func reloadData() {
        self.currentPage = 1
        self.isAllLoaded = false
        let count = self.currentPage * pageNum
        self.dataClient.loadInfos(count: count)
    }
    
    func loadMore() {
        if self.isLoading {
            print("is loading")
            return
        }
        if self.isAllLoaded {
            print("is all loaded")
            return
        }
        
        self.currentPage += 1
        let count = self.currentPage * pageNum
        self.dataClient.loadInfos(count: count)
    }
}
