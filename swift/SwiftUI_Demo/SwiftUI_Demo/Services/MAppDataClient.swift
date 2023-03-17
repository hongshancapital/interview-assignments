//
//  MAppDataClient.swift
//  SwiftUI_Demo
//
//  Created by mazb on 2022/9/2.
//

import Foundation

class MAppDataClient {
    
    var isLoading: Bool = false

    private var error: Error? = nil

    private var completion: ([MAppInfo]) -> Void
    
    init(completion: @escaping ([MAppInfo]) -> Void) {
        self.completion = completion
    }
    
    func loadInfos(count: Int) {
        let str = String("https://itunes.apple.com/search?entity=software&limit=\(count)&term=chat")
        guard let url = URL(string: str) else {
            return
        }
        isLoading = true
        let task = URLSession.shared.dataTask(with: url){ (data, response, error) in
            self.isLoading = false
            guard error == nil else {
                self.error = error
                return
            }
            
            if let data = data {
                do{
                    let decoder = JSONDecoder()
                    let responseInfo: MInfoResponse = try decoder.decode(MInfoResponse.self, from: data)
                    DispatchQueue.main.async{ 
                        self.completion(responseInfo.results)
                    }
                }catch{
                    self.error = error
                }
            }
            
        } as URLSessionTask
        task.resume()
    }
}

