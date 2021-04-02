//
//  ToolboxListModel.swift
//  QA engineer's toolbox
//
//  Created by Boxiang Yu - Ciic on 2021/1/5.
//

import Foundation
struct ListResponse: Decodable {
    let message: String
    let data: [ToolboxModel]
}
class ToolboxListModel: ObservableObject {
    init(results: [ToolboxModel], options: [String]){
        self.results = results
        self.options = options
    }
    init(){
        self.results = []
        self.options = []
    }
    @Published var results: [ToolboxModel];
    @Published var options: [String];
    @Published var name: String = ""
    @Published var tools: String = ""
    @Published var optionsIndex: Int = 0

    func submit() {
//        tools = options[optionsIndex]
        createToolboxPref(name: name, tools: tools)
        loadData()
    }
    
    func loadToolboxList(onCompletion: (([ToolboxModel]) -> Void)? = nil) {
        guard let url = URL(string: "http://localhost:5000/toolbox") else {
            print("Invalid URL")
            return
        }
        let request = URLRequest(url: url)
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            if let data = data {
                if let response = try? JSONDecoder().decode(ListResponse.self, from: data) {
                    DispatchQueue.main.async {
                        self.results = response.data
                        onCompletion?(response.data)
                    }
                    return
                }
            }
        }.resume()
    }
    func loadOptions() {
        guard let url = URL(string: "http://localhost:5000/toolbox/options") else {
            print("Invalid URL")
            return
        }
        let request = URLRequest(url: url)
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            if let data = data {
                if let response = try? JSONDecoder().decode([String].self, from: data) {
                    DispatchQueue.main.async {
                        self.options = response
                    }
                    return
                }
            }
        }.resume()
    }
    func createToolboxPref(name: String, tools: String) {
        var json = [String:Any]()
        guard let url = URL(string: "http://localhost:5000/toolbox/create") else {
            print("Invalid URL")
            return
        }
        json["name"] = name
        json["tools"] = tools
        
        let data = try? JSONSerialization.data(withJSONObject: json, options: [])
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = data
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        
        URLSession.shared.dataTask(with: request){ data, response, error in
            
            self.loadToolboxList()
            
        }.resume()
        
    }
    func loadData() {
        self.loadOptions();
        self.loadToolboxList();
    }
}
