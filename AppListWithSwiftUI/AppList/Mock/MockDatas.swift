//
//  MockDatas.swift
//  AppList
//
//  Created by 王宁 on 2022/4/1.
//

import Foundation
import UIKit

public typealias FinishHandler = (_ success: Bool, _ object:Any? ,_ error: Error?) -> Void
class MockDatas{
    private var datas: ResultModel?
    
    init(){
        loadData()
    }
    
    func isEnd(currentCount: Int) -> Bool{
        return datas!.resultCount <= currentCount
    }
    
    func like(model: DataModel){
        if let _ = datas,let index = datas!.results.firstIndex(where: { (item) -> Bool in
            item.id == model.id
        }){
            datas!.results[index].like = model.like
        }
        
    }
    
    func load(page:Int,pageLength:Int)->ArraySlice<DataModel> {
        let start = page * pageLength;
        if let result = datas?.results, start <= result.count{
            let end = min((page + 1) * pageLength, result.count)
            return datas!.results[start..<end]
        }
        return []
    }
    
    private func loadData(){
        let decoder = JSONDecoder()
        if let mockData:Data = MockDatas.readJson(for: "MockData") {
            datas = try? decoder.decode(ResultModel.self, from: mockData)
        }
    }
    
    static func readJson(for filename: String) -> Data? {
        guard
            let url = Bundle.main.url(forResource:filename, withExtension: "json"),
            let data = try? Data(contentsOf: url) else {
                fatalError("Could not read JSON resource named \(filename) in bundle.")
            }
        return data
    }
}
