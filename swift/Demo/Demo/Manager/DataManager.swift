//
//  DataManager.swift
//  Demo
//
//  Created by Kai on 2022/2/16.
//

import SwiftUI

class DataManager: ObservableObject {
    
    @Published var list: [KKModel] = {
        var list = [KKModel]()
        let path =  Bundle.main.path(forResource: "data", ofType: "json")
        guard let data = try? Data(contentsOf: URL(fileURLWithPath: path ?? "")) else {
            return []
        }
        let decoder = JSONDecoder()
        guard let model = try? decoder.decode(KKData.self, from: data) else {
            return []
        }
        return model.results
    }()
    
}

