//
//  LoadDataProcessor.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/14.
//

import SwiftUI

struct DataLoader<T> where T: Decodable{
    static public func load(filename: String) -> (T? , DataError?) {
        let  data: Data
        var  result: T? = nil
        
        guard let file = Bundle.main.url(forResource: filename, withExtension: nil) else{
            return (nil, DataError.loadData(msg: "Couldn't find \(filename) in main bundle."))
        }
        
        do{
           data = try Data(contentsOf: file)
        }catch{
            return (nil, DataError.loadData(msg: "Load \(filename) error: \(error.localizedDescription)"))
        }
        
        do{
            result = try JSONDecoder().decode(T.self, from: data)
        }catch{
            return (nil, DataError.loadData(msg: "Parse \(filename) json error: \(error.localizedDescription)"))
        }
                
        return(result, nil)
    }
    
}
