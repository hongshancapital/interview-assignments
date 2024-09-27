//
//  FileService.swift
//  interview-assignments
//
//  Created by Pedro Pei on 2022/5/23.
//

import Foundation

class FileService {
    
    func fetchJsonFile() -> ApplyListModel? {
        
        guard let url = Bundle.main.url(forResource: "app_data", withExtension: "json") else{
            print("Unable to get file!")
            return nil
        }
        
        do {
            let data = try Data(contentsOf: url)
            
            do {
                
                let model = try JSONDecoder().decode(ApplyListModel.self, from: data)
                return model
            } catch {
                print(error.localizedDescription)
            }
            
        } catch {
            print(error.localizedDescription)
        }
        return nil
    }
}
