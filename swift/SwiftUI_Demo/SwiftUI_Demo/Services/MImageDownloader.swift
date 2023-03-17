//
//  MImageDownloader.swift
//  SwiftUI_Demo
//
//  Created by mazb on 2022/9/2.
//

import Foundation
import SwiftUI

class MImageDownloader {
    private var caches: Dictionary<String, Data> = Dictionary()
    private var completion: (UIImage) -> Void
    
    init(completion: @escaping (UIImage) -> Void) {
        self.completion = completion
    }
    
    func downloadImage(url: URL){
        
        if let data = caches[url.absoluteString]
        {
            if let image = UIImage(data: data)
            {
                self.completion(image)
                return
            }
        }
        
        let request = URLRequest(url: url)
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if error != nil {
                print(error.debugDescription)
                return
            }
            if let data = data{
                if let image = UIImage(data: data) {
                    DispatchQueue.main.async {
                        self.caches[url.absoluteString] = data
                        self.completion(image)
                    }
                }
            }
            else{
                print("Data error")
            }
            
            
        } as URLSessionTask
        
        task.resume()
        
    }
}
