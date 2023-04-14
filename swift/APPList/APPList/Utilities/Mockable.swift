//
//  Mockable.swift
//  APPList
//
//  Created by three on 2023/4/10.
//

import Foundation

protocol Mockable: AnyObject {
    var bundle: Bundle { get }
    func loadJson(fileName: String) -> Data
    func filterData(data: Data, index: Int, length: Int) -> Data
}

extension Mockable {
    var bundle: Bundle {
        return Bundle(for: type(of: self))
    }
    
    func loadJson(fileName: String) -> Data {
        guard let path = bundle.url(forResource: fileName, withExtension: "json") else {
            fatalError("Failed to load JSON file.")
        }
        do {
            let data = try Data(contentsOf: path)
  
            return data
        } catch {
            print("Error: \(error)")
            
            fatalError("Failed to load JSON file.")
        }
    }
    
    func filterData(data: Data, index: Int, length: Int) -> Data {
        
        do {
            if let jsonData = try JSONSerialization.jsonObject(with: data) as? [String: Any] {
                if let count = jsonData["resultCount"] as? Int, let results = jsonData["results"] as? [Any] {
                    let l = index * length, r = (index + 1) * length
                    var tmp = [Any]()
                    if r <= count {
                        for i in l..<r {
                            tmp.append(results[i])
                        }
                    }
                    return try JSONSerialization.data(withJSONObject: ["resultCount": count, "results": tmp])
                }
            }
            return Data()
        } catch {
            print("Error: \(error)")
            fatalError("Failed to decode JSON data.")
        }
    }
}

class MockURLProtocol: URLProtocol, Mockable {
    static var mockURLs = ["/getAppList": "appList"]

    override class func canInit(with request: URLRequest) -> Bool {
        if let url = request.url {
            return MockURLProtocol.mockURLs.keys.contains(url.path)
        }
        return false
    }
    
    override class func canonicalRequest(for request: URLRequest) -> URLRequest {
        return request;
    }
    
    override func startLoading() {
        if let url = request.url {
            if let fileName = MockURLProtocol.mockURLs[url.path] {
                
                if let query = url.query {
                    let data = loadDataFromUrlQuery(fileName: fileName, query: query)
                    
                    sleep(1)
                    
                    self.client?.urlProtocol(self, didLoad: data)
                    
                    if let response = HTTPURLResponse(url: url, statusCode: 200, httpVersion: nil, headerFields: nil) {
                        self.client?.urlProtocol(self, didReceive: response,
                                            cacheStoragePolicy: .notAllowed)
                    }
                } else {
                    let jsonBody = self.request.bodySteamAsJSON() as! [String: Any]
                    
                    let pageId = jsonBody["pageId"] as! Int
                    let perPage = jsonBody["perPage"] as! Int
                    let json = loadJson(fileName: fileName)
                    let data = filterData(data: json, index: pageId, length: perPage)
                    
                    sleep(1)
                    
                    self.client?.urlProtocol(self, didLoad: data)
                    
                    if let response = HTTPURLResponse(url: url, statusCode: 200, httpVersion: nil, headerFields: nil) {
                        self.client?.urlProtocol(self, didReceive: response,
                                            cacheStoragePolicy: .notAllowed)
                    }
                }
            }
        }
        
        self.client?.urlProtocolDidFinishLoading(self)
    }
    
    func loadDataFromUrlQuery(fileName: String, query: String) -> Data {
        let params = query.components(separatedBy: "&").map({
            $0.components(separatedBy: "=")
        }).reduce(into: [String: Int]()) { dict, pair in
            if pair.count == 2 {
                dict[pair[0]] = Int(pair[1])
            }
        }
        
        let pageId = params["pageId"] ?? 0
        let perPage = params["perPage"] ?? 0
        let json = loadJson(fileName: fileName)
        let data = filterData(data: json, index: pageId, length: perPage)
        
        return data
    }
    
    override func stopLoading() {}
}


extension URLRequest {

    func bodySteamAsJSON() -> Any? {

        guard let bodyStream = self.httpBodyStream else { return nil }

        bodyStream.open()
        let bufferSize: Int = 16
        let buffer = UnsafeMutablePointer<UInt8>.allocate(capacity: bufferSize)
        var dat = Data()

        while bodyStream.hasBytesAvailable {
            let readDat = bodyStream.read(buffer, maxLength: bufferSize)
            dat.append(buffer, count: readDat)
        }

        buffer.deallocate()
        bodyStream.close()

        do {
            return try JSONSerialization.jsonObject(with: dat, options: JSONSerialization.ReadingOptions.allowFragments)
        } catch {
            print("Error: \(error)")
            return nil
        }
    }
}
