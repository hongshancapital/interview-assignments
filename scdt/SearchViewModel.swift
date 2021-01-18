//
//  SearchViewModel.swift
//  scdt
//
//  Created by qiupeng on 2021/1/18.
//

import Foundation


class SearchViewModel: ObservableObject {

    @Published
    var searchList = [SearchListSectionModel]()

    func search(_ text: String) {
        let str = "http://localhost:8080/search/\(text)"
        guard let url = URL.init(string: str) else {
            return
        }
        let task = URLSession.shared.dataTask(with: url) { (data, rep, err) in
            if let data = data {

                let decoder = JSONDecoder.init()
                do {
                    let list = try decoder.decode([SearchListSectionModel].self, from: data)
                    DispatchQueue.main.async {
                        self.searchList = list
                    }
                } catch {

                }
            }
        }
        task.resume()
    }
}
