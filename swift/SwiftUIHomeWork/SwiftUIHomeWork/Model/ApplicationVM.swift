//
//  ApplicationVM.swift
//  SwiftUIHomeWork
//
//  Created by quanwei chen on 2022/9/4.
//

import Foundation
class ApplicationVM: ObservableObject {
    
    @Published var allAppList: [ApplicationInfo] = []
    // 下拉刷新, 上拉加载
    @Published var isRefreshing: Bool = false
    @Published var isLoadingMore: Bool = false
    @Published var isReloadData: Bool = false
    @Published var hasMoreData: Bool = true
    @Published var likeList: Set<Int> = []
    @Published var total: Int = 0
    private var currentPage = 0
    var pageCount = 10
    init() {
        refreshData()
    }

    func refreshData() {
        currentPage = 1
        Network().httpGet(from: "https://itunes.apple.com/search?entity=software&limit=\(pageCount)&term=social") { (data: ApplicationModel) in
            self.allAppList = data.results;
            self.total = data.results.count
            self.isRefreshing = false
            self.likeList.removeAll()
            print("请求成功\(data)")
            self.hasMoreData = true


        } onerror: { onerror in
            self.isRefreshing = false
            print("请求失败\(onerror)")

        }
    }
    
    func loadMoreData() {
        
        if hasMoreData{
            Network().httpGet(from: "https://itunes.apple.com/search?entity=software&limit=\(pageCount)&term=social") { (data: ApplicationModel) in
                if  data.results.count > 0{
                    self.allAppList.append(contentsOf: data.results) ;
                    self.total += data.results.count
                }
                if  self.total  >= 50{
                    self.hasMoreData = false
                }

                self.isLoadingMore = false
                print("请求成功\(data)")


            } onerror: { onerror in
                self.isLoadingMore = false
                print("请求失败\(onerror)")

            }
        }

    }
}
