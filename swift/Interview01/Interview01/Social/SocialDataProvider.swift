//
//  SocialDataProvider.swift
//  Interview01
//
//  Created by chenzhe on 2022/6/21.
//

import Foundation
import Combine

class SocialDataProvider: ObservableObject {
    
    @Published var data: [SocialModel] = []
    
    init() {
        fetchData()
    }
    
}

extension SocialDataProvider {
    func fetchData() {
        DispatchQueue.global().async {
            guard
                let jsonPath = Bundle.main.path(forResource: "SocialMock", ofType: "json") else {
                    return
                }
            do {
                let url = URL.init(fileURLWithPath: jsonPath)
                let json = try Data.init(contentsOf: url)
                let data = try JSONSerialization.jsonObject(with: json, options: .fragmentsAllowed) as? [String: AnyHashable] ?? [:]
                let results = data["results"] as? [[String: AnyHashable]] ?? []
                var modelArr = [SocialModel]()
                for (_, item) in results.enumerated() {
                    let data = try JSONSerialization.data(withJSONObject: item, options: .fragmentsAllowed)
                    let model = try JSONDecoder().decode(SocialModel.self, from: data)
                    modelArr.append(model)
                }

                DispatchQueue.main.async {
                    self.data = modelArr
                }
                
            } catch {
                print(error)
            }
            
        }
    }
}
