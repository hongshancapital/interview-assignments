//
//  STISOCountryCodeInfo.swift
//  STBaseProject
//
//  Created by stack on 2018/10/12.
//  Copyright © 2018 ST. All rights reserved.
//

import UIKit

public struct STISOCountryCodeData: Codable {
    var needUpdate: Bool = false
    var datas: [STISOCountryCodeDataModel] = [STISOCountryCodeDataModel]()
}

public struct STISOCountryCodeDataModel: Codable {
    var contryName: String?
    var internationalAreaCode: String?
}

private struct STISOContryCodeURL {
    static let contryCodeURL = "https://gitee.com/stacksong/ServerData/raw/master/isoCountryCode"
}

open class STISOCountryCodeInfo: NSObject {
    
    public func st_requestCoutryCode(success: @escaping(STISOCountryCodeData) -> Void, failed: @escaping(Error) -> Void) {
        if let url = URL.init(string: STISOContryCodeURL.contryCodeURL) {
            let session = URLSession.shared
            session.dataTask(with: url) { (data, response, error) in
                if let newData = data, newData.count > 0 {
                    do {
                        let model = try JSONDecoder().decode(STISOCountryCodeData.self, from: newData)
                        DispatchQueue.main.async {
                            success(model)
                        }
                    } catch let catchError {
                        DispatchQueue.main.async {
                            failed(catchError)
                        }
                    }
                } else {
                    DispatchQueue.main.async {
                        failed(error ?? NSError.init(domain: "reqeust failed", code: 0, userInfo: [:]))
                    }
                }
            }.resume()
        }
    }
}
