//
//  ContentView.swift
//  DemoApp
//
//  Created by dev on 2023/3/10.
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
                    AppItemList()
                        .navigationTitle(NSLocalizedString("App", comment: ""))
                        .background(Color(.systemGray6))
                }
                if let error = modelData.error {
                    VStack() {
                        Text(error.localizedDescription)
                        Button(NSLocalizedString("retry", comment: "")) {
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
            .environment(\.locale, .init(identifier: "zh"))
    }
}
