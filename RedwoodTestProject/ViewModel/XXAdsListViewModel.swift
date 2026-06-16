//
//  XXAdsListViewModel.swift
//  RedwoodTestProject
//
//  Created by 徐栋 on 2023/2/8.
//

import Foundation
public struct XXAdsModel: Codable {
    var resultCount: Int = 0
    var results: [XXAdsItemModel] = []
}

class XXAdsListViewModel: ObservableObject {
    private var pageIndex: Int = 0
    private var pageCount: Int = 0
    private var pageLength: Int = 10
    @Published public var dataSource: [XXAdsItemModel] = []
    static public let resourcePath = Bundle.main.path(forResource: "advsData", ofType: "json")
    private var adsModel: XXAdsModel?
    @Published var noMoreData = false
 
    func loadData() {
        do {
            let url = URL(filePath: XXAdsListViewModel.resourcePath ?? "")
            let data = try Data(contentsOf: url)
            let adsModel = try JSONDecoder().decode(XXAdsModel.self, from: data)
            self.adsModel = adsModel
            if adsModel.results.count > pageLength {
                noMoreData = false
                dataSource = Array(adsModel.results[0...(pageLength - 1)])
            } else {
                noMoreData = true
                dataSource = adsModel.results
            }
            
            pageCount = Int(ceil(Double(adsModel.resultCount / pageLength)))
            
        } catch let error as Error? {
            print("读取数据失败\(error.debugDescription)")
        }
    }
    func loadMoreData() {
        pageIndex = pageIndex + 1
        if pageIndex >= pageCount {
            noMoreData = true
            return
        }
        noMoreData = false
        guard let adsModel = adsModel else { return }
        let startIndex = pageIndex * pageLength
        let endIndex = (startIndex + pageLength)
        if adsModel.results.count > endIndex {
            dataSource.append(contentsOf: adsModel.results[startIndex...(endIndex - 1)])
        } else {
            dataSource = adsModel.results
        }
    }
}
