//
//  mockedApi.swift
//  Interview
//
//  Created by 梁宇峰 on 2023/2/19.
//

import Foundation

func LogError(_ tag: String, _ format: String, _ args: CVarArg...) {
    NSLog(format, args)
}

func LogFinal(_ tag: String, _ format: String, _ args: CVarArg...) {
    NSLog(format, args)
}

class FakeScdtIntroducationsLoader: ScdtAppIntroductionsLoader {
    private var count = 0
    
    func loadIntroductions() throws -> Data {
        count += 1
        Thread.sleep(until: .now + 2)
        if count == 3 {
            return """
                        {
                            "resultCount": 0,
                            "results": []
                        }
                    """.data(using: .utf8)!
        }
        guard let url = Bundle.main.url(forResource: "1", withExtension: "txt") else {
            let error = NSError(domain: "resource file dosen't exist", code: -1)
            throw error
        }
        if try url.checkResourceIsReachable() {
            let data = try Data(contentsOf: url)
            return data
        }
        let error = NSError(domain: "can't read resource file", code: -1)
        throw error
    }
}
