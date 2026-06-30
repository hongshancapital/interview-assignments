//
//  Network.swift
//  SwiftHomework-ZWC
//
//  Created by 油菜花 on 2022/2/16.
//

import Foundation

typealias HMNetworkCallBack = ((_ isSuccess: Bool, _ response: Any?, _ error: Error?) -> Void)?
class Network {
    static let shared = Network()

    func fetchApplicationList( callBack: HMNetworkCallBack) {
        guard let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=20&term=chat") else {fatalError("Request URL missed")}
        let task = URLSession.shared.dataTask(with: url) { (data, response, error) in
            guard error == nil else{
                print("Request error: \(error!)")
                if let callBack = callBack {
                    DispatchQueue.main.async {
                        callBack(false,nil,error)
                    }
                }
                return
            }
            guard let response = response as? HTTPURLResponse else { return }
            
            if response.statusCode == 200 {
                guard let data = data else { return }
                DispatchQueue.main.async {
                    do {
                        let decodedApplication = try JSONDecoder().decode(HMApplicationRequestResult.self, from: data)
                        if let callBack = callBack {
                            DispatchQueue.main.async {
                                callBack(true,decodedApplication.results,error)
                            }
                        }
                    } catch let error {
                        print("Error decoding: ", error)
                        if let callBack = callBack {
                            DispatchQueue.main.async {
                                callBack(false,nil,error)
                            }
                        }
                        
                    }
                }
            } else {
                if let callBack = callBack {
                    DispatchQueue.main.async {
                        callBack(false,nil,error)
                    }
                }
            }
            
        }
        task.resume()
    }
}
