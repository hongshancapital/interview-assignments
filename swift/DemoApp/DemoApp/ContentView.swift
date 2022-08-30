//
//  ContentView.swift
//  DemoApp
//
//  Created by 王俊 on 2022/8/30.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject var modelData: ModelData
    var body: some View {
        NavigationView() {
            ZStack() {
                if modelData.appInfos.isEmpty {
                    ProgressView()
                }
                if modelData.appInfos.count > 0 {
                    AppList()
                        .navigationTitle("App")
                        .background(bgGrayColor)
                }
                if let error = modelData.error {
                    VStack() {
                        Text(error.localizedDescription)
                        Button("retry") {
                            modelData.reloadData()
                        }
                    }
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environmentObject(ModelData())
    }
}
