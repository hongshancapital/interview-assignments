//
//  LoadData.swift
//  SwiftUIHW
//
//  Created by 施治昂 on 4/14/22.
//

import Foundation
import SwiftUI

enum DataLoaderError: Error {
    case NeedInitialize
}

class DataLoader: ObservableObject, DataLoaderProtocol {
    internal var onePageNum = 10 // 每一页数据量，默认 10 个
    private var allData: [AppItem]? // 从文件中获取的所有数据
    
    init(from file: String, onePageNum: Int = 10) {
        self.onePageNum = onePageNum
        guard let url = Bundle.main.url(forResource: file, withExtension: nil) else {
            return
        }
        guard let data = try? Data(contentsOf: url) else {
            return
        }
        let decoder = JSONDecoder()
        guard let response = try? decoder.decode(DataResponse.self, from: data) else {
            return
        }
        self.allData = response.results
    }
    
    func requestData(page: Int = 0) async throws -> [AppItem]? {
        guard let allData = allData else {
            throw DataLoaderError.NeedInitialize
        }
        
        try await Task.sleep(nanoseconds:5_00_000_000)
        
        // 越界返回nil
        if (page+1)*self.onePageNum >= allData.count {
            return nil
        }
        return Array(allData[(page*self.onePageNum)..<((page+1)*self.onePageNum)])
    }
}
