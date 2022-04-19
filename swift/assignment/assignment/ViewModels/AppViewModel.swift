//
//  AppViewModel.swift
//  homework
//
//  Created by 干饭人肝不完DDL on 2022/4/11.
//

import Foundation
import Combine

class AppViewModel: ObservableObject {
    @Published private (set) var apps: [AppModel] = []
    @Published private(set) var isLoading:Bool = false
    
    private var timer:AnyCancellable?
    private(set) var results: ResultsModel?
    init(){
        isLoading = true
        getResults()
    }
    func getResults(){
        self.apps=[]
        var i = 0
        DispatchQueue.main.asyncAfter(deadline: .now()+2) {
            self.results = load("Results.json")
            if let results = self.results {
                self.timer = Timer
                    .publish(every: 1, on: .main, in: .common)
                    .autoconnect()
                    .sink { [weak self] _ in
                        if(i<results.resultCount){
                            self?.apps.append(results.results[i])
                            self?.isLoading = false
                        }else {
                            self?.timer?.cancel()
                        }
                        i=i+1
                    }
            }
        }
    }
    
}
