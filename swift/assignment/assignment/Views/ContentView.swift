//
//  ContentView.swift
//  assignment
//
//  Created by 干饭人肝不完DDL on 2022/4/19.
//

import SwiftUI

struct ContentView: View {
    @StateObject var vm: AppViewModel = AppViewModel()
    var body: some View {
        NavigationView {
            Group{
                if vm.isLoading {
                    ProgressView()
                        .frame(maxWidth: .infinity,maxHeight: .infinity)
                } else{
                    ScrollRefreshable(title: "Pull Down", tintColor: .secondary) {
                        ForEach(vm.apps, id:\.self){ app in
                            Row(app: app)
                        }
                        if let results = vm.results, vm.apps.count < results.resultCount{
                            HStack {
                                ProgressView()
                                Text("LOADING...")
                                    .foregroundColor(.secondary)
                                    .font(.caption)
                            }
                            .frame(maxWidth: .infinity)
                        }else{
                            Text("No More Data")
                                .foregroundColor(.secondary)
                                .font(.caption)
                                .frame(maxWidth: .infinity)
                        }
                    } onRefresh: {
                        vm.getResults()
                    }
                }
            }
            .navigationTitle("App")
        }
        
    }
    
    
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
