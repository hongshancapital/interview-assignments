//
//  DataManager.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import Foundation

@Observable
class DataManager {
    static let instance : DataManager = DataManager();

    public var appleDatas: AppleResultData = AppleResultData()

    init() {
        getDataFromNet(loadingMore: false)
    }
    
    func loadNetData(appleNetDatas: AppleNetResultData) {
        //转换成UI用的数据
        var appleDatas = AppleResultData()
        appleDatas.resultCount = appleNetDatas.resultCount
        appleDatas.results = []
        for netData in appleNetDatas.results {
            var appleData: AppleData = AppleData()
            appleData.id = netData.trackId
            appleData.trackId = netData.trackId
            appleData.artworkUrl60 = netData.artworkUrl60
            appleData.trackName = netData.trackName
            appleData.description = netData.description
            appleDatas.results.append(appleData)
        }
        self.appleDatas = appleDatas
    }
    
    func getDataFromNet(loadingMore: Bool) {
        //这里的翻页试了一下,怎么传page都不行,只能直接把limit取大了,最多只有100
        let limit = loadingMore ? 100 : 50
        let urlString = "https://itunes.apple.com/search?entity=software&limit=\(limit)&term=chat"
        let url = URL(string: urlString)!
        URLSession.shared.dataTask(with: url){(data, response, error) in
            if data == nil {
                print("data == nil")
                return
            }
            do {
                let appleNetDatas = try JSONDecoder().decode(AppleNetResultData.self, from: data!)
                self.loadNetData(appleNetDatas: appleNetDatas)
            }
            catch {
                print(error)
            }
        }.resume()
    }
}
